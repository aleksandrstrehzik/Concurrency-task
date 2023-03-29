package ru.clevertec.strezhik.entity.impl;

import lombok.Getter;
import ru.clevertec.strezhik.entity.Server;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

@Getter
public class ServerImpl implements Server {

    private final Semaphore semaphore = new Semaphore(1);
    private List<Integer> response;


    public ServerImpl() {
        response = new ArrayList<>();
    }

    @Override
    public Integer returnCurrentResponseListSize() {
        return response.size();
    }

    @Override
    public void addElementInResponseList(Integer i) {
        response.add(i);
    }
}
