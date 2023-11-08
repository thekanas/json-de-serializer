package by.stolybko;

import by.stolybko.entity.Order;
import by.stolybko.entity.Product;
import by.stolybko.parser.Lexeme;
import by.stolybko.parser.LexemeBuffer;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import static by.stolybko.parser.Lexeme.lexAnalyze;

public class Main {

    /*static String json = """
                {
                 "id" : "7024a45d-f272-4189-a58c-fc24f2822bac",
                 "name" : "colbasa",
                 "price" : 255.6
                }
                """;*/
static String json = """
            {
             "id" : "1024a45d-f272-4189-a58c-fc24f2822bad",
             "products" : {
                                         "id" : "7024a45d-f272-4189-a58c-fc24f2822bac",
                                         "name" : "colbasa",
                                         "price" : 255.6
                                        }
            }
            """;
    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchFieldException {
//        List<Lexeme> lexems = lexAnalyze(json);
//        Product product = (Product) deSerializer(Product.class, lexems);
//        System.out.println(product);
//    Product product = new Product();
//        Field field = product.getClass().getDeclaredField("price");
//        field.setAccessible(true);
//
//        System.out.println(isWrapper(field.getType()));
//        System.out.println(Collection.class.isAssignableFrom(field.getType()));
        List<Lexeme> lexems = lexAnalyze(json);
        Order order = (Order) deSerializer(Order.class, lexems);
        System.out.println(order);


    }
    public static Object deSerializer(Class clazz, List<Lexeme> lexemes) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchFieldException {

        Object obj = clazz.getConstructor().newInstance();
        //String[] map = json.split(",");

       /* for (Lexeme l : lexemes) {
            //String[] kv = s.split(":");

            Field field = clazz.getDeclaredField(kv[0].trim());
            field.setAccessible(true);
            Object value = getValue(field, kv[1].trim() );

            field.set(obj, value);

        }*/
        LexemeBuffer lexemeBuffer = new LexemeBuffer(lexemes);
        Lexeme lexemeKey = lexemeBuffer.nextKey();
        do {
            Field field = clazz.getDeclaredField(lexemeKey.getValue());
            field.setAccessible(true);
            //field.getType().isAssignableFrom()


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
        return null;
    }

    public static boolean isWrapper(Class clazz) {
        List<Class> wrapper = new ArrayList<>();
        wrapper.add(UUID.class);
        wrapper.add(Integer.class);
        wrapper.add(String.class);
        wrapper.add(Double.class);

        return wrapper.contains(clazz);
    }


}