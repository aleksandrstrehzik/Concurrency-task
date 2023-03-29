package ru.clevertec.strezhik.entity.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.clevertec.strezhik.entity.Server;

import static org.assertj.core.api.Assertions.assertThat;

class ServerImplTest {

    private Server server;

    @BeforeEach
    void setUp() {
        this.server = new ServerImpl();
    }

    @Test
    void returnCurrentResponseListSize() {
        server.addElementInResponseList(23);

        assertThat(server.returnCurrentResponseListSize()).isEqualTo(1);
    }

    @Test
    void addElementInResponseList() {
        server.addElementInResponseList(45);
        server.addElementInResponseList(2);
        server.addElementInResponseList(40);

        assertThat(server.returnCurrentResponseListSize()).isEqualTo(3);
    }
}