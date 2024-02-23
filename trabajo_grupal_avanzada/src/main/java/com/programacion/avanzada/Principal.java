package com.programacion.avanzada;

import io.r2dbc.postgresql.PostgresqlConnectionConfiguration;
import io.r2dbc.postgresql.PostgresqlConnectionFactory;

import java.math.BigDecimal;
import java.util.Scanner;

public class Principal {
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

        //
        BookRepository.createBook("Test2","test","TestTitle",new BigDecimal(15), connectionFactory);
        BookRepository.readAllBooks(connectionFactory);
        BookRepository.updateBook(4,"update","test","TestTitle",new BigDecimal(1),connectionFactory);
        BookRepository.deleteBook(18,connectionFactory);

        scanner.nextLine();



    }

}
