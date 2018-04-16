package Controller;

import java.sql.*;

/**
 * This class acts as the interface between the GUI view and the data models.
 */
public class Controller {
    //private Connection connection;
    Connection connection;
    PreparedStatement selectCountFromProducts;

    public boolean connect(){

        try {
            // this will require the sqlite-jdbc driver JAR to be added to the project.
            connection = DriverManager.getConnection("jdbc:sqlite:WMSDatabase.db");
        }
        catch (SQLException e){
            System.err.println("connection failed");
            return false;
        }
        return true;
    }

    public boolean prepareStatements(){
        String sql = "SELECT COUNT(*) FROM Products";
        try {
            selectCountFromProducts = connection.prepareStatement(sql);
            connection.prepareStatement(sql);
        }
        catch (SQLException e){
            System.err.println("Statement(s) failed to prepare");
            return false;
        }

        return true;
    }

    public int getProductCount(){
        try{
            ResultSet rs = selectCountFromProducts.executeQuery();
            return rs.getInt(1);
        }
        catch (SQLException e){
            return -1;
        }
    }

    public static void main(String[] args) {
        Controller cont = new Controller();
        boolean didConnect = cont.connect();
        System.out.printf("didConnect = %b%n", didConnect);
        boolean didPrepare = cont.prepareStatements();
        System.out.printf("didPrepare = %b%n", didPrepare);
        System.out.printf("Number of products in database: %d%n", cont.getProductCount());
    }
}