package com.company.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
    private static Connection connection;

    private DbConnection() {

    }
    public static Connection getInstance() throws SQLException {
        if (connection == null) {
            String url = "jdbc:mysql://localhost:3306/javaproject";
            String user = "root";
            String password = "";
            connection = DriverManager.getConnection(url, user, password);
        }
        return connection;
    }
}
