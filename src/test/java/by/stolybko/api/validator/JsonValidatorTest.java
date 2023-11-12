package by.stolybko.api.validator;

import by.stolybko.api.parser.Lexeme;
import by.stolybko.api.parser.LexemeType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class JsonValidatorTest {

    @ParameterizedTest
    @MethodSource("getExceptionArguments")
    void validateShouldTrowValidationException_whenJsonIsNotValid(List<Lexeme> lexemes, String exceptionMessage) {
        // when
        JsonValidator jsonValidator = JsonValidator.getInstance();
        ValidationResult actual = jsonValidator.validate(lexemes);
        // then
        assertThat(actual.getErrors().size()).isEqualTo(1);
        assertThat(actual.getErrors().get(0).getMessage()).isEqualTo(exceptionMessage);
    }

    static Stream<Arguments> getExceptionArguments() {
        return Stream.of(
                Arguments.of(List.of(
                                new Lexeme(LexemeType.START_OBJECT, "{"),
                                new Lexeme(LexemeType.KEY, "id"),
                                //new Lexeme(LexemeType.KEY_VALUE_SEPARATOR, ":"),
                                //new Lexeme(LexemeType.VALUE, "1024a45d-f272-4189-a58c-fc24f2822bad"),
                                //new Lexeme(LexemeType.PROPERTY_SEPARATOR, ","),
                                new Lexeme(LexemeType.KEY, "firstName"),
                                new Lexeme(LexemeType.KEY_VALUE_SEPARATOR, ":"),
                                new Lexeme(LexemeType.VALUE, "Reuben"),
                                new Lexeme(LexemeType.PROPERTY_SEPARATOR, ","),
                                new Lexeme(LexemeType.END_OBJECT, "}"),
                                new Lexeme(LexemeType.EOF, "")
                        ),
                        "Missing separator key value"),
                Arguments.of(List.of(
                                new Lexeme(LexemeType.START_OBJECT, "{"),
                                new Lexeme(LexemeType.KEY, "id"),
                                new Lexeme(LexemeType.KEY_VALUE_SEPARATOR, ":"),
                                new Lexeme(LexemeType.VALUE, "1024a45d-f272-4189-a58c-fc24f2822bad"),
                                new Lexeme(LexemeType.PROPERTY_SEPARATOR, ","),
                                new Lexeme(LexemeType.KEY, "firstName"),
                                new Lexeme(LexemeType.KEY_VALUE_SEPARATOR, ":"),
                                new Lexeme(LexemeType.VALUE, "Reuben"),
                                new Lexeme(LexemeType.PROPERTY_SEPARATOR, ","),
                                //new Lexeme(LexemeType.END_OBJECT, "}"),
                                new Lexeme(LexemeType.EOF, "")
                        ),
                        "Count of opening { and closing } brackets must match"),
                Arguments.of(List.of(
                                new Lexeme(LexemeType.START_OBJECT, "{"),
                                new Lexeme(LexemeType.KEY, "id"),
                                new Lexeme(LexemeType.KEY_VALUE_SEPARATOR, ":"),
                                new Lexeme(LexemeType.VALUE, "1024a45d-f272-4189-a58c-fc24f2822bad"),
                                new Lexeme(LexemeType.PROPERTY_SEPARATOR, ","),
                                new Lexeme(LexemeType.KEY, "firstName"),
                                new Lexeme(LexemeType.KEY_VALUE_SEPARATOR, ":"),
                                new Lexeme(LexemeType.VALUE, "Reuben"),
                                new Lexeme(LexemeType.PROPERTY_SEPARATOR, ","),
                                new Lexeme(LexemeType.START_ARRAY, "["),
                                new Lexeme(LexemeType.START_OBJECT, "{"),
                                new Lexeme(LexemeType.START_ARRAY, "["),
                                new Lexeme(LexemeType.PROPERTY_SEPARATOR, ","),
                                new Lexeme(LexemeType.KEY, "price"),
                                new Lexeme(LexemeType.KEY_VALUE_SEPARATOR, ":"),
                                new Lexeme(LexemeType.VALUE, "205.6"),
                                new Lexeme(LexemeType.END_OBJECT, "}"),
                                new Lexeme(LexemeType.PROPERTY_SEPARATOR, ","),
                                new Lexeme(LexemeType.START_OBJECT, "{"),
                                new Lexeme(LexemeType.KEY, "name"),
                                new Lexeme(LexemeType.KEY_VALUE_SEPARATOR, ":"),
                                new Lexeme(LexemeType.VALUE, "1colbasa"),
                                new Lexeme(LexemeType.PROPERTY_SEPARATOR, ","),
                                new Lexeme(LexemeType.KEY, "price"),
                                new Lexeme(LexemeType.KEY_VALUE_SEPARATOR, ":"),
                                new Lexeme(LexemeType.VALUE, "255.6"),
                                new Lexeme(LexemeType.END_OBJECT, "}"),
                                new Lexeme(LexemeType.END_ARRAY, "]"),
                                //new Lexeme(LexemeType.END_ARRAY, "]"),
                                new Lexeme(LexemeType.END_OBJECT, "}"),
                                new Lexeme(LexemeType.EOF, "")
                        ),
                        "Count of opening [ and closing ] brackets must match")
        );
    }
}