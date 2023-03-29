package ru.clevertec.strezhik.entity.exeptions;

public class RequestListIsEmpty extends RuntimeException {

    public RequestListIsEmpty(String message) {
        super(message);
    }

}
