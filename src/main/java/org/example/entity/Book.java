package org.example.entity;

import lombok.*;

//POJO классы (Plain old java object)
@Getter
@Setter
@Builder
@ToString
public class Book {
    private int id;
    private String title;
    private double price;
    private int amount;
    private Author author;
}
