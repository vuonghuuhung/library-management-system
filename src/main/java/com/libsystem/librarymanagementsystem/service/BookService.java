package com.libsystem.librarymanagementsystem.service;

import com.libsystem.librarymanagementsystem.database.BookDAO;
import com.libsystem.librarymanagementsystem.database.BookTypeDAO;
import com.libsystem.librarymanagementsystem.database.BorrowNoteDAO;
import com.libsystem.librarymanagementsystem.model.Book;
import com.libsystem.librarymanagementsystem.model.Reader;
import javafx.collections.ObservableList;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BookService {
    public static String language;
    public static String bookType;
    public static String bookName = "";
    public static String author = "";
    public static String takenDate = "";
    public static String price = "";
    public static String publisher = "";

    public static ArrayList<Book> findBook(String findingPhrase) {
        BookDAO bookDAO = new BookDAO();
        ArrayList<Book> books = new ArrayList<Book>(bookDAO.findBook(findingPhrase));
        ArrayList<Book> bookBorrowing = bookDAO.getBookBorrowingStatus();
        Map<Integer, Integer> idToBorrowNumber = new HashMap<>();
        bookBorrowing.forEach(book -> {
            idToBorrowNumber.put(book.getBookID(), book.getBorrowing());
        });
        books.forEach(book -> {
            book.setAvailableAmount(book.getAvailableAmount() - idToBorrowNumber.get(book.getBookID()));
        });
        Map<Integer,String> languages = BookService.getAllLanguage();
        Map<Integer,String> bookTypes = BookService.getBookType();
        books.forEach(book -> {
            book.setLanguage(languages.get(book.getLanguageID()));
            book.setBookType(bookTypes.get(book.getBookTypeID()));
        });
        return books;
    }

    public static Map<Integer, String> getAllLanguage() {
        Map<Integer, String> languages = new HashMap<Integer, String>();
        BookDAO bookDAO = new BookDAO();
        languages = bookDAO.getAllLanguage();
        return languages;
    }

    public static Map<Integer, String> getBookType() {
        Map<Integer, String> bookTypes = new HashMap<Integer, String>();
        BookTypeDAO bookDAO = new BookTypeDAO();
        bookTypes = bookDAO.getAllBookType();
        return bookTypes;
    }

    public static ArrayList<Book> advancedFindBook() {
        BookDAO bookDAO = new BookDAO();
        Map<Integer,String> languages = BookService.getAllLanguage();
        Map<Integer,String> bookTypes = BookService.getBookType();
        ArrayList<Book> books = new ArrayList<Book>(bookDAO.findBook(bookName, author, takenDate, price, publisher, !bookType.equals("") ? getKey(bookTypes, bookType) : -1, !language.equals("") ? getKey(languages, language) : -1));
        ArrayList<Book> bookBorrowing = bookDAO.getBookBorrowingStatus();
        Map<Integer, Integer> idToBorrowNumber = new HashMap<>();
        bookBorrowing.forEach(book -> {
            idToBorrowNumber.put(book.getBookID(), book.getBorrowing());
        });
        books.forEach(book -> {
            book.setAvailableAmount(book.getAvailableAmount() - idToBorrowNumber.get(book.getBookID()));
        });
        books.forEach(book -> {
            book.setLanguage(languages.get(book.getLanguageID()));
            book.setBookType(bookTypes.get(book.getBookTypeID()));
        });
        return books;
    }

    public static <K,V> K getKey(Map<K, V> map, V value) {
        return map.entrySet().stream().filter(entry -> value.equals(entry.getValue()))
                .findFirst().map(Map.Entry::getKey).orElse(null);
    }

    public static Book findBookByID(String bookID) {
        BookDAO bookDAO = new BookDAO();
        Book book = bookDAO.findBookByID(Integer.parseInt(bookID));
        if (book == null) return null;
        Map<Integer,String> languages = BookService.getAllLanguage();
        Map<Integer,String> bookTypes = BookService.getBookType();
        book.setLanguage(languages.get(book.getLanguageID()));
        book.setBookType(bookTypes.get(book.getBookTypeID()));
        return book;
    }

    public static ArrayList<Reader> getTopBorrowed() {
        BorrowNoteDAO borrowNoteDAO = new BorrowNoteDAO();
        return borrowNoteDAO.getTopBorrowed();
    }

    public static ArrayList<Book> getNewestBook() {
        BookDAO bookDAO = new BookDAO();
        return bookDAO.getNewestBooks();
    }

    public static boolean addBook(String bookName, String price, String author, String publisher, String availableNumber, String takenDate, String language, String bookType) {
        Map<Integer, String> languages = getAllLanguage();
        Map<Integer, String> bookTypes = getBookType();
        Book book = new Book(0, getKey(bookTypes,bookType), getKey(languages, language), publisher, bookName, author, takenDate, price, Integer.parseInt(availableNumber), Integer.parseInt(availableNumber), language, bookType);
        BookDAO bookDAO = new BookDAO();
        return bookDAO.addBook(book);
    }

    public static boolean updateBook(String bookName, String price, String author, String publisher, String availableNumber, String takenDate, String language, String bookType, Book book) {
        Map<Integer, String> languages = getAllLanguage();
        Map<Integer, String> bookTypes = getBookType();
        Book newBook = new Book(book.getBookID(), getKey(bookTypes,bookType), getKey(languages, language), publisher, bookName, author, takenDate, price, Integer.parseInt(availableNumber), Integer.parseInt(availableNumber), language, bookType);
        BookDAO bookDAO = new BookDAO();
        return bookDAO.updateBook(newBook);
    }
}


