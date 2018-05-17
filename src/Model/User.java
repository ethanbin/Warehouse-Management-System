package Model;

/**
 * A simple model for a User's data as pulled from a database. This model contains
 * all the data of a User from the database with the exception of the user's
 * password to prevent potential security vulnerabilities from memory scanners.
 */
public class User {
    private int user_id;
    private String name;
    private boolean isAdmin;
    private String username;
    private int warehouseID;

    /**
     * Constructor which sets every property in the class.
     * @param user_id integer ID of the user
     * @param name full name of the user
     * @param isAdmin admin status of user
     * @param username username
     * @param warehouseID integer ID of the warehouse this user is employed at
     */
    public User(int user_id, String name, boolean isAdmin, String username, int warehouseID) {
        this.user_id = user_id;
        this.name = name;
        this.isAdmin = isAdmin;
        this.username = username;
        this.warehouseID = warehouseID;
    }

    /**
     * Return User ID.
     * @return User ID
     */
    public int getUser_id() {
        return user_id;
    }

    /**
     * Return the User's full name.
     * @return the User's full name
     */
    public String getName() {
        return name;
    }

    /**
     * Return true if the user is an admin.
     * @return true if the user is an admin
     */
    public boolean isAdmin() {
        return isAdmin;
    }

    /**
     * Return the username.
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Return the ID of the warehouse where the user is employed.
     * @return the ID of the warehouse where the user is employed
     */
    public int getWarehouseID() {
        return warehouseID;
    }
}
