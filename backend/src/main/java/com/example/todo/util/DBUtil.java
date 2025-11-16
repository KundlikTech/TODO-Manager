package com.example.todo.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {

    // TODO: update these values for your environment
    private static final String URL = "jdbc:mysql://localhost:3306/todo_jdbc?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASS = "root";

    static {
        try {
            // load driver (not strictly required on newer JDKs but safe)
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            // try the new driver classname
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
