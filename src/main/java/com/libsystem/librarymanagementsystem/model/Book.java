package com.libsystem.librarymanagementsystem.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Book {
    private final SimpleIntegerProperty bookID;
    private final SimpleIntegerProperty bookTypeID;
    private final SimpleIntegerProperty languageID;
    private final SimpleStringProperty publisher;
    private final SimpleStringProperty bookName;
    private final SimpleStringProperty author;
    private final SimpleStringProperty takenDate;
    private final SimpleStringProperty price;
    private final SimpleIntegerProperty totalAmount;
    private final SimpleIntegerProperty availableAmount;
    private final SimpleStringProperty language;
    private final SimpleStringProperty bookType;
    private int borrowing;

    public int getBorrowing() {
        return borrowing;
    }

    public void setBorrowing(int borrowing) {
        this.borrowing = borrowing;
    }

    public Book(int bookID, int bookTypeID, int languageID, String publisher, String bookName, String author, String takenDate, String price, int totalAmount, int availableAmount, String language, String bookType) {
        this.bookID = new SimpleIntegerProperty(bookID);
        this.bookTypeID = new SimpleIntegerProperty(bookTypeID);
        this.languageID = new SimpleIntegerProperty(languageID);
        this.publisher = new SimpleStringProperty(publisher);
        this.bookName = new SimpleStringProperty(bookName);
        this.author = new SimpleStringProperty(author);
        this.takenDate = new SimpleStringProperty(takenDate);
        this.price = new SimpleStringProperty(price);
        this.totalAmount = new SimpleIntegerProperty(totalAmount);
        this.availableAmount = new SimpleIntegerProperty(availableAmount);
        this.language = new SimpleStringProperty(language);
        this.bookType = new SimpleStringProperty(bookType);
    }

    public int getBookID() {
        return bookID.get();
    }

    public void setBookID(int bookID) {
        this.bookID.set(bookID);
    }

    public int getBookTypeID() {
        return bookTypeID.get();
    }

    public void setBookTypeID(int bookTypeID) {
        this.bookTypeID.set(bookTypeID);
    }

    public int getLanguageID() {
        return languageID.get();
    }

    public void setLanguageID(int languageID) {
        this.languageID.set(languageID);
    }

    public String getPublisher() {
        return publisher.get();
    }

    public void setPublisher(String publisher) {
        this.publisher.set(publisher);
    }

    public String getBookName() {
        return bookName.get();
    }

    public void setBookName(String bookName) {
        this.bookName.set(bookName);
    }

    public String getAuthor() {
        return author.get();
    }

    public void setAuthor(String author) {
        this.author.set(author);
    }

    public String getTakenDate() {
        return takenDate.get();
    }

    public void setTakenDate(String takenDate) {
        this.takenDate.set(takenDate);
    }

    public String getPrice() {
        return price.get();
    }

    public void setPrice(String price) {
        this.price.set(price);
    }

    public int getTotalAmount() {
        return totalAmount.get();
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount.set(totalAmount);
    }

    public int getAvailableAmount() {
        return availableAmount.get();
    }

    public void setAvailableAmount(int availableAmount) {
        this.availableAmount.set(availableAmount);
    }

    public String getLanguage() {
        return language.get();
    }

    public void setLanguage(String language) {
        this.language.set(language);
    }

    public String getBookType() {
        return bookType.get();
    }


    public void setBookType(String bookType) {
        this.bookType.set(bookType);
    }
}
