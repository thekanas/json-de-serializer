package by.stolybko;

import by.stolybko.api.impl.SerializerImpl;
import by.stolybko.entity.Order;
import by.stolybko.entity.Product;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public class Temp {
    public static void main(String[] args) {


        Product product1 = new Product(UUID.fromString("4024a45d-f272-4189-a58c-fc24f2822bac"), "baton", 12.12);
        Product product2 = new Product(UUID.fromString("7024a45d-f272-4189-a58c-fc24f2822bac"), "hleb", 15.12);
        Order order = new Order(UUID.fromString("1024a45d-f272-4189-a58c-fc24f2822bac"), List.of(product1, product2), OffsetDateTime.now());


        SerializerImpl serializer = new SerializerImpl();
        System.out.println(serializer.serializingInJson(order));
    }
}
