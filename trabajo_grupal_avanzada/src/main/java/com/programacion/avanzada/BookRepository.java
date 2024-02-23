package com.programacion.avanzada;

import io.r2dbc.postgresql.PostgresqlConnectionFactory;
import io.r2dbc.postgresql.api.PostgresqlResult;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.tuples.Tuple2;
import io.smallrye.mutiny.tuples.Tuple3;
import io.smallrye.mutiny.tuples.Tuple4;
import io.smallrye.mutiny.tuples.Tuple5;
import io.smallrye.mutiny.tuples.Tuple6;
import io.smallrye.mutiny.tuples.Tuple7;
import io.smallrye.mutiny.tuples.Tuple8;
import io.smallrye.mutiny.tuples.Tuple9;
import io.smallrye.mutiny.tuples.Tuples;

import java.math.BigDecimal;


public class BookRepository {
    public static void createBook(String author, String isbn, String title,BigDecimal price, PostgresqlConnectionFactory connectionFactory) {
        connectionFactory.create()
                .flatMapMany(connection -> connection.createStatement(
                                "INSERT INTO books (author, isbn, title, price) VALUES ($1, $2, $3, $4)")
                        .bind("$1", author)
                        .bind("$2", isbn)
                        .bind("$3", title)
                        .bind("$4", price)
                        .execute())
                .flatMap(PostgresqlResult::getRowsUpdated).subscribe(rowsUpdated -> System.out.println("Hecho"))
                ;
    }

    public static void readAllBooks(PostgresqlConnectionFactory connectionFactory) {
        // Leer todos los libros de la base de datos
        connectionFactory.create()
                .flatMapMany(connection -> connection.createStatement("SELECT * FROM books").execute())
                .flatMap(result -> result.map((row, metadata) -> {
                    int id = row.get("id", Integer.class);
                    String author = row.get("author", String.class);
                    String isbn = row.get("isbn", String.class);
                    String title = row.get("title", String.class);
                    BigDecimal price = row.get("price", BigDecimal.class);
                    return Tuple5.of(id, author, isbn, title, price);
                }))
                .subscribe(book -> {
                    System.out.println("Book{" +
                            "id=" + book.getItem1() +
                            ", author='" + book.getItem2() + '\'' +
                            ", isbn='" + book.getItem3() + '\'' +
                            ", title='" + book.getItem4() + '\'' +
                            ", price=" + book.getItem5() +
                            '}');
                });
    }

    public static void updateBook(int id,String author, String isbn, String title,BigDecimal price, PostgresqlConnectionFactory connectionFactory) {


        connectionFactory.create()
                .flatMapMany(connection -> connection.createStatement(
                                "UPDATE books SET author = $1, isbn = $2, title = $3, price = $4 WHERE id = $5")
                        .bind("$1", author)
                        .bind("$2", isbn)
                        .bind("$3", title)
                        .bind("$4", price)
                        .bind("$5", id)
                        .execute())
                .flatMap(PostgresqlResult::getRowsUpdated).subscribe(rowsUpdated -> System.out.println("Hecho"));
    }

    //ELIMINAR
    // Método para eliminar un libro existente
    public static void deleteBook(int id, PostgresqlConnectionFactory connectionFactory) {
        connectionFactory.create()
                .flatMapMany(connection -> connection.createStatement(
                                "DELETE FROM books WHERE id = $1")
                        .bind("$1", id)
                        .execute())
                .flatMap(PostgresqlResult::getRowsUpdated).subscribe(rowsUpdated -> System.out.println("Hecho"));
    }
}
