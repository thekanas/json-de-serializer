package by.stolybko;

import by.stolybko.entity.Product;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

public class Main {

    private static final String json = """
                        
                id : 7024a45d-f272-4189-a58c-fc24f2822bac,
                name : "colbasa",
                price : 255.6
                        
            """;

    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchFieldException {

        Product product = (Product) deSerializer(Product.class, json);

        System.out.println(product);

    }
    public static Object deSerializer(Class clazz, String json) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchFieldException {

        Object obj = clazz.getConstructor().newInstance();
        String[] map = json.split(",");

        for (String s : map) {
            String[] kv = s.split(":");

            Field field = clazz.getDeclaredField(kv[0].trim());
            field.setAccessible(true);
            Object value = getValue(field, kv[1].trim() );

            field.set(obj, value);

        }

        return obj;

    }


    public static Object getValue(Field field, String value) throws NoSuchFieldException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
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
        return deSerializer(field.getType(), value);
    }


}