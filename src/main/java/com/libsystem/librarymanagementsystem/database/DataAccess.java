package com.libsystem.librarymanagementsystem.database;


import com.microsoft.sqlserver.jdbc.SQLServerDriver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class DataAccess {
    public static Connection getConnection() {
        Connection conn = null;

        String url = "jdbc:sqlserver://" + DatabaseConfig.HOSTNAME
                + "\\HUNGCOMPUTER\\SQLEXPRESS:"
                + DatabaseConfig.PORT
                + ";" + "databaseName=" + DatabaseConfig.DBNAME
                + ";encrypt=true;trustServerCertificate=true";

        try {
            DriverManager.registerDriver(new SQLServerDriver());
            conn = DriverManager.getConnection(url, DatabaseConfig.USERNAME, DatabaseConfig.PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return conn;
    }

    public static void closeConnection(Connection conn) {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            System.out.println("Close Connection failed.");
        }
    }

    public static void closePreparedStatement(PreparedStatement preState) {
        try {
            if (preState != null) {
                preState.close();
            }
        } catch (SQLException e) {
            System.out.println("Close PreparedStatement failed.");
        }
    }

    public static void closeResultSet(ResultSet resSet) {
        try {
            if (resSet != null) {
                resSet.close();
            }
        } catch (SQLException e) {
            System.out.println("Close ResultSet failed.");
        }
    }

    public static void main(String[] args) {
        System.out.println(DataAccess.getConnection());
    }
}
