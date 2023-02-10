package com.libsystem.librarymanagementsystem.model;

public class BorrowBook {
    private int borrowNoteID;
    private int bookID;
    private String returnedDate;
    private String Note;

    public BorrowBook() {
    }

    public BorrowBook(int borrowNoteID, int bookID) {
        this.borrowNoteID = borrowNoteID;
        this.bookID = bookID;
    }

    public int getBorrowNoteID() {
        return borrowNoteID;
    }

    public void setBorrowNoteID(int borrowNoteID) {
        this.borrowNoteID = borrowNoteID;
    }

    public int getBookID() {
        return bookID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    public String getReturnedDate() {
        return returnedDate;
    }

    public void setReturnedDate(String returnedDate) {
        this.returnedDate = returnedDate;
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String note) {
        Note = note;
    }
}
