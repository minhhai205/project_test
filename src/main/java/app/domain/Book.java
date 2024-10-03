package app.domain;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Book {
    private SimpleIntegerProperty id;
    private SimpleStringProperty title;
    private SimpleStringProperty author;
    private SimpleIntegerProperty year;
    private SimpleIntegerProperty pages;

    public Book(int id, String title, String author, int year, int pages) {
        this.id = new SimpleIntegerProperty(id);
        this.title = new SimpleStringProperty(title);
        this.author = new SimpleStringProperty(author);
        this.year = new SimpleIntegerProperty(year);
        this.pages = new SimpleIntegerProperty(pages);
    }

    public int getId() {
        return id.get();
    }

    public String getTitle() {
        return title.get();
    }

    public String getAuthor() {
        return author.get();
    }

    public int getYear() {
        return year.get();
    }

    public int getPages() {
        return pages.get();
    }

    public void setId(int id) {
        this.id = new SimpleIntegerProperty(id);
    }

    public void setTitle(String title) {
        this.title = new SimpleStringProperty(title);
    }

    public void setAuthor(String author) {
        this.author = new SimpleStringProperty(author);
    }

    public void setYear(int year) {
        this.year = new SimpleIntegerProperty(year);
    }

    public void setPages(int pages) {
        this.pages = new SimpleIntegerProperty(pages);
    }

}
