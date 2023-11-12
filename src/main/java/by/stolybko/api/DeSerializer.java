package by.stolybko.api;

import by.stolybko.api.exception.ClassAndJsonMappingException;
import by.stolybko.api.parser.Lexeme;
import by.stolybko.api.parser.LexemeBuffer;
import by.stolybko.api.parser.ParserJson;
import by.stolybko.api.utils.ClassConstants;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class DeSerializer {

    public <T> T deSerializingJson(Class<T> clazz, String json) {
        if ("null".equals(json) || json == null) return null;
        List<Lexeme> lexemes;
        T obj;
        try {
            lexemes = ParserJson.parseJson(json);
            obj = deSerializing(clazz, lexemes);
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchFieldException | ClassNotFoundException e) {
            throw new ClassAndJsonMappingException(e.getClass() + ": " + e.getMessage());
        }
        return obj;
    }


    private <T> T deSerializing(Class<T> clazz, List<Lexeme> lexemes) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchFieldException, ClassNotFoundException {
        T obj = clazz.getConstructor().newInstance();
        LexemeBuffer lexemeBuffer = new LexemeBuffer(lexemes);
        Lexeme lexemeKey = lexemeBuffer.nextKey();

        do {
            Field field = clazz.getDeclaredField(lexemeKey.getValue());
            field.setAccessible(true);

            if (ClassConstants.isWrapper(field.getType()) || ClassConstants.isCommonClass(field.getType()) || field.getType().isPrimitive()) {
                Lexeme lexemeValue = lexemeBuffer.nextValue();
                Object value = getValue(field.getType().getSimpleName(), lexemeValue.getValue());
                field.set(obj, value);
            } else if (Collection.class.isAssignableFrom(field.getType())) {
                List<Lexeme> lexemeArray = lexemeBuffer.nextArray();
                Object subObjectArray = getCollection(field, lexemeArray);
                field.set(obj, subObjectArray);
            } else {
                Object value = deSerializing(field.getType(), lexemeBuffer.nextObject());
                field.set(obj, value);
            }

            lexemeKey = lexemeBuffer.nextKey();
        } while (lexemeKey != null);
        return obj;
    }

    private Object getValue(String name, String value) {
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

    private Object getCollection(Field field, List<Lexeme> lexemeArray) throws ClassNotFoundException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        LexemeBuffer lexemeBufferArray = new LexemeBuffer(lexemeArray);
        String fieldType = field.getType().getSimpleName();
        String type = String.valueOf(field.getGenericType());
        Class<?> elementClass = Class.forName(type.substring(type.indexOf("<") + 1, type.indexOf(">")));

        if ("List".equals(fieldType)) {
            List<Object> subObjectList = new ArrayList<>();
            List<Lexeme> lexemeNextObject = lexemeBufferArray.nextObject();
            while (lexemeNextObject != null && lexemeNextObject.size() != 0) {
                subObjectList.add(deSerializing(elementClass, lexemeNextObject));
                lexemeNextObject = lexemeBufferArray.nextObject();
            }
            return subObjectList;
        } else if ("Set".equals(fieldType)) {
            Set<Object> subObjectSet = new HashSet<>();
            List<Lexeme> lexemeNextObject = lexemeBufferArray.nextObject();
            while (lexemeNextObject != null && lexemeNextObject.size() != 0) {
                subObjectSet.add(deSerializing(elementClass, lexemeNextObject));
                lexemeNextObject = lexemeBufferArray.nextObject();
            }
            return subObjectSet;
        } else {
            throw new ClassAndJsonMappingException("Collection: " + fieldType + " is not supported. Needed to add collection support");
        }
    }
}
