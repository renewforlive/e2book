package com.mycaculate.e2book;

import android.graphics.Bitmap;

public class Book {
        private Bitmap pic;
        private int book_id;
        private String book_name;
        private String catalog;
        private String publisher;
        private String author;
        private String create_time;

    public Book(Bitmap pic, int book_id, String book_name, String catalog, String publisher, String author,String create_time) {
        this.pic = pic;
        this.book_id = book_id;
        this.book_name = book_name;
        this.catalog = catalog;
        this.publisher = publisher;
        this.author = author;
        this.create_time = create_time;
    }

    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
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

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }
}
