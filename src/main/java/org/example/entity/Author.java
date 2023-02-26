package org.example.entity;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@ToString
public class Author {
    private int id;
    private String name;
    @ToString.Exclude
    private List<Book> books;
}
