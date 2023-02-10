package com.libsystem.librarymanagementsystem.model;

public class Reader {
    private int readerID;
    private String address;
    private String readerName;
    private String identityNumber;
    private String dateOfBirth;
    private String phoneNumber;
    private int believePoint;
    private int borrowTimes;

    public Reader() {
    }

    public Reader(String address, String readerName, String identityNumber, String dateOfBirth, String phoneNumber, int believePoint) {
        this.address = address;
        this.readerName = readerName;
        this.identityNumber = identityNumber;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
        this.believePoint = believePoint;
    }

    public Reader(int readerID, String address, String readerName, String identityNumber, String dateOfBirth, String phoneNumber, int believePoint) {
        this.readerID = readerID;
        this.address = address;
        this.readerName = readerName;
        this.identityNumber = identityNumber;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
        this.believePoint = believePoint;
    }

    public int getBorrowTimes() {
        return borrowTimes;
    }

    public void setBorrowTimes(int borrowTimes) {
        this.borrowTimes = borrowTimes;
    }

    public int getReaderID() {
        return readerID;
    }

    public void setReaderID(int readerID) {
        this.readerID = readerID;
    }

    public String getReaderName() {
        return readerName;
    }

    public void setReaderName(String readerName) {
        this.readerName = readerName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIdentityNumber() {
        return identityNumber;
    }

    public void setIdentityNumber(String identityNumber) {
        this.identityNumber = identityNumber;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getBelievePoint() {
        return believePoint;
    }

    public void setBelievePoint(int believePoint) {
        this.believePoint = believePoint;
    }
}
