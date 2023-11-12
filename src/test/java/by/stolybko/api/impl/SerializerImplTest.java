package by.stolybko.api.impl;

import by.stolybko.api.Serializer;
import by.stolybko.entity.Customer;
import by.stolybko.entity.Order;
import by.stolybko.entity.Product;
import by.stolybko.entity.Store;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;


class SerializerImplTest {

    @Test
    void serializingInJsonTest() throws JsonProcessingException {
        // given
        Product product1 = new Product(UUID.fromString("4024a45d-f272-4189-a58c-fc24f2822bac"), "baton", 12.12);
        Product product2 = new Product(UUID.fromString("7024a45d-f272-4189-a58c-fc24f2822bac"), "hleb", 15.12);
        Order order = new Order(UUID.fromString("1024a45d-f272-4189-a58c-fc24f2822bac"), List.of(product1, product2), OffsetDateTime.of(LocalDateTime.of(2023,11, 11, 12, 0, 1), ZoneOffset.MIN));
        Customer customer = new Customer(UUID.fromString("1024a45d-f272-4189-a58c-fc24f2822bad"), "Reuben", "Martin", LocalDate.of(2000, 11 , 3), List.of(order));
        Store store = new Store("UniverMag", List.of(customer), Set.of(product1, product2));

        ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.registerModule(new JavaTimeModule());
        String expected = objectMapper.writeValueAsString(store);

        // when
        Serializer serializer = new SerializerImpl();
        String actual = serializer.serializingInJson(store);

        String tab = " ";
        String newLine = "\n";

        actual = actual.replaceAll(tab, "");
        actual = actual.replaceAll(newLine, "");

        // then
        assertEquals(expected, actual);

    }
}