package org.example;

import org.example.dao.BookDao;
import org.example.dao.impl.BookDaoImpl;
import org.example.entity.Author;
import org.example.entity.Book;

import java.sql.*;
import java.util.List;

public class Program {
    public static void main(String[] args) throws Exception {
        BookDao dao = new BookDaoImpl(
                "jdbc:postgresql://localhost:5432/books_java225",
                "postgres",
                "123"
        );

        List<Book> books = dao.allBooks();
        Author author = books.get(1).getAuthor();
        System.out.println(author);
        Book book = Book.builder()
                .title("Какая-то книга Есенина")
                .author(author)
                .amount(123)
                .price(765.99)
                .build();
        dao.save(book);
    }
}
