package com.libsystem.librarymanagementsystem.service;

import com.libsystem.librarymanagementsystem.database.BorrowBookDAO;
import com.libsystem.librarymanagementsystem.database.BorrowNoteDAO;
import com.libsystem.librarymanagementsystem.database.ReaderDAO;
import com.libsystem.librarymanagementsystem.model.Book;
import com.libsystem.librarymanagementsystem.model.BorrowBook;
import com.libsystem.librarymanagementsystem.model.BorrowNote;
import com.libsystem.librarymanagementsystem.model.Reader;

import java.util.ArrayList;

public class BorrowReturnService {
    public static int readerID = -1;
    public static String fullName = "";
    public static String borrowDate = "";
    public static String promisedReturnDate = "";

    public static BorrowNote findNote(String noteID) {
        BorrowNoteDAO borrowNoteDAO = new BorrowNoteDAO();
        return borrowNoteDAO.findNote(Integer.parseInt(noteID));
    }

    public static ArrayList<BorrowNote> getWarningNote() {
        BorrowNoteDAO borrowNoteDAO = new BorrowNoteDAO();
        return borrowNoteDAO.getWarningBorrowNote();
    }

    public static ArrayList<BorrowNote> getNoteOfReaderID(int readerID) {
        BorrowNoteDAO borrowNoteDAO = new BorrowNoteDAO();
        return borrowNoteDAO.findNoteByReaderID(readerID);
    }

    public static ArrayList<BorrowNote> findNotes() {
        BorrowNoteDAO borrowNoteDAO = new BorrowNoteDAO();
        return borrowNoteDAO.findBorrowNotes(readerID, fullName, borrowDate, promisedReturnDate);
    }

    public static boolean addNote(String readerID, String borrowDate, String promisedReturnDate, String deposit, ArrayList<Book> books, int userID) {
        BorrowNoteDAO borrowNoteDAO = new BorrowNoteDAO();

        // TO-DO
        Reader reader = ReaderService.findReader(readerID);
        if (reader != null) {
            BorrowNote borrowNote = new BorrowNote(Integer.parseInt(readerID), borrowDate, promisedReturnDate, books.size(), deposit, "Borrowing", userID);
            int borrowNoteID = borrowNoteDAO.addBorrowNote(borrowNote);
            if (borrowNoteID != -1) {
                for (Book book : books) {
                    BorrowBookDAO borrowBookDAO = new BorrowBookDAO();
                    BorrowBook borrowBook = new BorrowBook(borrowNoteID, book.getBookID());
                    borrowBookDAO.addBorrowBook(borrowBook);
                }
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public static boolean deleteBorrowNote(int borrowNoteID) {
        BorrowBookDAO borrowBookDAO = new BorrowBookDAO();
        if (borrowBookDAO.deleteBorrowBook(borrowNoteID)) {
            BorrowNoteDAO borrowNoteDAO = new BorrowNoteDAO();
            return borrowNoteDAO.deleteBorrowNote(borrowNoteID);
        }
        return false;
    }

    public static boolean updateNote(int borrowNoteID, String promisedReturnDate, String status, BorrowNote borrowNote) {
        BorrowNoteDAO borrowNoteDAO = new BorrowNoteDAO();
        borrowNote.setPromiseReturnDate(promisedReturnDate);
        borrowNote.setStatus(status);
        return borrowNoteDAO.updateBorrowNote(borrowNote);
    }
}
