package ru.clevertec.strezhik.entity.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.clevertec.strezhik.entity.Client;
import ru.clevertec.strezhik.entity.Server;
import ru.clevertec.strezhik.entity.exeptions.RequestListIsEmpty;

class IntegrationTest {

    private Client client;
    private Server server;

    @BeforeEach
    void setUp() {
        this.server = new ServerImpl();
        this.client = new ClientImpl(100, 8, server);
        client.startThreads();
    }

    @Test
    void checkSizeRequestListShouldBeEmpty() {
        Assertions.assertThrows(RequestListIsEmpty.class,
                () -> client.getRandomElementFromRequestList());
    }

    @Test
    void checkSizeRespondListShouldBeEmpty() {
        org.assertj.core.api.Assertions.assertThat(server.returnCurrentResponseListSize()).isEqualTo(0);
    }
}
