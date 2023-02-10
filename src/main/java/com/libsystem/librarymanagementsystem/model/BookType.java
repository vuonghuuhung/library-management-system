package com.libsystem.librarymanagementsystem.model;

public class BookType {
    private int bookTypeID;
    private String typeName;

    public int getBookTypeID() {
        return bookTypeID;
    }

    public void setBookTypeID(int bookTypeID) {
        this.bookTypeID = bookTypeID;
    }

    public String getBookTypeName() {
        return typeName;
    }

    public void setBookTypeName(String bookTypeName) {
        this.typeName = bookTypeName;
    }
}
