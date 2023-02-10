package com.libsystem.librarymanagementsystem.database;

import com.libsystem.librarymanagementsystem.model.BookType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class BookTypeDAO {
    private Connection connection = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    public boolean addBookType(BookType bookType) {
        String sql = "INSERT INTO TB_BookType (BookTypeID, TypeName) " +
                "VALUES (?, ?)";
        try {
            connection = DataAccess.getConnection();
            assert connection != null;
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, bookType.getBookTypeID());
            preparedStatement.setString(2, bookType.getBookTypeName());

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new book type was inserted.");
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

    public boolean updateBookType(BookType bookType) {
        String sql = "UPDATE TB_BookType SET TypeName=? WHERE BookTypeID=?";
        try {
            connection =  DataAccess.getConnection();
            assert connection != null;
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, bookType.getBookTypeName());
            preparedStatement.setInt(2, bookType.getBookTypeID());


            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("An existing book type was updated successfully!");
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

    public boolean deleteBookType(int bookTypeId) {
        String sql = "DELETE FROM TB_BookType WHERE BookID=?";
        try {
            connection =  DataAccess.getConnection();
            assert connection != null;
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, bookTypeId);

            int rowsDeleted = preparedStatement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("A book type was deleted successfully!");
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

    public String findBookTypeFrom(int bookTypeId) {
        String sql = "SELECT TypeName FROM TB_BookType, TB_book WHERE TB_Book.BookTypeID=TB_BookType.BookTypeID AND TB_Book.BookTypeID=?";
        try {
            connection =  DataAccess.getConnection();

            assert connection != null;
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, bookTypeId);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString("TypeName");
            }

        }catch (SQLException exception) {
            System.out.println("Query request failed.");
        } finally {
            DataAccess.closeConnection(connection);
            DataAccess.closePreparedStatement(preparedStatement);
            DataAccess.closeResultSet(resultSet);
        }
        return "";
    }

    public Map<Integer, String> getAllBookType() {
        Map<Integer, String> typeNames = new HashMap<>();
        String sql = "SELECT BookTypeID,TypeName FROM TB_BookType";
        try {
            connection = DataAccess.getConnection();
            assert connection != null;
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                typeNames.put(resultSet.getInt("BookTypeID"), resultSet.getString("TypeName"));
            }

            return typeNames;
        } catch (SQLException e) {
            System.out.println("Query request failed.");
        } finally {
            DataAccess.closeConnection(connection);
            DataAccess.closePreparedStatement(preparedStatement);
            DataAccess.closeResultSet(resultSet);
        }

        return null;
    }
}
