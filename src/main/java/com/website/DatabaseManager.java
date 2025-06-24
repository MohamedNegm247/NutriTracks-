package com.website;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
    public class DatabaseManager {
        private static final String URL = "jdbc:mysql://localhost:3306/project";
        private static final String USER = "root";
        private static final String PASSWORD = "StarLord*12354";





        public static Connection getConnection() {
            Connection connection = null;
            try {

                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();

            }
            return connection;
        }
    }


