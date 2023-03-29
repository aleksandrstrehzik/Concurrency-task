package ru.clevertec.strezhik.entity.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.clevertec.strezhik.entity.Client;

import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class ClientImplTest {

    private Client client;

    @BeforeEach
    void setUp() {
        this.client = new ClientImpl(100, 8, new ServerImpl());
    }

    @Test
    void checkRandomElementFromRequestList() {
        boolean empty = IntStream.generate(() -> client.getRandomElementFromRequestList())
                .filter(i -> i < 0 || i > 100)
                .limit(100)
                .findAny()
                .isEmpty();

        assertThat(empty).isTrue();
    }

    @ParameterizedTest
    @MethodSource("putNumberToAccumulator")
    void checkPutNumberToAccumulator(Integer nValue, Integer expected) {
        Integer actual = client.putNumberToAccumulator(nValue);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void removeRandomElementFromRequestList() {
        Integer integer = client.removeRandomElementFromRequestList();

        assertThat(integer).isNotNull();
    }

    static Stream<Arguments> putNumberToAccumulator() {
        return Stream.of(
                arguments(100, 5050),
                arguments(90, 4095),
                arguments(8, 36));
    }
}