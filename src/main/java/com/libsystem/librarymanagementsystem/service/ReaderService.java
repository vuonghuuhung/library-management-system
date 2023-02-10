package com.libsystem.librarymanagementsystem.service;

import com.libsystem.librarymanagementsystem.database.ReaderDAO;
import com.libsystem.librarymanagementsystem.model.Reader;

import java.util.ArrayList;

public class ReaderService {
    public static int readerID = -1;
    public static String fullName = "";
    public static String dateOfBirth = "";
    public static String phoneNumber = "";
    public static String identityNumber = "";
    

    public static Reader findReader(String readerID) {
        ReaderDAO readerDAO = new ReaderDAO();
        return readerDAO.findReader(Integer.parseInt(readerID));
    }

    public static boolean addReader(String fullName, String dateOfBirth, String phoneNumber, String identityNumber, String address) {
        ReaderDAO readerDAO = new ReaderDAO();
        return readerDAO.addReader(new Reader(address, fullName, identityNumber, dateOfBirth, phoneNumber, 100));
    }

    public static ArrayList<Reader> findReaders() {
        ReaderDAO readerDAO = new ReaderDAO();
        return readerDAO.findReaders(readerID, fullName, dateOfBirth, phoneNumber, identityNumber);
    }

    public static boolean updateReader(int readerID, String fullName, String dateOfBirth, String address, String phoneNumber, String identityNumber, int believePoint) {
        ReaderDAO readerDAO = new ReaderDAO();
        return readerDAO.updateReader(new Reader(readerID, address, fullName, identityNumber, dateOfBirth, phoneNumber, believePoint));
    }
}
