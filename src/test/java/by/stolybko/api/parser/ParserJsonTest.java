package by.stolybko.api.parser;

import by.stolybko.api.exception.ValidationException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class ParserJsonTest {

    @Test
    void parseJson() {
        // given
        String json = """
                {
                 "id": "1024a45d-f272-4189-a58c-fc24f2822bad",
                 "firstName": "Reuben",
                 "orders": [
                     {
                         "products": [
                             {
                                 "name": "1colbasa",
                                 "price": 205.6
                             },
                             {
                                 "name": "1colbasa",
                                 "price": 255.6
                             }
                         ],
                         "createDate": "2021-09-30T15:30:00+01:00"
                     }
                 ]
             }
                """;

        List<Lexeme> expected = List.of(
                new Lexeme(LexemeType.START_OBJECT, "{"),
                new Lexeme(LexemeType.KEY, "id"),
                new Lexeme(LexemeType.KEY_VALUE_SEPARATOR, ":"),
                new Lexeme(LexemeType.VALUE, "1024a45d-f272-4189-a58c-fc24f2822bad"),
                new Lexeme(LexemeType.PROPERTY_SEPARATOR, ","),
                new Lexeme(LexemeType.KEY, "firstName"),
                new Lexeme(LexemeType.KEY_VALUE_SEPARATOR, ":"),
                new Lexeme(LexemeType.VALUE, "Reuben"),
                new Lexeme(LexemeType.PROPERTY_SEPARATOR, ","),
                new Lexeme(LexemeType.KEY, "orders"),
                new Lexeme(LexemeType.KEY_VALUE_SEPARATOR, ":"),
                new Lexeme(LexemeType.START_ARRAY, "["),
                new Lexeme(LexemeType.START_OBJECT, "{"),
                new Lexeme(LexemeType.KEY, "products"),
                new Lexeme(LexemeType.KEY_VALUE_SEPARATOR, ":"),
                new Lexeme(LexemeType.START_ARRAY, "["),
                new Lexeme(LexemeType.START_OBJECT, "{"),
                new Lexeme(LexemeType.KEY, "name"),
                new Lexeme(LexemeType.KEY_VALUE_SEPARATOR, ":"),
                new Lexeme(LexemeType.VALUE, "1colbasa"),
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
                new Lexeme(LexemeType.PROPERTY_SEPARATOR, ","),
                new Lexeme(LexemeType.KEY, "createDate"),
                new Lexeme(LexemeType.KEY_VALUE_SEPARATOR, ":"),
                new Lexeme(LexemeType.VALUE, "2021-09-30T15:30:00+01:00"),
                new Lexeme(LexemeType.END_OBJECT, "}"),
                new Lexeme(LexemeType.END_ARRAY, "]"),
                new Lexeme(LexemeType.END_OBJECT, "}"),
                new Lexeme(LexemeType.EOF, "")
        );

        // when
        List<Lexeme> actual = ParserJson.parseJson(json);

        // then
        assertIterableEquals(expected, actual);

    }

    @Test
    void parseJsonShouldTrowValidationException_whenJsonIsNotValid() {
        // given
        String json = """
                {
                 "id": "1024a45d-f272-4189-a58c-fc24f2822bad",
                 "firstName": "Reuben",
                 "orders": [
                     {
                         "products": [
                             {
                                 "name": "1colbasa",
                                 "price": 205.6
                             },
                             {
                                 "name": "1colbasa",
                                 "price": 255.6
                             }
                         ],
                         "createDate": "2021-09-30T15:30:00+01:00"
                     
                 ]
             }
                """;

        // when, then
        assertThrows(ValidationException.class, () -> ParserJson.parseJson(json));

    }
}