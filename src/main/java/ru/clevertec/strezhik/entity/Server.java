package ru.clevertec.strezhik.entity;

import java.util.concurrent.Semaphore;

public interface Server {
    Integer returnCurrentResponseListSize();

    void addElementInResponseList(Integer i);

    Semaphore getSemaphore();
}
