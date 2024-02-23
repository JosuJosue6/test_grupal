package org.example;

import io.r2dbc.postgresql.PostgresqlConnectionConfiguration;
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
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Configuración de la conexión a PostgreSQL
        PostgresqlConnectionConfiguration configuration = PostgresqlConnectionConfiguration.builder()
                .host("localhost")
                .port(5432)
                .database("Test")
                .username("postgres")
                .password("josueNSD6")
                .build();

        // Crear una conexión a la base de datos
        PostgresqlConnectionFactory connectionFactory = new PostgresqlConnectionFactory(configuration);

        // Menú de opciones
        String menu = "Seleccione una opción:\n" +
                "1. Crear un libro\n" +
                "2. Leer todos los libros\n" +
                "3. Actualizar un libro\n" +
                "4. Borrar un libro\n" +
                "5. Salir\n";

        System.out.println("Bienvenido a la aplicación de gestión de libros!");

        int option = 0;
        while (option != 5) {
            System.out.println(menu);
            option = scanner.nextInt();
            scanner.nextLine(); // Consumir la nueva línea

            switch (option) {
                case 1:
                    createBook(scanner, connectionFactory);
                    break;
                case 2:
                    readAllBooks(connectionFactory);
                    break;
                case 3:
                    //updateBook(scanner, connectionFactory);
                    break;
                case 4:
                    //deleteBook(scanner, connectionFactory);
                    break;
                case 5:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción inválida, por favor seleccione una opción válida.");
            }
        }

        // Cerrar el scanner
        scanner.close();
    }

    // Método para crear un nuevo libro
    private static void createBook(Scanner scanner, PostgresqlConnectionFactory connectionFactory) {

        System.out.println("Ingrese el autor del libro:");
        String author = scanner.nextLine();

        System.out.println("Ingrese el ISBN del libro:");
        String isbn = scanner.nextLine();

        System.out.println("Ingrese el título del libro:");
        String title = scanner.nextLine();

        System.out.println("Ingrese el precio del libro:");
        BigDecimal price = scanner.nextBigDecimal();
        scanner.nextLine(); // Consumir la nueva línea

        // Crear el libro en la base de datos
        connectionFactory.create()
                .flatMapMany(connection -> connection.createStatement(
                                "INSERT INTO books (author, isbn, title, price) VALUES ($1, $2, $3, $4)")
                        .bind("$1", author)
                        .bind("$2", isbn)
                        .bind("$3", title)
                        .bind("$4", price)
                        .execute())
                .flatMap(PostgresqlResult::getRowsUpdated)
                .subscribe(rowsUpdated -> System.out.println("Libro creado exitosamente."));

    }


    // Método para leer todos los libros
    private static void readAllBooks(PostgresqlConnectionFactory connectionFactory) {
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
                    System.out.println("ID: " + book.getItem1());
                    System.out.println("Autor: " + book.getItem2());
                    System.out.println("ISBN: " + book.getItem3());
                    System.out.println("Título: " + book.getItem4());
                    System.out.println("Precio: " + book.getItem5());
                    System.out.println("---------------------------------------");
                });
    }

    // Método para actualizar un libro
    /*private static void updateBook(Scanner scanner, PostgresqlConnectionFactory connectionFactory) {
        System.out.println("Ingrese el ID del libro que desea actualizar:");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consumir la nueva línea

        System.out.println("Ingrese el nuevo autor del libro:");
        String author = scanner.nextLine();

        System.out.println("Ingrese el nuevo ISBN del libro:");
        String isbn = scanner.nextLine();

        System.out.println("Ingrese el nuevo título del libro:");
        String title = scanner.nextLine();

        System.out.println("Ingrese el nuevo precio del libro:");
        BigDecimal price = scanner.nextBigDecimal();
        scanner.nextLine(); // Consumir la nueva línea

        // Actualizar el libro en la base de datos
        connectionFactory.create()
                .flatMap(connection -> connection.createStatement(
                                "UPDATE books SET author = $1, isbn = $2, title = $3, price = $4 WHERE id = $5")
                        .bind("$1", author)
                        .bind("$2", isbn)
                        .bind("$3", title)
                        .bind("$4", price)
                        .bind("$5", id)
                        .execute())
                .flatMap(result -> result.getRowsUpdated())
                .subscribe(updatedRows -> {
                    if (updatedRows > 0) {
                        System.out.println("Libro actualizado exitosamente.");
                    } else {
                        System.out.println("No se pudo actualizar el libro.");
                    }
                });
    }*/

    // Método para borrar un libro
    /*private static void deleteBook(Scanner scanner, PostgresqlConnectionFactory connectionFactory) {
        System.out.println("Ingrese el ID del libro que desea borrar:");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consumir la nueva línea

        // Borrar el libro de la base de datos
        connectionFactory.create()
                .flatMap(connection -> connection.createStatement(
                                "DELETE FROM books WHERE id = $1")
                        .bind("$1", id)
                        .execute())
                .flatMap(result -> result.getRowsUpdated())
                .subscribe(updatedRows -> {
                    if (updatedRows > 0) {
                        System.out.println("Libro borrado exitosamente.");
                    } else {
                        System.out.println("No se pudo borrar el libro.");
                    }
                });
    }*/
}



