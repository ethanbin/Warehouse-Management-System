package Controller;

import Exceptions.DataControllerException;
import org.sqlite.SQLiteConfig;
import org.sqlite.SQLiteOpenMode;

import javax.print.DocFlavor;
import javax.xml.crypto.Data;
import java.sql.*;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * A controller to interface with a database.
 * This class follows the singleton pattern.
 */
public class DataController {
    private static DataController instance;

    private final static String SETTINGS_FILE_NAME = "settings";
    private final static String DATABASE_PATH_PREFIX = "jdbc:sqlite:";
    private String sqliteDatabaseURL;

    private Connection connection;

    // lets keep this in alphabetical order
    private PreparedStatement selectCountFromProducts;
    private PreparedStatement selectCountFromOrders;

    /**
     * Following singleton pattern, this method will return the static instance of the DataController class.
     * If no instance yet exists, one will be created using the DataController constructor,
     * saved as a static variable, and returned.
     * @return static property DataController instance
     * @throws SQLException
     * @throws DataControllerException
     */
    public static DataController getInstance() throws SQLException, DataControllerException{
        if (instance == null)
            instance = new DataController();
        return instance;
    }

    // constructor private for singleton pattern
    private DataController() throws SQLException, DataControllerException{
        //TODO - pull database URL from properties file instead of hard coding it
        ResourceBundle bundle = ResourceBundle.getBundle(SETTINGS_FILE_NAME);
        // check if bundle has key 'databaseURL' - if not, throw exception. Otherwise, get database URL
        if (!bundle.containsKey("databaseURL"))
            throw new MissingResourceException("dataBaseURL property not found",SETTINGS_FILE_NAME,"databaseURL");
        sqliteDatabaseURL = DATABASE_PATH_PREFIX + bundle.getString("databaseURL");
        // try to connect to database. If fails, throw exception
        if (!connect())
            throw new DataControllerException("Connecting to database: ",DataControllerException.DATABASE_FAILED);
        // Try to prepare statements. If fails, throw exception.
        // It seems like statements only fail when making a code related error, like syntax errors, doing
        // something SQL doesn't support, etc.
        if (!prepareStatements())
            throw new DataControllerException("Preparing statements: ",DataControllerException.PREPARED_STATEMENTS_FAILED);
    }

    private boolean connect(){
        try {
            // this will stop an empty file from being created if the one we try to open doesnt exist
            SQLiteConfig config = new SQLiteConfig();
            config.resetOpenMode(SQLiteOpenMode.READWRITE);

            // this will require the sqlite-jdbc driver JAR to be added to the project.
            connection = DriverManager.getConnection(sqliteDatabaseURL, config.toProperties());
        }
        catch (SQLException e){
            System.err.println("connection failed");
            return false;
        }
        return true;
    }

    /**
     * Prepare all PreparedStatements.
     * If preparation failed, output to console short failure message and return false.
     *
     * @return true if statements prepared successfully, false otherwise
     */
    public boolean prepareStatements(){
        try {
            selectCountFromProducts = connection.prepareStatement("SELECT COUNT(*) FROM Products");
            selectCountFromOrders = connection.prepareStatement("SELECT COUNT(*) FROM Orders");
        }
        catch (SQLException e){
            System.err.println("Statement(s) failed to prepare");
            return false;
        }

        return true;
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        connection.close();
    }

    private boolean closeDatabase(){
        try {
            connection.close();
            return true;
        }
        catch (SQLException e){
            return false;
        }
    }

    // for neatness, methods below here are strictly methods for specified sql statements, with the exception
    // being main at the bottom.

    /**
     * Query the database and return integer value representing count of how many items exist in the Orders table.
     * @return integer value representing count of how many items exist in the Customers table
     */
    public int getOrdersCount(){
        return executeCountStatement(selectCountFromOrders);
    }

    /**
     * Query the database and return integer value representing count of how many items exist in the Products table.
     * @return integer value representing count of how many items exist in the Products table
     */
    public int getProductsCount(){
        return executeCountStatement(selectCountFromProducts);
    }

    private int executeCountStatement(PreparedStatement selectCount){
        try{
            ResultSet rs = selectCount.executeQuery();
            int count = rs.getInt(1);
            rs.close();
            return count;
        }
        catch (SQLException e){
            return -1;
        }
    }

    public static void main(String[] args) {
        try {
            DataController cont = DataController.getInstance();
            System.out.printf("Number of products in database: %d%n", cont.getProductsCount());
        }
        catch (Exception e){
            System.err.println(e.toString());
        }
    }
}
