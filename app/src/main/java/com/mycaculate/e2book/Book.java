package com.mycaculate.e2book;

import android.graphics.Bitmap;

public class Book {
        private Bitmap pic;
        private String book_name;
        private String catalog;
        private String publisher;
        private String author;

    public Book(Bitmap pic, String book_name, String catalog, String publisher, String author) {
        this.pic = pic;
        this.book_name = book_name;
        this.catalog = catalog;
        this.publisher = publisher;
        this.author = author;
    }

    public Bitmap getPic() {
        return pic;
    }

    public void setPic(Bitmap pic) {
        this.pic = pic;
    }

    public String getBook_name() {
        return book_name;
    }

    public void setBook_name(String book_name) {
        this.book_name = book_name;
    }

    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
