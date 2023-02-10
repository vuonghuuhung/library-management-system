package com.libsystem.librarymanagementsystem.database;

import com.libsystem.librarymanagementsystem.model.Book;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BookDAO {
    private Connection connection = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    public boolean addBook(Book newBook) {
        String sql = "INSERT INTO TB_Book (BookTypeID, Publisher, LanguageID, BookName, Author, TotalAmount, AvailableAmount, TakenDate, Price) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            connection = DataAccess.getConnection();
            assert connection != null;
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, newBook.getBookTypeID());
            preparedStatement.setString(2, newBook.getPublisher());
            preparedStatement.setInt(3, newBook.getLanguageID());
            preparedStatement.setString(4, newBook.getBookName());
            preparedStatement.setString(5, newBook.getAuthor());
            preparedStatement.setInt(6, newBook.getTotalAmount());
            preparedStatement.setInt(7, newBook.getAvailableAmount());
            preparedStatement.setString(8, newBook.getTakenDate());
            preparedStatement.setString(9, newBook.getPrice());

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new book was inserted.");
                return true;
            }
        } catch (SQLException exception) {
            System.out.println("Query request failed.");
        } finally {
            DataAccess.closeConnection(connection);
            DataAccess.closePreparedStatement(preparedStatement);
            DataAccess.closeResultSet(resultSet);
        }
        return false;
    }

    public boolean updateBook(Book newBook) {
        String sql = "UPDATE TB_Book SET BookTypeID=?, Publisher=?, LanguageID=?, BookName=?, Author=?, TotalAmount=?, AvailableAmount=?, TakenDate=?, Price=? WHERE BookID=?";
        try {
            connection = DataAccess.getConnection();
            assert connection != null;
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, newBook.getBookTypeID());
            preparedStatement.setString(2, newBook.getPublisher());
            preparedStatement.setInt(3, newBook.getLanguageID());
            preparedStatement.setString(4, newBook.getBookName());
            preparedStatement.setString(5, newBook.getAuthor());
            preparedStatement.setInt(6, newBook.getTotalAmount());
            preparedStatement.setInt(7, newBook.getAvailableAmount());
            preparedStatement.setString(8, newBook.getTakenDate());
            preparedStatement.setString(9, newBook.getPrice());
            preparedStatement.setInt(10, newBook.getBookID());

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("An existing book was updated successfully!");
                return true;
            }
        } catch (SQLException exception) {
            System.out.println("Query request failed.");
        } finally {
            DataAccess.closeConnection(connection);
            DataAccess.closePreparedStatement(preparedStatement);
            DataAccess.closeResultSet(resultSet);
        }
        return false;
    }

    public boolean deleteBook(int bookId) {
        String sql = "DELETE FROM TB_Book WHERE BookID=?";
        try {
            connection = DataAccess.getConnection();
            assert connection != null;
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, bookId);

            int rowsDeleted = preparedStatement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("A book was deleted successfully!");
                return true;
            }
        } catch (SQLException exception) {
            System.out.println("Query request failed.");
        } finally {
            DataAccess.closeConnection(connection);
            DataAccess.closePreparedStatement(preparedStatement);
            DataAccess.closeResultSet(resultSet);
        }
        return false;
    }

    public ArrayList<Book> showAllBook() {
        ArrayList<Book> bookList = new ArrayList<>();
        String sql = "SELECT * FROM TB_Book";
        try {
            connection = DataAccess.getConnection();
            assert connection != null;
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Book insBook = new Book(0,0,0,"", "","","","",0,0,"","");
                insBook.setBookID(resultSet.getInt("BookID"));
                insBook.setBookTypeID(resultSet.getInt("BookTypeID"));
                insBook.setPublisher(resultSet.getString("Publisher"));
                insBook.setLanguageID(resultSet.getInt("LanguageID"));
                insBook.setBookName(resultSet.getString("BookName"));
                insBook.setAuthor(resultSet.getString("Author"));
                insBook.setTotalAmount(resultSet.getInt("TotalAmount"));
                insBook.setAvailableAmount(resultSet.getInt("AvailableAmount"));
                insBook.setTakenDate(resultSet.getString("TakenDate"));
                insBook.setPrice(resultSet.getString("Price"));

                bookList.add(insBook);
            }

            return bookList;
        } catch (SQLException e) {
            System.out.println("Query request failed.");
        } finally {
            DataAccess.closeConnection(connection);
            DataAccess.closePreparedStatement(preparedStatement);
            DataAccess.closeResultSet(resultSet);
        }

        return null;
    }

    public ArrayList<Book> findBook(String keyWords) {
        ArrayList<Book> books = new ArrayList<>();
        System.out.println(keyWords);
        String sql = "SELECT * FROM TB_Book WHERE " +
                "BookName like N'%" + keyWords + "%'" +
                " OR Author like N'%" + keyWords + "%'";
        System.out.println(sql);
        try {
            connection = DataAccess.getConnection();
            assert connection != null;
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
//            System.out.println(resultSet.next());
            while (resultSet.next()) {
                Book insBook = new Book(0,0,0,"", "","","","",0,0,"","");
                insBook.setBookID(resultSet.getInt("BookID"));
                insBook.setBookTypeID(resultSet.getInt("BookTypeID"));
                insBook.setPublisher(resultSet.getString("Publisher"));
                insBook.setLanguageID(resultSet.getInt("LanguageID"));
                insBook.setBookName(resultSet.getString("BookName"));
                insBook.setAuthor(resultSet.getString("Author"));
                insBook.setTotalAmount(resultSet.getInt("TotalAmount"));
                insBook.setAvailableAmount(resultSet.getInt("AvailableAmount"));
                insBook.setTakenDate(resultSet.getString("TakenDate"));
                insBook.setPrice(resultSet.getString("Price"));
                System.out.println(insBook);
                books.add(insBook);
            }

            return books;
        } catch (SQLException e) {
            System.out.println("Query request failed.");
        } finally {
            DataAccess.closeConnection(connection);
            DataAccess.closePreparedStatement(preparedStatement);
            DataAccess.closeResultSet(resultSet);
        }

        return null;
    }

    public ArrayList<Book> findBook(String bookName, String author, String takenDate, String price, String publisher, int bookTypeId, int languageID) {
        ArrayList<Book> books = new ArrayList<>();
        String sql = "";
        if (bookTypeId == -1 && languageID == -1) {
            sql = "SELECT * FROM TB_Book WHERE " +
                    "BookName like N'%" + bookName + "%'" +
                    " AND Author like N'%" + author + "%'" +
                    " AND TakenDate like N'%" + takenDate + "%'" +
                    " AND Price like N'%" + price + "%'" +
                    " AND Publisher like N'%" + publisher + "%'";
        }
        if (bookTypeId == -1 && languageID != -1) {
            sql = "SELECT * FROM TB_Book WHERE " +
                    "BookName like N'%" + bookName + "%'" +
                    " AND Author like N'%" + author + "%'" +
                    " AND TakenDate like N'%" + takenDate + "%'" +
                    " AND Price like N'%" + price + "%'" +
                    " AND Publisher like N'%" + publisher + "%'" +
                    " AND LanguageID=" + languageID;
        }
        if (bookTypeId != -1 && languageID == -1) {
            sql = "SELECT * FROM TB_Book WHERE " +
                    "BookName like N'%" + bookName + "%'" +
                    " AND Author like N'%" + author + "%'" +
                    " AND TakenDate like N'%" + takenDate + "%'" +
                    " AND Price like N'%" + price + "%'" +
                    " AND Publisher like N'%" + publisher + "%'" +
                    " AND BookTypeID=" + bookTypeId;
        }
        if (bookTypeId != -1 && languageID != -1) {
            sql = "SELECT * FROM TB_Book WHERE " +
                    "BookName like N'%" + bookName + "%'" +
                    " AND Author like N'%" + author + "%'" +
                    " AND TakenDate like N'%" + takenDate + "%'" +
                    " AND Price like N'%" + price + "%'" +
                    " AND Publisher like N'%" + publisher + "%'" +
                    " AND BookTypeID=" + bookTypeId +
                    " AND LanguageID=" + languageID;
        }
        System.out.println(sql);
        try {
            connection = DataAccess.getConnection();
            assert connection != null;
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
//            System.out.println(resultSet.next());
            while (resultSet.next()) {
                Book insBook = new Book(0,0,0,"", "","","","",0,0,"","");
                insBook.setBookID(resultSet.getInt("BookID"));
                insBook.setBookTypeID(resultSet.getInt("BookTypeID"));
                insBook.setPublisher(resultSet.getString("Publisher"));
                insBook.setLanguageID(resultSet.getInt("LanguageID"));
                insBook.setBookName(resultSet.getString("BookName"));
                insBook.setAuthor(resultSet.getString("Author"));
                insBook.setTotalAmount(resultSet.getInt("TotalAmount"));
                insBook.setAvailableAmount(resultSet.getInt("AvailableAmount"));
                insBook.setTakenDate(resultSet.getString("TakenDate"));
                insBook.setPrice(resultSet.getString("Price"));
                System.out.println(insBook);
                books.add(insBook);
            }

            return books;
        } catch (SQLException e) {
            System.out.println("Query request failed.");
        } finally {
            DataAccess.closeConnection(connection);
            DataAccess.closePreparedStatement(preparedStatement);
            DataAccess.closeResultSet(resultSet);
        }

        return null;
    }

    public String findLanguageName(int languageId) {
        String sql = "SELECT LanguageName FROM TB_Language, TB_Book WHERE TB_Language.LanguageID=TB_Book.LanguageID AND TB_Book.LanguageID=?";
        try {
            connection = DataAccess.getConnection();
            assert connection != null;
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, languageId);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString("LanguageName");
            }
        } catch (SQLException e) {
            System.out.println("Query request failed.");
        } finally {
            DataAccess.closeConnection(connection);
            DataAccess.closePreparedStatement(preparedStatement);
            DataAccess.closeResultSet(resultSet);
        }
        return "";
    }

    public ArrayList<String> getAllColumnName() {
        ArrayList<String> columns = new ArrayList<String>();
        String sql = "SELECT name FROM sys.columns WHERE object_id = OBJECT_ID('TB_Book')";
        try {
            connection = DataAccess.getConnection();
            assert connection != null;
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                columns.add(resultSet.getString("name"));
            }
            return columns;
        } catch (SQLException e) {
            System.out.println("Query request failed.");
        } finally {
            DataAccess.closeConnection(connection);
            DataAccess.closePreparedStatement(preparedStatement);
            DataAccess.closeResultSet(resultSet);
        }
        return null;
    }

    public Map<Integer, String> getAllLanguage() {
        Map<Integer, String> languages = new HashMap<>();
        String sql = "SELECT LanguageID,LanguageName FROM TB_Language";
        try {
            connection = DataAccess.getConnection();
            assert connection != null;
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                languages.put(resultSet.getInt("LanguageID"), resultSet.getString("LanguageName"));
            }

            return languages;
        } catch (SQLException e) {
            System.out.println("Query request failed.");
        } finally {
            DataAccess.closeConnection(connection);
            DataAccess.closePreparedStatement(preparedStatement);
            DataAccess.closeResultSet(resultSet);
        }

        return null;
    }

    public Book findBookByID(int bookID) {
        ArrayList<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM TB_Book WHERE BookID=?";
        try {
            connection = DataAccess.getConnection();
            assert connection != null;
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, bookID);
            resultSet = preparedStatement.executeQuery();
//            System.out.println(resultSet.next());
            while (resultSet.next()) {
                Book insBook = new Book(0,0,0,"", "","","","",0,0,"","");
                insBook.setBookID(resultSet.getInt("BookID"));
                insBook.setBookTypeID(resultSet.getInt("BookTypeID"));
                insBook.setPublisher(resultSet.getString("Publisher"));
                insBook.setLanguageID(resultSet.getInt("LanguageID"));
                insBook.setBookName(resultSet.getString("BookName"));
                insBook.setAuthor(resultSet.getString("Author"));
                insBook.setTotalAmount(resultSet.getInt("TotalAmount"));
                insBook.setAvailableAmount(resultSet.getInt("AvailableAmount"));
                insBook.setTakenDate(resultSet.getString("TakenDate"));
                insBook.setPrice(resultSet.getString("Price"));
                System.out.println(insBook);
                books.add(insBook);
            }

            if (books.size() > 0)
                return books.get(0);
        } catch (SQLException e) {
            System.out.println("Query request failed.");
        } finally {
            DataAccess.closeConnection(connection);
            DataAccess.closePreparedStatement(preparedStatement);
            DataAccess.closeResultSet(resultSet);
        }

        return null;
    }

    public ArrayList<Book> getNewestBooks() {
        ArrayList<Book> books = new ArrayList<>();
        String sql = """
                SELECT TOP(10) *
                FROM TB_Book
                ORDER BY BookID DESC""";
        try {
            connection = DataAccess.getConnection();
            assert connection != null;
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
//            System.out.println(resultSet.next());
            while (resultSet.next()) {
                Book insBook = new Book(0,0,0,"", "","","","",0,0,"","");
                insBook.setBookID(resultSet.getInt("BookID"));
                insBook.setBookTypeID(resultSet.getInt("BookTypeID"));
                insBook.setPublisher(resultSet.getString("Publisher"));
                insBook.setLanguageID(resultSet.getInt("LanguageID"));
                insBook.setBookName(resultSet.getString("BookName"));
                insBook.setAuthor(resultSet.getString("Author"));
                insBook.setTotalAmount(resultSet.getInt("TotalAmount"));
                insBook.setAvailableAmount(resultSet.getInt("AvailableAmount"));
                insBook.setTakenDate(resultSet.getString("TakenDate"));
                insBook.setPrice(resultSet.getString("Price"));
                System.out.println(insBook);
                books.add(insBook);
            }
            return books;
        } catch (SQLException e) {
            System.out.println("Query request failed.");
        } finally {
            DataAccess.closeConnection(connection);
            DataAccess.closePreparedStatement(preparedStatement);
            DataAccess.closeResultSet(resultSet);
        }

        return null;
    }

    public ArrayList<Book> getBookBorrowingStatus() {
        ArrayList<Book> books = new ArrayList<>();
        String sql = """
                SELECT bb.BookID, b.Author, b.AvailableAmount, b.BookName, b.BookTypeID,
                b.LanguageID, b.Price, b.Publisher, b.TakenDate, b.TotalAmount, COUNT(bb.BorrowNoteID) as 'borrowing'
                FROM TB_BorrowBook bb
                JOIN TB_BorrowNote bn on bn.BorrowNoteID = bb.BorrowNoteID
                JOIN TB_Book b on b.BookID = bb.BookID
                where bn.Status = 'borrowing'
                group by bb.BookID, b.Author, b.AvailableAmount, b.BookName, b.BookTypeID,
                b.LanguageID, b.Price, b.Publisher, b.TakenDate, b.TotalAmount""";
        try {
            connection = DataAccess.getConnection();
            assert connection != null;
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
//            System.out.println(resultSet.next());
            while (resultSet.next()) {
                Book insBook = new Book(0,0,0,"", "","","","",0,0,"","");
                insBook.setBookID(resultSet.getInt("BookID"));
                insBook.setBookTypeID(resultSet.getInt("BookTypeID"));
                insBook.setPublisher(resultSet.getString("Publisher"));
                insBook.setLanguageID(resultSet.getInt("LanguageID"));
                insBook.setBookName(resultSet.getString("BookName"));
                insBook.setAuthor(resultSet.getString("Author"));
                insBook.setTotalAmount(resultSet.getInt("TotalAmount"));
                insBook.setAvailableAmount(resultSet.getInt("AvailableAmount"));
                insBook.setTakenDate(resultSet.getString("TakenDate"));
                insBook.setPrice(resultSet.getString("Price"));
                insBook.setBorrowing(resultSet.getInt("borrowing"));
                books.add(insBook);
            }
            return books;
        } catch (SQLException e) {
            System.out.println("Query request failed.");
        } finally {
            DataAccess.closeConnection(connection);
            DataAccess.closePreparedStatement(preparedStatement);
            DataAccess.closeResultSet(resultSet);
        }

        return null;
    }

    public boolean addBatchBook(String filePath) {
        String sql = "INSERT INTO TB_Book (BookTypeID, Publisher, LanguageID, BookName, Author, TotalAmount, AvailableAmount, TakenDate, Price) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            connection = DataAccess.getConnection();
            assert connection != null;
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(sql);

            BufferedReader lineReader = new BufferedReader(new FileReader(filePath));
            int batchSize = 200;

            String lineText = null;
            int count = 0;
            lineReader.readLine();
            while ((lineText = lineReader.readLine()) != null) {
                String[] data = lineText.split(",");
                String bookTypeID = data[0];
                String publisher = data[1];
                String languageID = data[2];
                String bookName = data[3];
                String author = data[4];
                String totalAmount = data[5];
                String availableAmount = data[6];
                String takenDate = data[7];
                String price = data[8];

                preparedStatement.setInt(1, Integer.parseInt(bookTypeID));
                preparedStatement.setString(2, publisher);
                preparedStatement.setInt(3, Integer.parseInt(languageID));
                preparedStatement.setString(4, bookName);
                preparedStatement.setString(5, author);
                preparedStatement.setInt(6, Integer.parseInt(totalAmount));
                preparedStatement.setInt(7, Integer.parseInt(availableAmount));
                preparedStatement.setString(8, takenDate);
                preparedStatement.setString(9, price);
                preparedStatement.addBatch();
                if(count == batchSize) {
                    break;
                }
                count++;
            }

            lineReader.close();
            preparedStatement.executeBatch();
            connection.commit();
            System.out.println("Data has been inserted successfully.");
        } catch (SQLException exception) {
            exception.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            DataAccess.closeConnection(connection);
            DataAccess.closePreparedStatement(preparedStatement);
            DataAccess.closeResultSet(resultSet);
        }
        return false;
    }
}
