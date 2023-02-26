package org.example.dao;

import org.example.entity.Book;

import java.sql.SQLException;
import java.util.List;

public interface BookDao {
    List<Book> allBooks() throws Exception;
    Book getById(int id) throws Exception;
    void save(Book book) throws Exception;
}
