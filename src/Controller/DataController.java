package Controller;

import javax.print.DocFlavor;
import javax.xml.crypto.Data;
import java.sql.*;

/**
 * A controller to interface with a database.
 * This class follows the singleton pattern.
 */
public class DataController {
    private static DataController instance;

    private final String DATABASE_PATH_PREFIX = "jdbc:sqlite:";
    private String sqliteDatabaseURL;

    private Connection connection;

    // lets keep this in alphabetical order
    private PreparedStatement selectCountFromTable;

    public static DataController getInstance(){
        if (instance == null) {
            try {
                instance = new DataController();
            }
            catch(SQLException e){
                return null;
            }
        }
        return instance;
    }

    private DataController() throws SQLException{
        //TODO - pull database URL from properties file instead of hard coding it
        sqliteDatabaseURL = DATABASE_PATH_PREFIX + "WMSDatabase.db";
        if (!connect())
            throw new SQLException();
        if (!prepareStatements())
            throw new SQLException();
    }

    public boolean connect(){
        try {
            // this will require the sqlite-jdbc driver JAR to be added to the project.
            connection = DriverManager.getConnection(sqliteDatabaseURL);
        }
        catch (SQLException e){
            System.err.println("connection failed");
            return false;
        }
        return true;
    }

    public boolean prepareStatements(){
        String sql = "";
        try {
            selectCountFromTable = connection.prepareStatement("SELECT COUNT(*) FROM ?");
        }
        catch (SQLException e){
            System.err.println("Statement(s) failed to prepare");
            return false;
        }

        return true;
    }

    /**
     * Query the database and return integer value representing count of how many items exist in the Customers table.
     * @return integer value representing count of how many items exist in the Customers table
     */
    public int getCustomerCount(){
        return getCountOfATable("Customers");
    }

    /**
     * Query the database and return integer value representing count of how many items exist in the Products table.
     * @return integer value representing count of how many items exist in the Products table
     */
    public int getProductCount(){
        return getCountOfATable("Product");
    }

    // for neatness, methods below here are strictly methods for specified sql statements

    /**
     * Query the database and return integer value representing count of how many items exist in the specified table.
     * By passing the table we want to query as a string for any table, we cut down on repeated code, since the same
     * few lines would need to be repeated for getting a count from any table.
     * @return integer value representing count of how many items exist in the specified table
     */
    private int getCountOfATable(String table){
        try{
            selectCountFromTable.setString(1,table);
            ResultSet rs = selectCountFromTable.executeQuery();
            return rs.getInt(1);
        }
        catch (SQLException e){
            return -1;
        }
    }

    public static void main(String[] args) {
        try {
            DataController cont = DataController.getInstance();
            System.out.printf("Number of products in database: %d%n", cont.getProductCount());
        }
        catch (Exception e){
            System.err.println("Failed to open/prepare database.");
        }
    }
}
