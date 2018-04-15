package Controller;

import java.sql.*;

public class Controller {
    //private Connection connection;

    public static void main(String[] args) {
        Connection connection = null;

        try {
            // this will require the sqlite-jdbc driver JAR to be added to the project.
            connection = DriverManager.getConnection("jdbc:sqlite:WMSDatabase.db");
        }
        catch (SQLException e){
            System.err.println("connection failed");
        }
    }
}