package Model;

public class User {
    private int user_id;
    private String name;
    private boolean isAdmin;
    private String username;
    private int warehouseID;

    /**
     * The User data model contains all the data of a record from the User table of the database
     * except for the password to prevent potential security vulnerabilities to memory scanning software.
     * @param user_id ID of the user
     * @param name full name of the user
     * @param isAdmin admin status of user
     * @param username username
     * @param warehouseID Database ID of the warehouse this user is employed at
     */
    public User(int user_id, String name, boolean isAdmin, String username, int warehouseID) {
        this.user_id = user_id;
        this.name = name;
        this.isAdmin = isAdmin;
        this.username = username;
        this.warehouseID = warehouseID;
    }

    public int getUser_id() {
        return user_id;
    }

    public String getName() {
        return name;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public String getUsername() {
        return username;
    }

    public int getWarehouseID() {
        return warehouseID;
    }
}
