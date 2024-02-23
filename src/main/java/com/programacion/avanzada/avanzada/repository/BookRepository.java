package com.programacion.avanzada.avanzada.repository;

import com.programacion.avanzada.avanzada.repository.modelo.Book;
import io.smallrye.mutiny.Uni;
import io.r2dbc.spi.ConnectionFactory;
import io.smallrye.mutiny.sqlclient.Row;
import io.smallrye.mutiny.sqlclient.RowSet;
import io.smallrye.mutiny.sqlclient.SqlClient;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class BookRepository {
    private final ConnectionFactory connectionFactory;

    public BookRepository(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public Uni<Void> createBook(Book book) {
        return getClient().preparedQuery("INSERT INTO books (author, isbn, title, price) VALUES ($1, $2, $3, $4)")
                .execute(book.getAuthor(), book.getIsbn(), book.getTitle(), book.getPrice())
                .onItem().ignore().andContinueWithNull();
    }

    public Uni<Void> updateBook(Book book) {
        return getClient().preparedQuery("UPDATE books SET author = $1, isbn = $2, title = $3, price = $4 WHERE id = $5")
                .execute(book.getAuthor(), book.getIsbn(), book.getTitle(), book.getPrice(), book.getId())
                .onItem().ignore().andContinueWithNull();
    }

    public Uni<Void> deleteBook(Integer id) {
        return getClient().preparedQuery("DELETE FROM books WHERE id = $1")
                .execute(id)
                .onItem().ignore().andContinueWithNull();
    }

    public Uni<List<Book>> getAllBooks() {
        return getClient().query("SELECT * FROM books")
                .execute()
                .onItem().transformToMulti(this::extractBooks)
                .collect().asList();
    }

    private Uni<List<Book>> extractBooks(RowSet<Row> rowSet) {
        List<Book> books = new ArrayList<>();
        rowSet.forEach(row -> {
            Book book = new Book();
            book.setId(row.get("id", Integer.class));
            book.setAuthor(row.get("author", String.class));
            book.setIsbn(row.get("isbn", String.class));
            book.setTitle(row.get("title", String.class));
            book.setPrice(row.get("price", BigDecimal.class));
            books.add(book);
        });
        return Uni.createFrom().item(books);
    }

    private SqlClient getClient() {
        return io.smallrye.mutiny.sqlclient.SqlClient.newInstance(connectionFactory);
    }
}

