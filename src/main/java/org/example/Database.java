package org.example;

import java.sql.Connection;
import java.sql.DriverManager;

public class Database {

    public static Connection connectDb() {

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connect = DriverManager.getConnection("jdbc:mysql://localhost/library?useSSL=false", "root", ""); // root is the default username
            return connect;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;


    }

}
