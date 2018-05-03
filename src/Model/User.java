package Model;

public class User {
    private int user_id;
    private String name;
    private boolean isAdmin;
    private String username;
    private int warehouseID;

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
