package by.stolybko.api.parser;

public enum LexemeType {
    START_OBJECT, END_OBJECT,
    KEY, VALUE,
    START_ARRAY, END_ARRAY,
    KEY_VALUE_SEPARATOR,
    PROPERTY_SEPARATOR,
    IS_OBJECT, IS_ARRAY,
    IS_STRING, IS_BOOLEAN, IS_NUMBER,

    PROPERTY,

    EOF;
}
