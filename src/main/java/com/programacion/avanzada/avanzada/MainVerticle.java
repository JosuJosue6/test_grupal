package com.programacion.avanzada.avanzada;

import com.programacion.avanzada.avanzada.repository.BookRepository;
import com.programacion.avanzada.avanzada.repository.modelo.Book;
import io.r2dbc.postgresql.PostgresqlConnectionFactory;
import io.smallrye.mutiny.Uni;

//import io.vertx.mutiny.core.AbstractVerticle;
//import io.vertx.mutiny.core.Vertx;


import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class MainVerticle extends AbstractVerticle {
    public static void main(String[] args) {
        Vertx.vertx().deployVerticle(new MainVerticle());
    }

    @Override
    public Uni<Void> asyncStart() {
        PostgresqlConnectionFactory connectionFactory = new PostgresqlConnectionFactory(PostgresqlConnectionConfiguration.builder()
                .host("localhost")
                .port(5432)
                .database("Test")
                .username("admin")
                .password("josueNSD6")
                .build());

        BookRepository bookRepository = new BookRepository(connectionFactory);

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Seleccione una opción:");
            System.out.println("1. Agregar libro");
            System.out.println("2. Actualizar libro");
            System.out.println("3. Eliminar libro");
            System.out.println("4. Mostrar todos los libros");
            System.out.println("0. Salir");

            int option = scanner.nextInt();
            scanner.nextLine(); // Consumir la nueva línea

            switch (option) {
                case 1:
                    System.out.println("Ingrese el autor:");
                    String author = scanner.nextLine();
                    System.out.println("Ingrese el ISBN:");
                    String isbn = scanner.nextLine();
                    System.out.println("Ingrese el título:");
                    String title = scanner.nextLine();
                    System.out.println("Ingrese el precio:");
                    BigDecimal price = scanner.nextBigDecimal();
                    scanner.nextLine(); // Consumir la nueva línea
                    Book newBook = new Book();
                    newBook.setAuthor(author);
                    newBook.setIsbn(isbn);
                    newBook.setTitle(title);
                    newBook.setPrice(price);
                    bookRepository.createBook(newBook).await().indefinitely();
                    System.out.println("Libro agregado con éxito.");
                    break;
                case 2:
                    // Implementar la actualización de un libro
                    break;
                case 3:
                    // Implementar la eliminación de un libro
                    break;
                case 4:
                    List<Book> books = bookRepository.getAllBooks().await().indefinitely();
                    for (Book book : books) {
                        System.out.println(book);
                    }
                    break;
                case 0:
                    System.out.println("Saliendo de la aplicación.");
                    System.exit(0);
                default:
                    System.out.println("Opción inválida. Por favor, seleccione una opción válida.");
            }
        }
    }
}
