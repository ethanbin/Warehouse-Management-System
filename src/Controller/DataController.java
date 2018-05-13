package Controller;

import Exceptions.DataControllerException;
import Exceptions.ErrorHandler;
import Model.Product;
import Model.User;
import org.sqlite.SQLiteConfig;
import org.sqlite.SQLiteOpenMode;

import java.sql.*;
import java.util.*;

/**
 * A controller to interface with a database.
 * This class follows the singleton pattern.
 */
public class DataController implements AutoCloseable{
    private static DataController instance;

    private final static String DATABASE_PATH_PREFIX = "jdbc:sqlite:";
    private String sqliteDatabaseURL;

    private Connection connection;

    private List<Product> immutableProductBuffer = null;

    // lets keep this in alphabetical order
    // prepared statements are named with a prefix of s_ to distinguish them from the methods that use them
    private PreparedStatement s_insertOrReplaceIntoProductStock;
    private PreparedStatement s_insertProduct;
    private PreparedStatement s_selectAllProductsInRange;
    private PreparedStatement s_selectAllProductsWithID;
    private PreparedStatement s_selectAllProductsWithName;
    private PreparedStatement s_selectAllProductsWithPrice;
    private PreparedStatement s_selectAllProductsWithStock;
    private PreparedStatement s_selectAllProductsWithLowStockForWarehouse;
    private PreparedStatement s_selectAllWarehouseIDs;
    private PreparedStatement s_selectCountFromProducts;
    private PreparedStatement s_selectCountFromOrders;
    private PreparedStatement s_selectStockFromProductsStock;
    private PreparedStatement s_selectUser;
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
    public synchronized static DataController getInstance() {
        if (instance == null)
            instance = new DataController();
        return instance;
    }

    /**
     * Close the database connection and reinitialize the DataController instance,
     * opening the database again.
     */
    public synchronized void resetDataController(){
        close();
        connection = null;
        instance = new DataController();
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
            s_insertOrReplaceIntoProductStock = connection.prepareStatement(
                    // note that the first arg is same as third, and the second same as fourth. The fifth is the stock.
                    // all these args are ints
                    "INSERT OR REPLACE INTO Products_Stock " +
                    "(products_stock_id, product_id, warehouse_id, stock) VALUES " +
                    "((SELECT products_stock_id FROM Products_Stock WHERE product_id = ? AND warehouse_id = ?), ?, ?, ?);");

            s_insertProduct = connection.prepareStatement("INSERT INTO Products (name, description, " +
                    "price, discontinued, stock_exists) VALUES   (?, ?, ?, ?, ?)");

            s_selectAllProductsInRange = connection.prepareStatement("SELECT * FROM Products LIMIT ? OFFSET ?");

            s_selectAllProductsWithID = connection.prepareStatement("SELECT * FROM Products WHERE product_id = ?");

            s_selectAllProductsWithName = connection.prepareStatement("Select * FROM Products where name LIKE ?");

            s_selectAllProductsWithPrice = connection.prepareStatement("SELECT * from Products WHERE price = ?");

            s_selectAllProductsWithStock = connection.prepareStatement("Select * FROM Products where " +
                    "product_id IN (Select product_id FROM Products_Stock WHERE stock = ? AND warehouse_id = ?)");

            s_selectAllProductsWithLowStockForWarehouse = connection.prepareStatement(
                    "Select * FROM Products where product_id IN " +
                            "(Select product_id FROM Products_Stock WHERE stock <= ? AND warehouse_id = ?)");

            s_selectCountFromProducts = connection.prepareStatement("SELECT COUNT(*) FROM Products");

            s_selectCountFromOrders = connection.prepareStatement("SELECT COUNT(*) FROM Orders");

            s_selectStockFromProductsStock = connection.prepareStatement(
                    "Select * FROM Products_Stock WHERE product_id = ? AND warehouse_id = ?");

            s_selectUser = connection.prepareStatement("SELECT * FROM Users WHERE username = ? AND password = ? ");

            s_selectAllWarehouseIDs = connection.prepareStatement("SELECT warehouse_id FROM Warehouses");

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

    /**
     * Close database connection when garbage collection runs on a DataController.
     * @throws Throwable any possible Exception
     * @see Throwable
     * @deprecated
     */
    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        connection.close();
    }

