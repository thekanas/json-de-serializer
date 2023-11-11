package by.stolybko.api;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class Serializer {

    private static final List<Class<?>> wrapper = List.of(Integer.class, Double.class, Boolean.class);
    private static final List<Class<?>> specClass = List.of(String.class, UUID.class, LocalDate.class, LocalDateTime.class, OffsetDateTime.class);

    String json = """
                      {
                    "id": "2024a45d-f272-4189-a58c-fc24f2822bad",
                    "products": [
                        {
                            "id": "3024a45d-f272-4189-a58c-fc24f2822bac",
                            "name": "1colbasa",
                            "price": 205.6
                        },
                        {
                            "id": "4024a45d-f272-4189-a58c-fc24f2822bac",
                            "name": "1colbasa",
                            "price": 255.6
                        }
                    ],
                    "createDate": "2021-09-30T15:30:00+01:00"
                }
            """;

    public String serializing(Object obj) throws IllegalAccessException, ClassNotFoundException {

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
        } else if (isWrapper(field.getType())) {
            return String.valueOf(value);
        } else if (isSpecClass(field.getType())) {
            return "\"" + value + "\"";
        } else if (Collection.class.isAssignableFrom(field.getType())) {
            return getCollection(value);
        }

        return serializing(value);
    }

    private String getCollection(Object value) throws ClassNotFoundException, IllegalAccessException {
        StringBuilder sb = new StringBuilder();
        sb.append("[\n");
        for (Object obj : (Collection<?>)value){
            String stringObj = serializing(obj);
            sb.append(stringObj);
            sb.append(",");
            sb.append("\n");
        }
        sb.deleteCharAt(sb.lastIndexOf(","));
        sb.append("]");
        return sb.toString();
    }

    private boolean isWrapper(Class<?> clazz) {
        return wrapper.contains(clazz);
    }

    private boolean isSpecClass(Class<?> clazz) {
        return specClass.contains(clazz);
    }
}
