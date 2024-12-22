package com.example.courseproject.model;

public class Course {
    private int id;
    private int category;
    private String date;
    private String author;
    private String color;
    private String img;
    private String title;
    private String text;
    private String publisher;
    private int pages;
    private String language;
    private String form;
    private String theme;
    private String link;

    // Конструктор
    public Course(int id, String img, String title, String date, String author, String color,
                  int category, String publisher, int pages, String language, String form, String theme, String link) {
        this.id = id;
        this.img = img;
        this.title = title;
        this.date = date;
        this.author = author;
        this.color = color;
        this.text = text;
        this.category = category;
        this.publisher = publisher;
        this.pages = pages;
        this.language = language;
        this.form = form;
        this.theme = theme;
        this.link = link;
    }

    // Геттеры и сеттеры
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getForm() {
        return form;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
