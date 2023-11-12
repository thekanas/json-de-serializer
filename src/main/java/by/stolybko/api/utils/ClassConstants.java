package by.stolybko.api.utils;

import by.stolybko.api.exception.ClassAndJsonMappingException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public class ClassConstants {

    private static final List<Class<?>> WRAPPER = List.of(Byte.class, Short.class, Integer.class, Long.class, Float.class, Double.class, Boolean.class, Character.class);
    private static final List<Class<?>> COMMON_CLASSES = List.of(String.class, UUID.class, LocalDate.class, LocalDateTime.class, OffsetDateTime.class);


    public static boolean isWrapper(Class<?> clazz) {
        return WRAPPER.contains(clazz);
    }

    public static boolean isCommonClass(Class<?> clazz) {
        return COMMON_CLASSES.contains(clazz);
    }

    public static Object getValue(String name, String value) {
        return switch (name) {
            case "String" -> value;
            case "Byte", "byte" -> Byte.valueOf(value);
            case "Short", "short" -> Short.valueOf(value);
            case "Integer", "int" -> Integer.valueOf(value);
            case "Long", "long" -> Long.valueOf(value);
            case "Float", "float" -> Float.valueOf(value);
            case "Double", "double" -> Double.valueOf(value);
            case "Boolean", "boolean" -> Boolean.valueOf(value);
            case "Character", "character" -> value.charAt(0);
            case "UUID" -> UUID.fromString(value);
            case "LocalDate" -> LocalDate.parse(value);
            case "LocalDateTime" -> LocalDateTime.parse(value);
            case "OffsetDateTime" -> OffsetDateTime.parse(value);
            default -> throw new ClassAndJsonMappingException("Class: " + name + " is not supported. Needed to add class support");
        };
    }

}
