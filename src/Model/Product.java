package Model;

import Controller.MainController;

import java.util.Objects;

public class Product {
    private int id;
    private String name;
    private String description;
    private float price;
    private boolean discontinued;
    private boolean stock_exists;
    private int stock;
    private int warehouseID;

    public Product(int id, String name, String description, float price, boolean discontinued,
                   boolean stock_exists, int stock) {
        this(id, name, description, price, discontinued, stock_exists, stock, MainController.getInstance().getCurrentWarehouseID());
    }

    public Product(int id, String name, String description, float price, boolean discontinued,
                   boolean stock_exists, int stock, int warehouseID) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.discontinued = discontinued;
        this.stock_exists = stock_exists;
        this.stock = stock;
        this.warehouseID = warehouseID;
    }

    public  Product (Product p){
        this.id = p.getId();
        this.name = p.getName();
        this.description = p.getDescription();
        this.price = p.getPrice();
        this.discontinued = p.isDiscontinued();
        this.stock_exists = p.doesStockExist();
        this.stock = p.getStock();
        this.warehouseID = p.getWarehouseID();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public boolean isDiscontinued() {
        return discontinued;
    }

    public void setDiscontinued(boolean discontinued) {
        this.discontinued = discontinued;
    }

    public boolean doesStockExist() {
        return stock_exists;
    }

    public void setStock_exists(boolean stock_exists) {
        this.stock_exists = stock_exists;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getWarehouseID() {
        return warehouseID;
    }

    public void setWarehouseID(int warehouseID) {
        this.warehouseID = warehouseID;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", discontinued=" + discontinued +
                ", stock_exists=" + stock_exists +
                ", stock=" + stock +
                ", warehouseID=" + warehouseID +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id &&
                Float.compare(product.price, price) == 0 &&
                discontinued == product.discontinued &&
                stock_exists == product.stock_exists &&
                stock == product.stock &&
                warehouseID == product.warehouseID &&
                Objects.equals(name, product.name) &&
                Objects.equals(description, product.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, price, discontinued, stock_exists, stock, warehouseID);
    }
}
