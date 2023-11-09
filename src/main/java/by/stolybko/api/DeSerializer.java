package by.stolybko.api;

import by.stolybko.api.parser.Lexeme;
import by.stolybko.api.parser.LexemeBuffer;
import by.stolybko.api.parser.ParserJson;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class DeSerializer {

    private final List<Class<?>> wrapper = List.of(String.class, Integer.class, Double.class, UUID.class, Boolean.class,
                                                LocalDate.class, LocalDateTime.class, OffsetDateTime.class );


    public <T> T deSerializerJson(Class<T> clazz, String json) throws NoSuchFieldException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, ClassNotFoundException {
        List<Lexeme> lexemes = ParserJson.lexAnalyze(json);
        return deSerializer(clazz, lexemes);
    }


    private <T> T deSerializer(Class<T> clazz, List<Lexeme> lexemes) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchFieldException, ClassNotFoundException {

        Object obj = clazz.getConstructor().newInstance();
        LexemeBuffer lexemeBuffer = new LexemeBuffer(lexemes);
        Lexeme lexemeKey = lexemeBuffer.nextKey();

        do {
            Field field = clazz.getDeclaredField(lexemeKey.getValue());
            field.setAccessible(true);

            if(isWrapper(field.getType()) || field.getType().isPrimitive()) {
                Lexeme lexemeValue = lexemeBuffer.nextValue();
                Object value = getValue(field.getType().getSimpleName(), lexemeValue.getValue());
                field.set(obj, value);
            }
            else if (Collection.class.isAssignableFrom(field.getType())) {
                List<Lexeme> lexemeArray = lexemeBuffer.nextArray();
                Object subObjectArray = getCollection(field, lexemeArray);
                field.set(obj, subObjectArray);
            }
            else {
                Object value = deSerializer(field.getType(), lexemeBuffer.nextObject());
                field.set(obj, value);
            }

            lexemeKey = lexemeBuffer.nextKey();

        } while (lexemeKey != null);

        return (T) obj;

    }


    private Object getValue(String name, String value) throws NoSuchFieldException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return switch (name) {
            case "String" -> value;
            case "UUID" -> UUID.fromString(value);
            case "Integer", "int" -> Integer.valueOf(value);
            case "Double", "double" -> Double.valueOf(value);
            case "Boolean", "boolean" -> Boolean.valueOf(value);
            case "LocalDate" -> LocalDate.parse(value);
            case "LocalDateTime" -> LocalDateTime.parse(value);
            case "OffsetDateTime" -> OffsetDateTime.parse(value);
            default -> null;
        };
    }

    private Object getCollection(Field field, List<Lexeme> lexemeArray) throws ClassNotFoundException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        LexemeBuffer lexemeBufferArray = new LexemeBuffer(lexemeArray);
        String fieldType = field.getType().getSimpleName();
        String type = String.valueOf(field.getGenericType());
        Class<?> elementClass = Class.forName(type.substring(type.indexOf("<") + 1, type.indexOf(">")));

        if("List".equals(fieldType)) {
            List<Object> subObjectList = new ArrayList<>();
            List<Lexeme> lexemeNextObject = lexemeBufferArray.nextObject();
            while (lexemeNextObject != null && lexemeNextObject.size() != 0) {
                subObjectList.add(deSerializer(elementClass, lexemeNextObject));
                lexemeNextObject = lexemeBufferArray.nextObject();
            }
            return subObjectList;
        }

        if("Set".equals(fieldType)) {
            Set<Object> subObjectList = new LinkedHashSet<>();
            List<Lexeme> lexemeNextObject = lexemeBufferArray.nextObject();
            while (lexemeNextObject != null && lexemeNextObject.size() != 0) {
                subObjectList.add(deSerializer(elementClass, lexemeNextObject));
                lexemeNextObject = lexemeBufferArray.nextObject();
            }
            return subObjectList;
        }

        return null;
    }

    private boolean isWrapper(Class<?> clazz) {
        return wrapper.contains(clazz);
    }
}
