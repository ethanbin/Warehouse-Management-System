package Controller;

import Exceptions.DataControllerException;
import Exceptions.ErrorHandler;
import Model.Product;
import com.sun.tools.javac.Main;
import org.sqlite.SQLiteConfig;
import org.sqlite.SQLiteOpenMode;

import java.sql.*;
import java.util.*;

/**
 * A controller to interface with a database.
 * This class follows the singleton pattern.
 */
public class DataController {
    private static DataController instance;

    private final static String DATABASE_PATH_PREFIX = "jdbc:sqlite:";
    private String sqliteDatabaseURL;

    private Connection connection;

    private List<Product> immutableProductBuffer = null;

    // lets keep this in alphabetical order
    // prepared statements are named with a prefix of s_ to distinguish them from the methods that use them
    private PreparedStatement s_selectAllProductsInRange;
    private PreparedStatement s_selectCountFromProducts;
    private PreparedStatement s_selectCountFromOrders;
    private PreparedStatement s_selectStockFromProductsStock;
    private PreparedStatement s_updateProductAtIndex;
    private PreparedStatement s_updateProductStockExistsAtIndex;

    /**
     * Following singleton pattern, this method will return the static instance of the
     * DataController class. If no instance yet exists, one will be created using the
     * DataController constructor, saved as a static variable, and returned.
     * If there is a problem creating the DataController, an exception will be passed
     * to ErrorHandler.logCriticalError and the application will terminate.
     * @return DataController's static property instance, of type DataController
     * @see ErrorHandler
     */
    public static DataController getInstance() {
        if (instance == null)
            instance = new DataController();
        return instance;
    }

    // constructor private for singleton pattern
    private DataController() {
        sqliteDatabaseURL = DATABASE_PATH_PREFIX + MainController.getInstance().getDatabaseURL();
        // try to connect to database. If fails, throw exception
        if (!connect())
            ErrorHandler.logCriticalError(
                    new DataControllerException("Connecting to database: ",DataControllerException.DATABASE_FAILED));
        // Try to prepare statements. If fails, throw exception.
        // It seems like statements only fail when making a code related error, like syntax errors, doing
        // something SQL doesn't support, etc.
        if (!prepareStatements())
            ErrorHandler.logCriticalError(
                    new DataControllerException("Preparing statements: ",DataControllerException.PREPARED_STATEMENTS_FAILED));
    }

