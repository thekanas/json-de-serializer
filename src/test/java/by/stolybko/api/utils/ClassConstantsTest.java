package by.stolybko.api.utils;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ClassConstantsTest {

    @ParameterizedTest
    @MethodSource("getClassAndValueArguments")
    void getValueTest(String expected, String value) {
        // when
        Object obj = ClassConstants.getValue(expected, value);
        String actual = obj.getClass().getSimpleName();

        // then
        assertEquals(expected, actual);
        assertEquals(value, obj.toString());
    }

    static Stream<Arguments> getClassAndValueArguments() {
        return Stream.of(
                Arguments.of("String", "string"),
                Arguments.of("Byte", "8"),
                Arguments.of("Short", "8"),
                Arguments.of("Integer", String.valueOf(Integer.MAX_VALUE)),
                Arguments.of("Long", String.valueOf(Long.MAX_VALUE)),
                Arguments.of("Float", "11.1"),
                Arguments.of("Double", "11.1"),
                Arguments.of("Boolean", "true"),
                Arguments.of("Character", "t"),
                Arguments.of("UUID", "4024a45d-f272-4189-a58c-fc24f2822bac"),
                Arguments.of("LocalDate", "2023-11-11"),
                Arguments.of("LocalDateTime", "2023-11-11T23:59"),
                Arguments.of("OffsetDateTime", "2023-11-11T23:59+03:00")

        );
    }
}