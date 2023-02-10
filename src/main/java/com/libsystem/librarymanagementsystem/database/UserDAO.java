package com.libsystem.librarymanagementsystem.database;

import com.libsystem.librarymanagementsystem.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

// TO-DO: create again
public class UserDAO {
    private Connection connection = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    public User findUsername(String username) {
        String sql = "SELECT * FROM TB_User WHERE Username=?";
        try {
            connection = DataAccess.getConnection();
            assert connection != null;
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                User user = new User();
                user.setUserID(resultSet.getInt("UserID"));
                user.setFullName(resultSet.getString("FullName"));
                user.setUsername(resultSet.getString("Username"));
                user.setPassword(resultSet.getString("Password"));
                user.setSalt(resultSet.getString("Salt"));
                user.setRole(resultSet.getString("Role"));
                user.setDateOfBirth((resultSet.getString("DateOfBirth")));
                user.setPhoneNumber(resultSet.getString("PhoneNumber"));
                user.setGender(resultSet.getString("Gender"));
                return user;
            }
        } catch (SQLException exception) {
            System.out.println("Query request failed.");
        } finally {
            DataAccess.closeConnection(connection);
            DataAccess.closePreparedStatement(preparedStatement);
            DataAccess.closeResultSet(resultSet);
        }
        return null;
    }

    public ArrayList<User> findUser(String keyWords) {
        ArrayList<User> users = new ArrayList<>();
        System.out.println(keyWords);
        String sql = "SELECT * FROM TB_User WHERE " +
                "Username like N'%" + keyWords + "%'" +
                " OR FullName like N'%" + keyWords + "%'";
        System.out.println(sql);
        try {
            connection = DataAccess.getConnection();
            assert connection != null;
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
//            System.out.println(resultSet.next());
            while (resultSet.next()) {
                User user = new User();
                user.setUserID(resultSet.getInt("UserID"));
                user.setFullName(resultSet.getString("FullName"));
                user.setUsername(resultSet.getString("Username"));
                user.setPassword(resultSet.getString("Password"));
                user.setSalt(resultSet.getString("Salt"));
                user.setRole(resultSet.getString("Role"));
                user.setDateOfBirth((resultSet.getString("DateOfBirth")));
                user.setPhoneNumber(resultSet.getString("PhoneNumber"));
                user.setGender(resultSet.getString("Gender"));
                users.add(user);
            }

            return users;
        } catch (SQLException e) {
            System.out.println("Query request failed.");
        } finally {
            DataAccess.closeConnection(connection);
            DataAccess.closePreparedStatement(preparedStatement);
            DataAccess.closeResultSet(resultSet);
        }

        return null;
    }

    public boolean updateUser(User user) {
        String sql = "UPDATE TB_User SET FullName=?, Username=?, Password=?, Salt=?, DateOfBirth=?, PhoneNumber=?, Gender=? WHERE UserID=?";
        try {
            connection = DataAccess.getConnection();
            assert connection != null;
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, user.getFullName());
            preparedStatement.setString(2, user.getUsername());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setString(4, user.getSalt());
            preparedStatement.setString(5, user.getDateOfBirth());
            preparedStatement.setString(6, user.getPhoneNumber());
            preparedStatement.setString(7, user.getGender());
            preparedStatement.setInt(8, user.getUserID());

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("An existing user was updated successfully!");
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

    public boolean deleteUser(int userId) {
        String sql = "DELETE FROM TB_User WHERE UserID=?";
        try {
            connection = DataAccess.getConnection();
            assert connection != null;
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, userId);

            int rowsDeleted = preparedStatement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("A user was deleted successfully!");
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

    public ArrayList<User> showAllUser() {
        ArrayList<User> users = new ArrayList<>();
        String sql = "SELECT * FROM TB_User";
        System.out.println(sql);
        try {
            connection = DataAccess.getConnection();
            assert connection != null;
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
//            System.out.println(resultSet.next());
            while (resultSet.next()) {
                User user = new User();
                user.setUserID(resultSet.getInt("UserID"));
                user.setFullName(resultSet.getString("FullName"));
                user.setUsername(resultSet.getString("Username"));
                user.setPassword(resultSet.getString("Password"));
                user.setSalt(resultSet.getString("Salt"));
                user.setRole(resultSet.getString("Role"));
                user.setDateOfBirth((resultSet.getString("DateOfBirth")));
                user.setPhoneNumber(resultSet.getString("PhoneNumber"));
                user.setGender(resultSet.getString("Gender"));
                users.add(user);
            }

            return users;
        } catch (SQLException e) {
            System.out.println("Query request failed.");
        } finally {
            DataAccess.closeConnection(connection);
            DataAccess.closePreparedStatement(preparedStatement);
            DataAccess.closeResultSet(resultSet);
        }

        return null;
    }

    public ArrayList<String> getAllRoles() {
        ArrayList<String> roles = new ArrayList<>();
        String sql = "Select distinct Role from TB_User";
        System.out.println(sql);
        try {
            connection = DataAccess.getConnection();
            assert connection != null;
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
//            System.out.println(resultSet.next());
            while (resultSet.next()) {
                roles.add(resultSet.getString("Role"));
            }

            return roles;
        } catch (SQLException e) {
            System.out.println("Query request failed.");
        } finally {
            DataAccess.closeConnection(connection);
            DataAccess.closePreparedStatement(preparedStatement);
            DataAccess.closeResultSet(resultSet);
        }

        return null;
    }

    public boolean addUser(User user) {
        String sql = "INSERT INTO TB_User (FullName, Username, Password, Salt, Role, DateOfBirth, PhoneNumber, Gender) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            connection = DataAccess.getConnection();
            assert connection != null;
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, user.getFullName());
            preparedStatement.setString(2, user.getUsername());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setString(4, user.getSalt());
            preparedStatement.setString(5, user.getRole());
            preparedStatement.setString(6, user.getDateOfBirth());
            preparedStatement.setString(7, user.getPhoneNumber());
            preparedStatement.setString(8, user.getGender());

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new user was inserted.");
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
}
