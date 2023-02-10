package com.libsystem.librarymanagementsystem.database;

import com.libsystem.librarymanagementsystem.model.Reader;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ReaderDAO {
    private Connection connection = DataAccess.getConnection();
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    // Continue...
    public boolean authenticateReader() {

        return true;
    }

    public boolean addReader(Reader reader) {
        String sql = "INSERT INTO TB_Reader (ReaderName, DateOfBirth, PhoneNumber, IdentityNumber, Address, BelievePoint) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        try {
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, reader.getReaderName());
            preparedStatement.setString(2, reader.getDateOfBirth());
            preparedStatement.setString(3, reader.getPhoneNumber());
            preparedStatement.setString(4, reader.getIdentityNumber());
            preparedStatement.setString(5, reader.getAddress());
            preparedStatement.setInt(6, reader.getBelievePoint());

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new reader was inserted.");
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

    public boolean updateReader(Reader reader) {
        String sql = "UPDATE TB_Reader SET ReaderName=?, DateOfBirth=?, PhoneNumber=?, IdentityNumber=?, Address=?, BelievePoint=? WHERE ReaderID=?";
        try {
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, reader.getReaderName());
            preparedStatement.setString(2, reader.getDateOfBirth());
            preparedStatement.setString(3, reader.getPhoneNumber());
            preparedStatement.setString(4, reader.getIdentityNumber());
            preparedStatement.setString(5, reader.getAddress());
            preparedStatement.setInt(6, reader.getBelievePoint());
            preparedStatement.setInt(7, reader.getReaderID());

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("An existing reader was updated successfully!");
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

    public boolean deleteReader(int readerId) {
        String sql = "DELETE FROM TB_Reader WHERE ReaderID=?";
        try {
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, readerId);

            int rowsDeleted = preparedStatement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("A reader was deleted successfully!");
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

    public Reader findReader(int readerID) {
        ArrayList<Reader> readers = new ArrayList<>();
        String sql = "SELECT * FROM TB_Reader WHERE ReaderID=?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, readerID);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Reader insReader = new Reader();
                insReader.setReaderID(resultSet.getInt("ReaderID"));
                insReader.setReaderName(resultSet.getString("ReaderName"));
                insReader.setDateOfBirth(resultSet.getString("DateOfBirth"));
                insReader.setPhoneNumber(resultSet.getString("PhoneNumber"));
                insReader.setIdentityNumber(resultSet.getString("IdentityNumber"));
                insReader.setAddress(resultSet.getString("Address"));
                insReader.setBelievePoint(resultSet.getInt("BelievePoint"));

                readers.add(insReader);
            }
            if (readers.size() > 0)
                return readers.get(0);
        } catch (SQLException e) {
            System.out.println("Query request failed.");
        } finally {
            DataAccess.closeConnection(connection);
            DataAccess.closePreparedStatement(preparedStatement);
            DataAccess.closeResultSet(resultSet);
        }

        return null;
    }

    public ArrayList<Reader> findReaders(int readerID, String fullName, String dateOfBirth, String phoneNumber, String identityNumber) {
        ArrayList<Reader> readers = new ArrayList<>();
        String sql;
        if (readerID == -1) {
            sql = "SELECT * FROM TB_Reader WHERE " +
                    "ReaderName like N'%" + fullName + "%'" +
                    " AND DateOfBirth like N'%" + dateOfBirth + "%'" +
                    " AND PhoneNumber like N'%" + phoneNumber + "%'" +
                    " AND IdentityNumber like N'%" + identityNumber + "%'";
        } else {
            sql = "SELECT * FROM TB_Reader WHERE ReaderID=" + readerID;
        }
        try {
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Reader insReader = new Reader();
                insReader.setReaderID(resultSet.getInt("ReaderID"));
                insReader.setReaderName(resultSet.getString("ReaderName"));
                insReader.setDateOfBirth(resultSet.getString("DateOfBirth"));
                insReader.setPhoneNumber(resultSet.getString("PhoneNumber"));
                insReader.setIdentityNumber(resultSet.getString("IdentityNumber"));
                insReader.setAddress(resultSet.getString("Address"));
                insReader.setBelievePoint(resultSet.getInt("BelievePoint"));

                readers.add(insReader);
            }
                return readers;
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