    private boolean connect(){
        try {
            // this will stop an empty file from being created if the one we try to open doesnt exist
            SQLiteConfig config = new SQLiteConfig();
            config.resetOpenMode(SQLiteOpenMode.CREATE);

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
    private boolean prepareStatements(){
        try {
            s_selectAllProductsInRange = connection.prepareStatement("SELECT * FROM Products WHERE " +
                    "ROWID >= ? AND ROWID < ?");

            s_selectCountFromProducts = connection.prepareStatement("SELECT COUNT(*) FROM Products");

            s_selectCountFromOrders = connection.prepareStatement("SELECT COUNT(*) FROM Orders");

            s_selectStockFromProductsStock = connection.prepareStatement(
                    "Select * FROM Products_Stock WHERE product_id = ? AND warehouse_id = ?");

            s_updateProductAtIndex = connection.prepareStatement("UPDATE Products " +
                    "SET name = ?, description = ?, price = ?, discontinued = ?, stock_exists = ? " +
                    "WHERE product_id = ?;");

            s_updateProductStockExistsAtIndex = connection.prepareStatement("UPDATE Products " +
                    "SET stock_exists = ? " +
                    "WHERE product_id = ?;");
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

    public List<Product> getImmutableProductBuffer(){
        return immutableProductBuffer;
    }

    // for neatness, methods below here are strictly public methods for specific
    // sql statements, with the exception of main

    //TODO - UPDATE JAVADOC WITH THE PRODUCTS BUFFER
    /**
     * Select from the database rows from the Products table starting at the
     * specified location for a specified distance and return the gathered
     * data as a List of Product objects.
     *
     * @param start the first row to begin selecting from. This is inclusive.
     * @param distance how many rows to select from after start.
     * @return List of Products selected over the given range from the database
     * @see Product
     */
    public List<Product> selectAllProductsInRange(int start, int distance){
        List<Product> products = null;
        try{
            s_selectAllProductsInRange.setInt(1,start);
            s_selectAllProductsInRange.setInt(2,start + distance);
            ResultSet rs = s_selectAllProductsInRange.executeQuery();
            while (rs.next()) {
                if (products == null)
                    products = new ArrayList<>();
                int productID = rs.getInt("product_id");
                products.add(new Product(productID,
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getFloat("price"),
                        // since 1 represents true, compare the value to 1. If its one, this will use true as the argument
                        rs.getInt("discontinued") == 1,
                        rs.getInt("stock_exists") == 1,
                        0));
            }
            rs.close();
            immutableProductBuffer = Collections.unmodifiableList(products);
            return products;
        }
        catch (SQLException e){
            return null;
        }
    }

    /**
     * Query the database and return integer value representing count of how many items exist in the Orders table.
     * @return integer value representing count of how many items exist in the Customers table
     */
    public int selectCountFromOrders(){
        return executeCountStatement(s_selectCountFromOrders);
    }

    /**
     * Query the database and return integer value representing count of how many items exist in the Products table.
     * @return integer value representing count of how many items exist in the Products table
     */
    public int selectCountFromProducts(){
        return executeCountStatement(s_selectCountFromProducts);
    }

    public int selectStockForProductAtIndex(int productID, int warehouseID){
        try{
            s_selectStockFromProductsStock.setInt(1,productID);
            s_selectStockFromProductsStock.setInt(2,warehouseID);
            ResultSet rs = s_selectStockFromProductsStock.executeQuery();
            int stock = rs.getInt("stock");
            rs.close();
            return stock;
        }
        catch (SQLException e){
            return 0;
        }
    }

    public boolean updateProductAtIndex(int product_ID, String name, String description, float price, int discontinued,
                                        int stockExists){
        try{
            s_updateProductAtIndex.setString(1, name);
            s_updateProductAtIndex.setString(2, description);
            s_updateProductAtIndex.setFloat(3, price);
            s_updateProductAtIndex.setInt(4, discontinued);
            s_updateProductAtIndex.setInt(5, stockExists);
            s_updateProductAtIndex.setInt(6, product_ID);
            s_updateProductAtIndex.executeUpdate();
            s_updateProductAtIndex.clearParameters();
            return true;
        }
        catch (SQLException e){
            return false;
        }
    }

    public boolean updateProductStockExistsAtIndex(int productID, int stockExists){
        try{
            s_updateProductStockExistsAtIndex.setInt(1, stockExists);
            s_updateProductStockExistsAtIndex.setInt(2, productID);
            s_updateProductStockExistsAtIndex.executeUpdate();
            s_updateProductStockExistsAtIndex.clearParameters();
            return true;
        }
        catch (SQLException e){
            return false;
        }
    }

    public static void main(String[] args) {
        try {
            DataController cont = DataController.getInstance();
            // testing s_selectCountFromProducts
            //System.out.printf("Number of products in database: %d%n", cont.s_selectCountFromProducts());

            // testing s_updateProductAtIndex
            // boolean updated = cont.s_updateProductAtIndex(1, "name", "desc", 5.5f, 0, 1);
            // if (updated)
            // System.out.println("Product with id 1 updated");

            // testing s_updateProductStockExistsAtIndex
            // if (cont.s_updateProductStockExistsAtIndex(1, 0)) System.out.println("success");

            List<Product> products = cont.selectAllProductsInRange(1, 5);
            for (Product product : products) {
                System.out.println(product.toString());
            }
            System.out.println();
            System.out.println(DataController.getInstance().getImmutableProductBuffer().get(0));
        }

        catch (Exception e){
            System.err.println(e.toString());
        }
    }
}
