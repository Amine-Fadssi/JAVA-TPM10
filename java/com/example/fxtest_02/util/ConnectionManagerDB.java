package com.example.fxtest_02.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManagerDB {
    private static Connection connection;
    private ConnectionManagerDB(){

    }
    public static Connection getConnection() throws SQLException {
        if(connection == null || connection.isClosed()){
            String url = "jdbc:mysql://localhost:3306/db_jdbc_tp6";
            String username = "root";
            String password = "";

            connection = DriverManager.getConnection(url,username,password);
        }
        return connection;
    }
}
