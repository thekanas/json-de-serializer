package by.stolybko.api.parser;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


class LexemeBufferTest {

    @Test
    void nextKey() {
        // given
        List<Lexeme> lexemes = List.of(
                new Lexeme(LexemeType.START_OBJECT, "{"),
                new Lexeme(LexemeType.KEY, "id"),
                new Lexeme(LexemeType.KEY_VALUE_SEPARATOR, ":"),
                new Lexeme(LexemeType.VALUE, "4024a45d-f272-4189-a58c-fc24f2822bac"),
                new Lexeme(LexemeType.PROPERTY_SEPARATOR, ","),
                new Lexeme(LexemeType.KEY, "name"),
                new Lexeme(LexemeType.KEY_VALUE_SEPARATOR, ":"),
                new Lexeme(LexemeType.VALUE, "someName"),
                new Lexeme(LexemeType.END_OBJECT, "}")
        );
        LexemeBuffer lexemeBuffer = new LexemeBuffer(lexemes);

        // when
        Lexeme actualFirst = lexemeBuffer.nextKey();

        // then
        assertThat(actualFirst.getType()).isEqualTo(LexemeType.KEY);
        assertThat(actualFirst.getValue()).isEqualTo("id");

        // when
        Lexeme actualSecond = lexemeBuffer.nextKey();

        // then
        assertThat(actualSecond.getType()).isEqualTo(LexemeType.KEY);
        assertThat(actualSecond.getValue()).isEqualTo("name");
    }

    @Test
    void nextValue() {
        // given
        List<Lexeme> lexemes = List.of(
                new Lexeme(LexemeType.START_OBJECT, "{"),
                new Lexeme(LexemeType.KEY, "id"),
                new Lexeme(LexemeType.KEY_VALUE_SEPARATOR, ":"),
                new Lexeme(LexemeType.VALUE, "4024a45d-f272-4189-a58c-fc24f2822bac"),
                new Lexeme(LexemeType.PROPERTY_SEPARATOR, ","),
                new Lexeme(LexemeType.KEY, "name"),
                new Lexeme(LexemeType.KEY_VALUE_SEPARATOR, ":"),
                new Lexeme(LexemeType.VALUE, "someName"),
                new Lexeme(LexemeType.END_OBJECT, "}")
        );
        LexemeBuffer lexemeBuffer = new LexemeBuffer(lexemes);

        // when
        Lexeme actualFirst = lexemeBuffer.nextValue();

        // then
        assertThat(actualFirst.getType()).isEqualTo(LexemeType.VALUE);
        assertThat(actualFirst.getValue()).isEqualTo("4024a45d-f272-4189-a58c-fc24f2822bac");

        // when
        Lexeme actualSecond = lexemeBuffer.nextValue();

        // then
        assertThat(actualSecond.getType()).isEqualTo(LexemeType.VALUE);
        assertThat(actualSecond.getValue()).isEqualTo("someName");
    }

    @Test
    void nextObject() {
        // given
        List<Lexeme> lexemes = List.of(
                new Lexeme(LexemeType.START_ARRAY, "["),
                new Lexeme(LexemeType.START_OBJECT, "{"),
                new Lexeme(LexemeType.KEY, "id"),
                new Lexeme(LexemeType.KEY_VALUE_SEPARATOR, ":"),
                new Lexeme(LexemeType.VALUE, "4024a45d-f272-4189-a58c-fc24f2822bac"),
                new Lexeme(LexemeType.END_OBJECT, "}"),
                new Lexeme(LexemeType.PROPERTY_SEPARATOR, ","),
                new Lexeme(LexemeType.START_OBJECT, "{"),
                new Lexeme(LexemeType.KEY, "name"),
                new Lexeme(LexemeType.KEY_VALUE_SEPARATOR, ":"),
                new Lexeme(LexemeType.VALUE, "someName"),
                new Lexeme(LexemeType.END_OBJECT, "}"),
                new Lexeme(LexemeType.END_ARRAY, "[")
        );
        LexemeBuffer lexemeBuffer = new LexemeBuffer(lexemes);

        // when
        List<Lexeme> actualFirst = lexemeBuffer.nextObject();

        // then
        assertThat(actualFirst.size()).isEqualTo(3);
        assertThat(actualFirst.get(0).getType()).isEqualTo(LexemeType.KEY);

        // when
        List<Lexeme> actualSecond = lexemeBuffer.nextObject();

        // then
        assertThat(actualSecond.size()).isEqualTo(3);
        assertThat(actualSecond.get(2).getValue()).isEqualTo("someName");
    }

    @Test
    void nextArray() {
        // given
        List<Lexeme> lexemes = List.of(
                new Lexeme(LexemeType.START_ARRAY, "["),
                new Lexeme(LexemeType.START_OBJECT, "{"),
                new Lexeme(LexemeType.KEY, "id"),
                new Lexeme(LexemeType.KEY_VALUE_SEPARATOR, ":"),
                new Lexeme(LexemeType.VALUE, "5024a45d-f272-4189-a58c-fc24f2822bac"),
                new Lexeme(LexemeType.END_OBJECT, "}"),
                new Lexeme(LexemeType.PROPERTY_SEPARATOR, ","),
                new Lexeme(LexemeType.START_OBJECT, "{"),
                new Lexeme(LexemeType.KEY, "name"),
                new Lexeme(LexemeType.KEY_VALUE_SEPARATOR, ":"),
                new Lexeme(LexemeType.VALUE, "someName"),
                new Lexeme(LexemeType.END_OBJECT, "}"),
                new Lexeme(LexemeType.END_ARRAY, "["),
                new Lexeme(LexemeType.START_ARRAY, "["),
                new Lexeme(LexemeType.VALUE, "4024a45d-f272-4189-a58c-fc24f2822bac"),
                new Lexeme(LexemeType.VALUE, "5024a45d-f272-4189-a58c-fc24f2822bac"),
                new Lexeme(LexemeType.END_ARRAY, "[")
        );
        LexemeBuffer lexemeBuffer = new LexemeBuffer(lexemes);

        // when
        List<Lexeme> actualFirst = lexemeBuffer.nextArray();
        System.out.println(actualFirst);
        // then
        assertThat(actualFirst.size()).isEqualTo(11);
        assertThat(actualFirst.get(0).getType()).isEqualTo(LexemeType.START_OBJECT);

        // when
        List<Lexeme> actualSecond = lexemeBuffer.nextArray();

        // then
        assertThat(actualSecond.size()).isEqualTo(2);
        assertThat(actualSecond.get(0).getValue()).isEqualTo("4024a45d-f272-4189-a58c-fc24f2822bac");
    }
}