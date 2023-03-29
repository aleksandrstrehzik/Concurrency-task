package ru.clevertec.strezhik.entity.impl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.clevertec.strezhik.entity.Client;
import ru.clevertec.strezhik.entity.Server;
import ru.clevertec.strezhik.entity.exeptions.RequestListIsEmpty;
import ru.clevertec.strezhik.utils.ConcurrentUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

@Setter
@Getter
public class ClientImpl implements Client {

    private final Lock lock = new ReentrantLock();
    private List<Integer> requestList;
    private Integer accumulator;
    private Server server;
    private int n;
    private int threadNumber;

    public ClientImpl(int n, int threadNumber, Server server) {
        this.n = n;
        this.threadNumber = threadNumber;
        this.requestList = new ArrayList<>();
        this.server = server;
        this.accumulator = 0;
        IntStream.range(1, n + 1)
                .forEach(requestList::add);
    }

    public void startThreads() {
        ExecutorService executor = Executors.newFixedThreadPool(threadNumber);
        try {
            executor.invokeAll(
                            IntStream.rangeClosed(1, this.getRequestList().size())
                                    .mapToObj(p -> new ClientThread(server, this))
                                    .collect(toList())).stream()
                    .map(f -> {
                        try {
                            return f.get();
                        } catch (InterruptedException | ExecutionException e) {
                            e.printStackTrace();
                        }
                        return null;
                    })
                    .sorted()
                    .forEach(this::putNumberToAccumulator);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            ConcurrentUtils.stop(executor);
        }
    }

    @Override
    public int getRandomElementFromRequestList() {
        return (int) (Math.random() * requestList.size());
    }

    @Override
    public Integer putNumberToAccumulator(Integer i) {
        accumulator = (1 + i) * (i / 2);
        return accumulator;
    }

    @Override
    public Integer removeRandomElementFromRequestList() {
        int elementIndex = getRandomElementFromRequestList();
        if (elementIndex == 0) {
            throw new RequestListIsEmpty("No more request");
        }
        return requestList.remove(elementIndex);
    }

    @AllArgsConstructor
    class ClientThread implements Callable<Integer> {

        private Server server;
        private Client client;

        @Override
        public Integer call() throws InterruptedException {
            while (true) {
                if (!requestList.isEmpty()) {
                    Integer removeInteger = null;
                    if (lock.tryLock()) {
                        removeInteger = client.removeRandomElementFromRequestList();
                        lock.unlock();
                    }
                    if (removeInteger != null) {
                        server.getSemaphore().acquire();
                        ConcurrentUtils.sleep(getRandomDelay());
                        server.addElementInResponseList(removeInteger);
                        server.getSemaphore().release();
                        return server.returnCurrentResponseListSize();
                    }
                } else {
                    break;
                }
            }
            return server.returnCurrentResponseListSize();
        }

        private int getRandomDelay() {
            return (int) (Math.random() * 901 + 100);
        }

    }
}
