package by.stolybko;

import by.stolybko.api.DeSerializer;
import by.stolybko.entity.Customer;
import by.stolybko.entity.Order;
import by.stolybko.api.parser.Lexeme;
import by.stolybko.api.parser.LexemeBuffer;
import by.stolybko.entity.Product;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
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
                 "id": "1024a45d-f272-4189-a58c-fc24f2822bad",
                 "firstName": "Reuben",
                 "lastName": "Martin",
                 "dateBirth": "2003-11-03",
                 "orders": [
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
                 ]
             }
            """;
    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchFieldException, ClassNotFoundException {
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
        Customer customer = deSerializer.deSerializerJson(Customer.class, json);
        System.out.println(customer);
        /*Product product1 = new Product();
        //Product product2 = new Product();

        List<Product> pr = List.of(product1);

        Order order = new Order(UUID.fromString("1024a45d-f272-4189-a58c-fc24f2822bad"),pr );

        Field field = order.getClass().getDeclaredField("products");
        field.setAccessible(true);
        System.out.println(field.getType().getSimpleName());
        String type = String.valueOf(field.getGenericType());
        System.out.println(type);
        Class<?> elementClass = Class.forName(type.substring(type.indexOf("<") + 1, type.indexOf(">")));
        System.out.println(elementClass);*/


    }



}