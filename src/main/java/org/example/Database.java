package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    public final static String JDBC_URL = "jdbc:h2:/Desktop/goit/h2/6";
    public final static String JDBC_USER = "sa";
    public final static String JDBC_PASSWORD = "";
    private static Database instance;
    private Connection connection;
    private Database() {
        try {
            connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }
    public Connection getConnection() {
        return connection;
    }

}
