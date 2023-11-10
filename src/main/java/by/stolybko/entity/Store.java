package by.stolybko.entity;

import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class Store {

    String name;
    List<Customer> customers;
    Set<Product> products;
}
