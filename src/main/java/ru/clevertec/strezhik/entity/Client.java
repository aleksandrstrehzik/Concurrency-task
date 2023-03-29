package ru.clevertec.strezhik.entity;

public interface Client {

    /**
     * @return random int between 1 and size of request List
     */
    int getRandomElementFromRequestList();

    /**
     * @param i arg to put
     * @return accumulator value
     */
    Integer putNumberToAccumulator(Integer i);

    /**
     * @return removed element
     */
    Integer removeRandomElementFromRequestList();


    /**
     * start all threads
     */
    void startThreads();
}
