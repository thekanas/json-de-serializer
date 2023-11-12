package by.stolybko.api;

import by.stolybko.api.exception.ClassAndJsonMappingException;
import by.stolybko.api.utils.ClassConstants;
import java.lang.reflect.Field;
import java.util.Collection;

public class Serializer {

    public String serializingInJson(Object obj) {
        String json;
        try {
            json = serializing(obj);
        } catch (IllegalAccessException | ClassNotFoundException e) {
            throw new ClassAndJsonMappingException(e.getMessage());
        }
        return json;
    }

    private String serializing(Object obj) throws IllegalAccessException, ClassNotFoundException {

        StringBuilder sb = new StringBuilder();
        sb.append("{\n");
        for (Field field : obj.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            String value = getValue(field, field.get(obj));
            sb.append("\"").append(field.getName()).append("\"").append(" : ").append(value).append(",").append("\n");
        }
        sb.deleteCharAt(sb.lastIndexOf(","));
        sb.append("}");
        return sb.toString();
    }

    private String getValue(Field field, Object value) throws IllegalAccessException, ClassNotFoundException {
        if (field.getType().isPrimitive()) {
            return String.valueOf(value);
        } else if (ClassConstants.isWrapper(field.getType())) {
            return String.valueOf(value);
        } else if (ClassConstants.isCommonClass(field.getType())) {
            return "\"" + value + "\"";
        } else if (Collection.class.isAssignableFrom(field.getType())) {
            return getCollection(value);
        }
        return serializing(value);
    }

    private String getCollection(Object value) throws ClassNotFoundException, IllegalAccessException {
        StringBuilder sb = new StringBuilder();
        sb.append("[\n");
        for (Object obj : (Collection<?>) value) {
            String stringObj = serializing(obj);
            sb.append(stringObj);
            sb.append(",");
            sb.append("\n");
        }
        sb.deleteCharAt(sb.lastIndexOf(","));
        sb.append("]");
        return sb.toString();
    }
}
