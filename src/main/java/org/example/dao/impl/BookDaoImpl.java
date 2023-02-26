package org.example.dao.impl;

import lombok.RequiredArgsConstructor;
import org.example.dao.BookDao;
import org.example.entity.Author;
import org.example.entity.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
public class BookDaoImpl implements BookDao {
    private final String url;
    private final String user;
    private final String password;

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    @Override
    public List<Book> allBooks() throws SQLException {
        String query = "SELECT * FROM book b INNER JOIN author a ON a.id = b.author_id";
        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        ResultSet set = statement.executeQuery(query);

        List<Book> result = new ArrayList<>();
        while(set.next()) {
            Author author = Author.builder()
                    .id(set.getInt(1))
                    .name(set.getString("name"))
                    .books(new ArrayList<>())
                    .build();

            Book book = Book.builder()
                    .id(set.getInt(6))
                    .title(set.getString("title"))
                    .author(author)
                    .amount(set.getInt("amount"))
                    .price(set.getDouble("price"))
                    .build();

            author.getBooks().add(book);
            result.add(book);
        }
        return result;
    }

    @Override
    public Book getById(int id) throws SQLException, NoSuchElementException {
        String query = "SELECT * FROM book b INNER JOIN author a ON a.id = b.author_id WHERE b.id = ?";
        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        statement.execute();
        ResultSet set = statement.getResultSet();
        set.next();
        return Book.builder()
                .id(set.getInt("id"))
                .title(set.getString("title"))
                .author(Author.builder().name(set.getString("name")).build())
                .amount(set.getInt("amount"))
                .price(set.getDouble("price"))
                .build();
    }

    @Override
    public void save(Book book) throws SQLException {
        String query = "INSERT INTO book(title, price, amount, author_id) VALUES(?, ?, ?, ?)";
        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, book.getTitle());
        statement.setDouble(2, book.getPrice());
        statement.setInt(3, book.getAmount());
        statement.setInt(4, book.getAuthor().getId());

        statement.executeUpdate();
    }
}
