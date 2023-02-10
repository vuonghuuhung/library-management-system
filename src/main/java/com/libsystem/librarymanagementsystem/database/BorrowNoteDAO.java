package com.libsystem.librarymanagementsystem.database;

import com.libsystem.librarymanagementsystem.model.BorrowNote;
import com.libsystem.librarymanagementsystem.model.Reader;

import java.sql.*;
import java.util.ArrayList;

public class BorrowNoteDAO {
    private Connection connection = DataAccess.getConnection();
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    public int addBorrowNote(BorrowNote borrowNote) {
        String sql = """
                INSERT INTO TB_BorrowNote\s
                (ReaderID, Amount, Deposit, BorrowDate, PromisedReturnDate, Status, UserID)
                VALUES (?, ?, ?, ?, ?, ?, ?)
                SELECT SCOPE_IDENTITY() AS "BorrowNoteID\"""";
        try {
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, borrowNote.getReaderID());
            preparedStatement.setInt(2, borrowNote.getAmount());
            preparedStatement.setString(3, borrowNote.getDeposit());
            preparedStatement.setString(4, borrowNote.getBorrowDate());
            preparedStatement.setString(5, borrowNote.getPromiseReturnDate());
            preparedStatement.setString(6, borrowNote.getStatus());
            preparedStatement.setInt(7, borrowNote.getUserID());

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int borrowNoteID = resultSet.getInt("BorrowNoteID");
                System.out.println(borrowNoteID);
                return borrowNoteID;
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        } finally {
            DataAccess.closeConnection(connection);
            DataAccess.closePreparedStatement(preparedStatement);
            DataAccess.closeResultSet(resultSet);
        }
        return -1;
    }

    public  boolean updateBorrowNote(BorrowNote borrowNote) {
        String sql = "UPDATE TB_BorrowNote SET ReaderID=?, Amount=?, Deposit=?, BorrowDate=?, PromisedReturnDate=?, Status=?, UserID=? WHERE BorrowNoteID=?";
        try {
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, borrowNote.getReaderID());
            preparedStatement.setInt(2, borrowNote.getAmount());
            preparedStatement.setString(3, borrowNote.getDeposit());
            preparedStatement.setString(4, borrowNote.getBorrowDate());
            preparedStatement.setString(5, borrowNote.getPromiseReturnDate());
            preparedStatement.setString(6, borrowNote.getStatus());
            preparedStatement.setInt(7, borrowNote.getUserID());
            preparedStatement.setInt(8, borrowNote.getBorrowNoteID());


            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("An existing borrow note was updated successfully!");
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

    public boolean deleteBorrowNote(int borrowNoteID) {
        String sql = "DELETE * FROM TB_BorrowNote WHERE BorrowNoteID=?";
        try {
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, borrowNoteID);

            int rowsDeleted = preparedStatement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("A borrow note was deleted successfully!");
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

    public BorrowNote findNote(int noteID) {
        ArrayList<BorrowNote> notes = new ArrayList<>();
        String sql = "SELECT * FROM TB_BorrowNote WHERE BorrowNoteID=?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, noteID);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                BorrowNote note = new BorrowNote();
                note.setBorrowNoteID(resultSet.getInt("BorrowNoteID"));
                note.setReaderID(resultSet.getInt("ReaderID"));
                note.setAmount(resultSet.getInt("Amount"));
                note.setDeposit(resultSet.getString("Deposit"));
                note.setBorrowDate(resultSet.getString("BorrowDate"));
                note.setPromiseReturnDate(resultSet.getString("PromisedReturnDate"));
                note.setStatus(resultSet.getString("Status"));
                note.setUserID(resultSet.getInt("UserID"));

                notes.add(note);
            }
            if (notes.size() > 0)
                return notes.get(0);
        } catch (SQLException e) {
            System.out.println("Query request failed.");
        } finally {
            DataAccess.closeConnection(connection);
            DataAccess.closePreparedStatement(preparedStatement);
            DataAccess.closeResultSet(resultSet);
        }

        return null;
    }

    public ArrayList<Reader> getTopBorrowed() {
        ArrayList<Reader> readers = new ArrayList<>();
        String sql = """
                SELECT TOP(10) r.ReaderID, r.ReaderName, COUNT(bn.BorrowNoteID) as "BorrowTimes"
                FROM TB_BorrowNote bn
                JOIN TB_Reader r ON r.ReaderID = bn.ReaderID
                GROUP BY r.ReaderID, r.ReaderName
                ORDER BY COUNT(bn.BorrowNoteID) DESC""";
        try {
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Reader reader = new Reader();
                reader.setReaderID(resultSet.getInt("ReaderID"));
                reader.setReaderName(resultSet.getString("ReaderName"));
                reader.setBorrowTimes(resultSet.getInt("BorrowTimes"));

                readers.add(reader);
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

    public ArrayList<BorrowNote> getWarningBorrowNote() {
        ArrayList<BorrowNote> notes = new ArrayList<>();
        String sql = """
                SELECT bn.*
                FROM TB_BorrowNote bn
                WHERE bn.Status = 'Borrowing' AND DATEDIFF(day, bn.PromisedReturnDate, GETDATE()) > 0""";
        try {
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                BorrowNote note = new BorrowNote();
                note.setBorrowNoteID(resultSet.getInt("BorrowNoteID"));
                note.setReaderID(resultSet.getInt("ReaderID"));
                note.setPromiseReturnDate(resultSet.getString("PromisedReturnDate"));

                notes.add(note);
            }
            return notes;
        } catch (SQLException e) {
            System.out.println("Query request failed.");
        } finally {
            DataAccess.closeConnection(connection);
            DataAccess.closePreparedStatement(preparedStatement);
            DataAccess.closeResultSet(resultSet);
        }
        return null;
    }

    public ArrayList<BorrowNote> findNoteByReaderID(int readerID) {
        ArrayList<BorrowNote> notes = new ArrayList<>();
        String sql = """
                select bn.*, u.FullName
                from TB_BorrowNote bn, TB_User u
                where bn.UserID = u.UserID and bn.ReaderID = ?""";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, readerID);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                BorrowNote note = new BorrowNote();
                note.setBorrowNoteID(resultSet.getInt("BorrowNoteID"));
                note.setReaderID(resultSet.getInt("ReaderID"));
                note.setAmount(resultSet.getInt("Amount"));
                note.setDeposit(resultSet.getString("Deposit"));
                note.setBorrowDate(resultSet.getString("BorrowDate"));
                note.setPromiseReturnDate(resultSet.getString("PromisedReturnDate"));
                note.setStatus(resultSet.getString("Status"));
                note.setUserID(resultSet.getInt("UserID"));
                note.setUserName(resultSet.getString("FullName"));

                notes.add(note);
            }
            return notes;
        } catch (SQLException e) {
            System.out.println("Query request failed.");
        } finally {
            DataAccess.closeConnection(connection);
            DataAccess.closePreparedStatement(preparedStatement);
            DataAccess.closeResultSet(resultSet);
        }

        return null;
    }

    public ArrayList<BorrowNote> findBorrowNotes(int readerID, String fullName, String borrowDate, String promiseDate) {
        ArrayList<BorrowNote> notes = new ArrayList<>();
        String sql;
        if (readerID == -1) {
            sql = "SELECT bn.*, r.ReaderName FROM TB_BorrowNote bn, TB_Reader r WHERE " +
                    "ReaderName like N'%" + fullName + "%'" +
                    " AND BorrowDate like N'%" + borrowDate + "%'" +
                    " AND PromisedReturnDate like N'%" + promiseDate + "%'" +
                    " AND bn.ReaderID = r.ReaderID";
        } else {
            sql = "SELECT bn.*, r.ReaderName FROM TB_BorrowNote bn, TB_Reader r WHERE bn.ReaderID = r.ReaderID and bn.ReaderID=" + readerID;
        }
        try {
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                BorrowNote note = new BorrowNote();
                note.setBorrowNoteID(resultSet.getInt("BorrowNoteID"));
                note.setReaderName(resultSet.getString("ReaderName"));
                note.setReaderID(resultSet.getInt("ReaderID"));
                note.setStatus(resultSet.getString("Status"));

                notes.add(note);
            }
            return notes;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DataAccess.closeConnection(connection);
            DataAccess.closePreparedStatement(preparedStatement);
            DataAccess.closeResultSet(resultSet);
        }

        return null;
    }
}
