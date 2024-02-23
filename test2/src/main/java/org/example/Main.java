package org.example;

import io.r2dbc.postgresql.PostgresqlConnectionConfiguration;
import io.r2dbc.postgresql.PostgresqlConnectionFactory;
import io.smallrye.mutiny.tuples.Tuple5;
import java.math.BigDecimal;
import java.util.Scanner;
import io.r2dbc.postgresql.api.PostgresqlResult;


// Coneccion usando
// GROPO6: Smallrye Mutiny

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        PostgresqlConnectionConfiguration configuration = PostgresqlConnectionConfiguration.builder()
                .host("localhost")
                .port(5432)
                .database("Test")
                .username("postgres")
                .password("josueNSD6")
                .build();

        PostgresqlConnectionFactory connectionFactory = new PostgresqlConnectionFactory(configuration);
        //CONECCION

        //CRUD EN LA CONSOLA
        String menu = "Seleccione una opción:\n" +
                "1. Ingresar    2. Mostrar todos en Stock\n" +
                "3. Actualizar  4. Borrar  5. Salir\n";

        System.out.println("GRUPAL STREAMS REACTIVOS: SMALLRYE MUTINY");

        int option = 0;
        while (option != 5) {
            System.out.println(menu);
            option = scanner.nextInt();
            scanner.nextLine(); // Consumir la nueva línea

            switch (option) {
                case 1:
                    crearLibro(scanner, connectionFactory);
                    break;
                case 2:
                    readAllBooks(connectionFactory);
                    break;
                case 3:
                    actualizarLibro(scanner, connectionFactory);
                    break;
                case 4:
                    eliminarLibro(scanner, connectionFactory);
                    break;
                case 5:
                    System.out.println("Vuelva Pronto.");
                    break;
                default:
                    System.out.println("Revise bien su seleccion");
            }
        }

        // Cerrar el scanner
        scanner.close();
    }


    //------------------------METODOS CRUD------------------------------

    // CREAR
    private static void crearLibro(Scanner scanner, PostgresqlConnectionFactory connectionFactory) {
        System.out.println("Ingrese el autor del libro:");
        String autor = scanner.nextLine();
        System.out.println("Ingrese el ISBN del libro:");
        String isbn = scanner.nextLine();
        System.out.println("Ingrese el título del libro:");
        String titulo = scanner.nextLine();
        System.out.println("Ingrese el precio del libro:");
        BigDecimal precio = scanner.nextBigDecimal();

        connectionFactory.create()
                .flatMapMany(connection -> connection.createStatement(
                                "INSERT INTO books (author, isbn, title, price) VALUES ($1, $2, $3, $4)")
                        .bind("$1", autor)
                        .bind("$2", isbn)
                        .bind("$3", titulo)
                        .bind("$4", precio)
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
                    //Tuple5<Integer, String, String, String, BigDecimal> objects = new Tuple5<>(id, author, isbn, title, price);
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

    //ACTUALIZAR:
    // Método para actualizar un libro existente
    private static void actualizarLibro(Scanner scanner, PostgresqlConnectionFactory connectionFactory) {
        System.out.println("Ingrese el ID del libro a actualizar:");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consumir la nueva línea
        System.out.println("Ingrese el nuevo autor del libro:");
        String autor = scanner.nextLine();
        System.out.println("Ingrese el nuevo ISBN del libro:");
        String isbn = scanner.nextLine();
        System.out.println("Ingrese el nuevo título del libro:");
        String titulo = scanner.nextLine();
        System.out.println("Ingrese el nuevo precio del libro:");
        BigDecimal precio = scanner.nextBigDecimal();

        connectionFactory.create()
                .flatMapMany(connection -> connection.createStatement(
                                "UPDATE books SET author = $1, isbn = $2, title = $3, price = $4 WHERE id = $5")
                        .bind("$1", autor)
                        .bind("$2", isbn)
                        .bind("$3", titulo)
                        .bind("$4", precio)
                        .bind("$5", id)
                        .execute())
                .flatMap(PostgresqlResult::getRowsUpdated)
                .subscribe(rowsUpdated -> {
                    if (rowsUpdated > 0) {
                        System.out.println("Libro actualizado exitosamente.");
                    } else {
                        System.out.println("No se encontró ningún libro con el ID especificado.");
                    }
                });
    }

    //ELIMINAR
    // Método para eliminar un libro existente
    private static void eliminarLibro(Scanner scanner, PostgresqlConnectionFactory connectionFactory) {
        System.out.println("Ingrese el ID del libro a eliminar:");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consumir la nueva línea

        connectionFactory.create()
                .flatMapMany(connection -> connection.createStatement(
                                "DELETE FROM books WHERE id = $1")
                        .bind("$1", id)
                        .execute())
                .flatMap(PostgresqlResult::getRowsUpdated)
                .subscribe(rowsUpdated -> {
                    if (rowsUpdated > 0) {
                        System.out.println("Libro eliminado exitosamente.");
                    } else {
                        System.out.println("No se encontró ningún libro con el ID especificado.");
                    }
                });
    }


}



