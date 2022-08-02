package ru.it1.tracing.common;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TestUtils {

    @SneakyThrows
    public static void sleep(long millis) {
        Thread.sleep(millis);
    }

}
