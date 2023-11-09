package by.stolybko;

import by.stolybko.api.DeSerializer;
import by.stolybko.entity.Order;
import by.stolybko.api.parser.Lexeme;
import by.stolybko.api.parser.LexemeBuffer;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


import static by.stolybko.api.parser.ParserJson.lexAnalyze;

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

        DeSerializer deSerializer = new DeSerializer();
        Order order = deSerializer.deSerializerJson(Order.class, json);
        System.out.println(order);


    }



}