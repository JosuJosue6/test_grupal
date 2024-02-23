package com.programacion.avanzada.modelo;

import com.programacion.avanzada.BookRepository;
import io.r2dbc.postgresql.PostgresqlConnectionConfiguration;
import io.r2dbc.postgresql.PostgresqlConnectionFactory;

import java.math.BigDecimal;
import java.util.Scanner;

public class Principal2 {
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


        String menu = "Escoja:\n" +
                "1. Crear un libro\n" +
                "2. Ver los libros\n" +
                "3. Actualizar un libro\n" +
                "4. Borrar un libro\n" +
                "5. Salir\n";


        int option = 0;
        while (option != 5) {
            System.out.println(menu);
            option = scanner.nextInt();
            scanner.nextLine();
            switch (option) {
                case 1:
                    System.out.println("Ingrese los datos");
                    System.out.println("Autor del libro:");
                    String author = scanner.nextLine();

                    System.out.println("ISBN del libro:");
                    String isbn = scanner.nextLine();

                    System.out.println("Título del libro:");
                    String title = scanner.nextLine();

                    System.out.println("Precio del libro:");
                    BigDecimal price = scanner.nextBigDecimal();

                    BookRepository.createBook(author,isbn,title,price, connectionFactory);
                    break;
                case 2:
                    BookRepository.readAllBooks(connectionFactory);
                    break;
                case 3:
                    System.out.println("Ingrese el ID del libro que desea actualizar:");
                    int idU = scanner.nextInt();
                    scanner.nextLine(); // Consumir la nueva línea

                    System.out.println("Nuevo autor del libro:");
                    String authorU = scanner.nextLine();

                    System.out.println("Nuevo ISBN del libro:");
                    String isbnU = scanner.nextLine();

                    System.out.println("Nuevo título del libro:");
                    String titleU = scanner.nextLine();

                    System.out.println("Nuevo precio del libro:");
                    BigDecimal priceU = scanner.nextBigDecimal();
                    scanner.nextLine(); // Consumir la nueva línea
                    BookRepository.updateBook(idU,authorU,isbnU,titleU,priceU, connectionFactory);
                    break;
                case 4:
                    System.out.println("Ingrese el ID del libro que desea borrar:");
                    int idD = scanner.nextInt();
                    scanner.nextLine(); // Consumir la nueva línea
                    BookRepository.deleteBook(idD, connectionFactory);
                    break;
                case 5:
                    System.out.println("Fin");
                    break;
                default:
                    System.out.println("Opción no valida.");
            }
        }

        // Cerrar el scanner
        scanner.close();
    }
}
