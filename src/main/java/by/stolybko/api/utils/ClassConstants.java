package by.stolybko.api.utils;

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

}
