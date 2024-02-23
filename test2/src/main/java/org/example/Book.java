package org.example;

import java.math.BigDecimal;

public class Book {
    private Integer id;
    private String autor;
    private String isbn;
    //ISBN significa "International Standard Book Number" (Número Internacional Normalizado del Libro).
    private String titulo;
    private BigDecimal precio;

    public Book(){}

    public Book(Integer id, String autor, String isbn, String titulo, BigDecimal precio) {
        this.id = id;
        this.autor = autor;
        this.isbn = isbn;
        this.titulo = titulo;
        this.precio = precio;
    }


    public Integer getId() {

        return id;
    }

    public void setId(Integer id) {

        this.id = id;
    }

    public String getAutor() {

        return autor;
    }

    public void setAutor(String autor) {

        this.autor = autor;
    }

    public String getIsbn() {

        return isbn;
    }

    public void setIsbn(String isbn) {

        this.isbn = isbn;
    }

    public String getTitulo()
    {
        return titulo;
    }

    public void setTitulo(String titulo) {

        this.titulo = titulo;
    }

    public BigDecimal getPrecio() {

        return precio;
    }

    public void setPrecio(BigDecimal precio) {

        this.precio = precio;
    }

    @Override
    public String toString() {
        return "Book{" +
                "\n  Autor='" + autor + '\'' +
                ",\n  isbn='" + isbn + '\'' +
                ",\n  Titulo='" + titulo + '\'' +
                ",\n  Precio=" + precio +
                "\n}";
    }
}
