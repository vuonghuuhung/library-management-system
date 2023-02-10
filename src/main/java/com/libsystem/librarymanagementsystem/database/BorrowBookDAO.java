package com.libsystem.librarymanagementsystem.database;

import com.libsystem.librarymanagementsystem.model.Book;
import com.libsystem.librarymanagementsystem.model.BorrowBook;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BorrowBookDAO {
    private Connection connection = DataAccess.getConnection();
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    public boolean deleteBorrowBook(int borrowNoteID) {
        String sql = "DELETE * FROM TB_BorrowBook WHERE BorrowNoteID=?";
        try {
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, borrowNoteID);

            int rowsDeleted = preparedStatement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("A borrow note details was deleted successfully!");
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

    public boolean updateBorrowBook(BorrowBook borrowNoteDetails) {
        String sql = "UPDATE TB_BorrowBook SET BookID=?, ReturnDate=?, Note=? WHERE BorrowNoteID=?";
        try {
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, borrowNoteDetails.getBookID());
            preparedStatement.setString(2, borrowNoteDetails.getReturnedDate());
            preparedStatement.setString(3, borrowNoteDetails.getNote());


            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("An existing borrow note details was updated successfully!");
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

    public boolean addBorrowBook(BorrowBook borrowNoteDetails) {
        String sql = "INSERT INTO TB_BorrowBook (BorrowNoteID, BookID) " +
                "VALUES (?, ?)";
        try {
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, borrowNoteDetails.getBorrowNoteID());
            preparedStatement.setInt(2, borrowNoteDetails.getBookID());

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new borrow note details type was inserted.");
                return true;
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        } finally {
            DataAccess.closeConnection(connection);
            DataAccess.closePreparedStatement(preparedStatement);
            DataAccess.closeResultSet(resultSet);
        }
        return false;
    }

    public ArrayList<Book> getAllBookFromNoteID(int noteID) {
        ArrayList<Book> books = new ArrayList<Book>();
        String sql = """
                select bb.BookID, b.BookName
                from TB_BorrowBook bb, TB_Book b
                where BorrowNoteID = ? and bb.BookID = b.BookID""";
        try {
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, noteID);

            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Book book = new Book(0,0,0,"", "","","","",0,0,"","");
                book.setBookID(resultSet.getInt("BookID"));
                book.setBookName(resultSet.getString("BookName"));

                books.add(book);
            }

            return books;
        } catch (SQLException exception) {
            exception.printStackTrace();
        } finally {
            DataAccess.closeConnection(connection);
            DataAccess.closePreparedStatement(preparedStatement);
            DataAccess.closeResultSet(resultSet);
        }
        return null;
    }
}
