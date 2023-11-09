package by.stolybko.api;

import by.stolybko.api.parser.Lexeme;
import by.stolybko.api.parser.LexemeBuffer;
import by.stolybko.api.parser.ParserJson;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DeSerializer {

    private final List<Class<?>> wrapper = List.of(String.class, Integer.class, Double.class, UUID.class, Boolean.class,
                                                LocalDate.class, LocalDateTime.class, OffsetDateTime.class );


    public <T> T deSerializerJson(Class<T> clazz, String json) throws NoSuchFieldException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        List<Lexeme> lexemes = ParserJson.lexAnalyze(json);
        return deSerializer(clazz, lexemes);
    }


    private <T> T deSerializer(Class<T> clazz, List<Lexeme> lexemes) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchFieldException {

        T obj = clazz.getConstructor().newInstance();
        LexemeBuffer lexemeBuffer = new LexemeBuffer(lexemes);
        Lexeme lexemeKey = lexemeBuffer.nextKey();
        do {
            Field field = clazz.getDeclaredField(lexemeKey.getValue());
            field.setAccessible(true);

            if(isWrapper(field.getType()) || field.getType().isPrimitive()) {
                Lexeme lexemeValue = lexemeBuffer.nextValue();
                Object value = getValue(field, lexemeValue.getValue());
                field.set(obj, value);
            }
//            else if (Collection.class.isAssignableFrom(field.getType())) {
//
//            }
            else {
                Object value = deSerializer(field.getType(), lexemeBuffer.nextObject());
                field.set(obj, value);

            }

            lexemeKey = lexemeBuffer.nextKey();

        } while (lexemeKey != null);

        return obj;

    }


    private Object getValue(Field field, String value) throws NoSuchFieldException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        if (field.getType() == UUID.class) {
            return UUID.fromString(value);
        }
        if (field.getType() == Integer.class) {
            return Integer.valueOf(value);
        }
        if (field.getType() == Double.class) {
            return Double.valueOf(value);
        }
        if (field.getType() == String.class) {
            return value;
        }
        return null;
    }

    private boolean isWrapper(Class<?> clazz) {
        return wrapper.contains(clazz);
    }
}
