package org.example.modelo;

import java.math.BigDecimal;

public class Book {
    private Integer id;
    private String author;
    private String isbn;
    private String title;
    private BigDecimal price;

    public Book(){}

    public Book(Integer id, String author, String isbn, String title, BigDecimal price) {
        this.id = id;
        this.author = author;
        this.isbn = isbn;
        this.title = title;
        this.price = price;
    }

    // getters y setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", author='" + author + '\'' +
                ", isbn='" + isbn + '\'' +
                ", title='" + title + '\'' +
                ", price=" + price +
                '}';
    }
}
