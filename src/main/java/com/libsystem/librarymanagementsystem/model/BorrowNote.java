package com.libsystem.librarymanagementsystem.model;

import java.util.ArrayList;

public class BorrowNote {
    private int borrowNoteID;
    private int readerID;
    private String borrowDate;
    private String promiseReturnDate;
    private int amount;
    private String deposit;
    private String status;
    private int userID;
    private ArrayList<BorrowBook> bookList;
    private String userName;
    private String readerName;

    public BorrowNote() {
    }

    public BorrowNote(int readerID, String borrowDate, String promiseReturnDate, int amount, String deposit, String status, int userID) {
        this.readerID = readerID;
        this.borrowDate = borrowDate;
        this.promiseReturnDate = promiseReturnDate;
        this.amount = amount;
        this.deposit = deposit;
        this.status = status;
        this.userID = userID;
    }

    public String getReaderName() {
        return readerName;
    }

    public void setReaderName(String readerName) {
        this.readerName = readerName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public ArrayList<BorrowBook> getBookList() {
        return bookList;
    }

    public void setBookList(ArrayList<BorrowBook> bookList) {
        this.bookList = bookList;
    }

    public int getBorrowNoteID() {
        return borrowNoteID;
    }

    public void setBorrowNoteID(int borrowNoteID) {
        this.borrowNoteID = borrowNoteID;
    }

    public int getReaderID() {
        return readerID;
    }

    public void setReaderID(int readerID) {
        this.readerID = readerID;
    }

    public String getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(String borrowDate) {
        this.borrowDate = borrowDate;
    }

    public String getPromiseReturnDate() {
        return promiseReturnDate;
    }

    public void setPromiseReturnDate(String promiseDate) {
        this.promiseReturnDate = promiseDate;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getDeposit() {
        return deposit;
    }

    public void setDeposit(String deposit) {
        this.deposit = deposit;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
}