    /**
     * Close the database connection. This should only be done when exiting the application.
     */
    @Override
    public void close(){
        try {
            connection.close();
        }
        catch (SQLException e){
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

    /**
     * Return the last set of Products selected from a range as an unmodifiable List.
     * @return unmodifiable List containing the last set of Products selected from a range
     * @see java.util.Collections.UnmodifiableList
     */
    public List<Product> getImmutableProductBuffer(){
        return immutableProductBuffer;
    }

    /**
     * Convert a Result Set's data to a list of Products.
     * @param rs Result Set to convert
     * @param warehouseID warehouse id for the list of products in order to select correct stock count
     * @return List of Products that can be made with the given Result Set
     * @throws SQLException
     */
    private List<Product> resultSetToProductList(ResultSet rs, int warehouseID) throws  SQLException{
        List<Product> products = new ArrayList<>();
        while (rs.next()) {
            int productID = rs.getInt("product_id");
            products.add(new Product(productID,
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getFloat("price"),
                    // since 1 represents true, compare the value to 1. If its one, this will use true as the argument
                    rs.getInt("discontinued") == 1,
                    rs.getInt("stock_exists") == 1,
                    selectStockForProductAtIndex(productID, warehouseID),
                    warehouseID));
        }
        return products;
    }

    // for neatness, methods below here are strictly public methods for specific
    // sql statements, with the exception of main

    /**
     * Insert a record into the Product table in the database.
     * @param name of new Product
     * @param description of new Product
     * @param price of new Product
     * @param discontinued status of new Product as a 1 for true or 0 for false
     * @param stockExists status of new Product as a 1 for true or 0 for false
     * @return true if Product successfully added to table
     */
    public boolean insertProduct(String name, String description, float price, int discontinued, int stockExists){
        try{
            s_insertProduct.setString(1, name);
            s_insertProduct.setString(2, description);
            s_insertProduct.setFloat(3, price);
            s_insertProduct.setInt(4, discontinued);
            s_insertProduct.setInt(5, stockExists);
            s_insertProduct.executeUpdate();
            s_insertProduct.clearParameters();
            return true;
        }
        catch (SQLException e){
            return false;
        }
    }

    /**
     * Return all Products as a list that have a stock below a specified amount at a specified warehouse.
     * @param lowStockThreshold The amount below which is considered to be low stock
     * @param warehouseID The ID of the warehouse to query for low stock
     * @return list of Products that have low stock
     */
    public List<Product> selectAllProductsAtLowStockAtWarehouse(int lowStockThreshold, int warehouseID) {
        List<Product> products;
        try{
            s_selectAllProductsWithLowStockForWarehouse.setInt(1,lowStockThreshold);
            s_selectAllProductsWithLowStockForWarehouse.setInt(2,warehouseID);
            ResultSet rs = s_selectAllProductsWithLowStockForWarehouse.executeQuery();
            products = resultSetToProductList(rs, warehouseID);
            rs.close();
            return products;
        }
        catch (SQLException e){
            return null;
        }
    }

    /**
     * Select from the database rows from the Products table starting after the
     * specified location for a specified distance and return the gathered
     * data as a List of Product objects. The selected Products are stored as
     * an unmodifiable list {@link DataController#immutableProductBuffer}.
     *
     * @param offset The exclusive offset to begin selecting rows after.
     * @param distance How many rows to select from after offset.
     * @return List of Products selected over the given range from the database, or null if no Product selected
     * @see Product
     */
    public List<Product> selectAllProductsInRange(int offset, int distance){
        List<Product> products;
        try{
            s_selectAllProductsInRange.setInt(1,distance);
            s_selectAllProductsInRange.setInt(2,offset);
            ResultSet rs = s_selectAllProductsInRange.executeQuery();
            products = resultSetToProductList(rs, MainController.getInstance().getCurrentWarehouseID());
            rs.close();
            immutableProductBuffer = Collections.unmodifiableList(products);
            return products;
        }
        catch (SQLException e){
            return null;
        }
    }

    /**
     * Select and return a List of Products with the specified ID at the specified warehouse.
     * @param productID int ID of the Product being queried
     * @param warehouseID int ID of the warehouse the stock of the product is requested for
     * @return Product with the specified ID, or null if no such Product exists
     */
    public List<Product> selectAllProductsWithID(int productID, int warehouseID){
        try {
            s_selectAllProductsWithID.setInt(1, productID);
            ResultSet rs = s_selectAllProductsWithID.executeQuery();
            return resultSetToProductList(rs, warehouseID);
        }
        catch (Exception e){
            return null;
        }
    }

    /**
     * Select and return a List of Products with the specified ID at the specified warehouse.
     * @param price float price of the Product being queried
     * @param warehouseID int ID of the warehouse the stock of the product is requested for
     * @return Product with the specified ID, or null if no such Product exists
     */
    public List<Product> selectAllProductsWithPrice(float price, int warehouseID){
        try {
            s_selectAllProductsWithPrice.setFloat(1, price);
            ResultSet rs = s_selectAllProductsWithPrice.executeQuery();
            return resultSetToProductList(rs, warehouseID);
        }
        catch (Exception e){
            return null;
        }
    }

    /**
     * Select and return a List of Products with the specified similar name at the specified warehouse.
     * Products in the database must contain the given string to search with as a substring or be
     * equal to it in order to be selected. This selection is case-insensitive.
     * @param name name of products to select
     * @param warehouseID int ID of the warehouse the stock of the product is requested for
     * @return Product with the specified ID, or null if no such Product exists
     */
    public List<Product> selectAllProductsWithName(String name, int warehouseID){
        try {
            // surround the name being searched with wildcards
            name = "%" + name + "%";
            s_selectAllProductsWithName.setString(1, name);
            ResultSet rs = s_selectAllProductsWithName.executeQuery();
            return resultSetToProductList(rs, warehouseID);
        }
        catch (Exception e){
            return null;
        }
    }

    /**
     * Select and return a List of Products with the specified stock count at the specified warehouse.
     * @param stock stock count to query matching products with
     * @param warehouseID int ID of the warehouse the query is being done for
     * @return A list of Products with the same stock as specified, or null if no such products exist
     */
    public List<Product> selectAllProductsWithStock(int stock, int warehouseID){
        try {
            s_selectAllProductsWithStock.setInt(1, stock);
            s_selectAllProductsWithStock.setInt(2, warehouseID);
            ResultSet rs = s_selectAllProductsWithStock.executeQuery();
            return resultSetToProductList(rs, warehouseID);
        }
        catch (Exception e){
            return null;
        }
    }

    /**
     * Returns a list of Integers containing the ID for each warehouse from the Warehouse table, or
     * null if no warehouses exist.
     * @return Integer List of every warehouse ID, or null if no warehouses exist
     */
    public List<Integer> selectAllWarehouseIDs(){
        List<Integer> warehouseIDs = new ArrayList<>();
        try {
            ResultSet rs = s_selectAllWarehouseIDs.executeQuery();
            while (rs.next()){
                warehouseIDs.add(rs.getInt("warehouse_id"));
            }
            return warehouseIDs;
        }
        catch (SQLException e){
            return null;
        }
    }

    /**
     * Query the database and return int value representing count of how many records exist in the
     * Orders table.
     * @return integer value representing count of how many items exist in the Customers table
     */
    public int selectCountFromOrders(){
        return executeCountStatement(s_selectCountFromOrders);
    }

    /**
     * Query the database and return integer value representing count of how many record exist in the
     * Products table.
     * @return integer value representing count of how many items exist in the Products table
     */
    public int selectCountFromProducts(){
        return executeCountStatement(s_selectCountFromProducts);
    }

    /**
     * Query the database and return stock count of specified Product at the specified warehouse,
     * or 0 if no stock exists.
     * @param productID int ID of the Product being queried
     * @param warehouseID int ID of the warehouse being queried
     * @return stock count of specified product at a specified warehouse
     */
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

    /**
     * Select and return User with the matching username and password, or null if no such User found.
     * @param username string username
     * @param password string password
     * @return User with matching username and password, or null if no such User found
     * @see User
     */
    public User selectUser(String username, String password){
        User user;
        try{
            s_selectUser.setString(1,username);
            s_selectUser.setString(2,password);
            ResultSet rs = s_selectUser.executeQuery();
            user = new User(rs.getInt("user_id"),
                    rs.getString("name"),
                    rs.getInt("is_admin") == 1,
                    rs.getString("username"),
                    rs.getInt("warehouse_id"));
            rs.close();
            return user;
        }
        catch (SQLException e){
            return null;
        }
    }

    /**
     * Update the stock count for the specified Product for the specified warehouse.
     * @param stock new stock count to update the database with
     * @param productID ID of the Product to update stock count for
     * @param warehouseID ID of the warehouse to update stock count for
     * @return true if stock count for specified Product for the specified warehouse updated successfully.
     */
    public boolean updateProductStockForProductAtWarehouse(int stock, int productID, int warehouseID){
        try{
            s_insertOrReplaceIntoProductStock.setInt(1, productID);
            s_insertOrReplaceIntoProductStock.setInt(2, warehouseID);
            s_insertOrReplaceIntoProductStock.setInt(3, productID);
            s_insertOrReplaceIntoProductStock.setInt(4, warehouseID);
            s_insertOrReplaceIntoProductStock.setInt(5, stock);
            s_insertOrReplaceIntoProductStock.executeUpdate();
            s_insertOrReplaceIntoProductStock.clearParameters();
            return true;
        }
        catch (SQLException e){
            return false;
        }

    }

    /**
     * Update all of a Product's data with the specified data.
     * @param product_ID ID of the product to update
     * @param name new name to update the Product with
     * @param description new description to update the Product with
     * @param price new price to update the Product with
     * @param discontinued new discontinued status to update the Product with
     * @param stockExists new stock exists status to update the Product with
     * @return true if the specified product exists and was updated
     */
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

    /**
     * Update the stock exist status for a specified product
     * @param productID ID of the product to update
     * @param stockExists new stock exists status to update the Product with
     * @return true if the specified product exists and was updated
     */
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
}
