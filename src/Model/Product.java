package Model;

import Controller.MainController;

import java.util.Objects;

/**
 * A model to represent a product from a database.
 */
public class Product {
    private int id;
    private String name;
    private String description;
    private float price;
    private boolean discontinued;
    private boolean stock_exists;
    private int stock;
    private int warehouseID;

    /**
     * Constructor that takes an integer ID, name, description, float price,
     * discontinued boolean, stock exists boolean, and stock count.
     * @param id ID of product
     * @param name Name
     * @param description description
     * @param price price
     * @param discontinued whether or not the product is discontinued
     * @param stock_exists whether or not the product has stock in the warehouse it was selected from
     * @param stock stock count
     */
    public Product(int id, String name, String description, float price, boolean discontinued,
                   boolean stock_exists, int stock) {
        this(id, name, description, price, discontinued, stock_exists, stock, MainController.getInstance().getCurrentWarehouseID());
    }

    /**
     * Constructor that takes an integer ID, name, description, float price,
     * discontinued boolean, stock exists boolean, stock count, and integer ID for
     * the warehouse at which the stock of this product is located at.
     * @param id ID of product
     * @param name Name
     * @param description description
     * @param price price
     * @param discontinued whether or not the product is discontinued
     * @param stock_exists whether or not the product has stock in the warehouse it was selected from
     * @param stock stock count
     * @param warehouseID ID of warehouse at which the product and its stock is located at
     */
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

    /**
     * Copy constructor.
     * @param p Product to copy
     */
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

    /**
     * Return integer ID.
     * @return integer ID
     */
    public int getId() {
        return id;
    }

    /**
     * Return the name.
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name.
     * @param name new name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Return the description.
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set the description.
     * @param description new description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Return the price of the Product in dollars as a float.
     * @return the price of the Product
     */
    public float getPrice() {
        return price;
    }

    /**
     * Set the price of the Product in dollars as a float.
     * @param price new price to set
     */
    public void setPrice(float price) {
        this.price = price;
    }

    /**
     * Return the boolean indicating if the product has been discontinued.
     * @return true if the product has been discontinued.
     */
    public boolean isDiscontinued() {
        return discontinued;
    }

    /**
     * Set the boolean indicating if the product has been discontinued.
     * @param discontinued new discontinued to set
     */
    public void setDiscontinued(boolean discontinued) {
        this.discontinued = discontinued;
    }

    /**
     * Return the boolean indicating if the product has existing stock.
     * @return true if the product has existing stock.
     */
    public boolean doesStockExist() {
        return stock_exists;
    }

    /**
     * Set the boolean indicating if the product has existing stock.
     * @param stock_exists new stock_existing to set
     */
    public void setStock_exists(boolean stock_exists) {
        this.stock_exists = stock_exists;
    }

    /**
     * Return stock count
     * @return stock count
     */
    public int getStock() {
        return stock;
    }

    /**
     * Set stock count.
     * @param stock new stock count to set
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Get the ID of the specific warehouse where this Product's stock count comes from.
     * @return ID of the specific warehouse where this Product's stock count comes from
     */
    public int getWarehouseID() {
        return warehouseID;
    }

    /**
     * Set the ID of the specific warehouse where this Product's stock count comes from
     * @param warehouseID new warehouse ID to set
     */
    public void setWarehouseID(int warehouseID) {
        this.warehouseID = warehouseID;
    }

    /**
     * Overridden toString.
     * @return String of this object's data
     */
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

    /**
     * Overridden equals method for comparing this Product with another.
     * @param o other Object to compare to
     * @return true if this and o hold the same reference or contain the same data.
     */
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

    /**
     * Overridden hashCode method which hashes all of this Product's properties.
     * @return hash of all this Product's properties
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, price, discontinued, stock_exists, stock, warehouseID);
    }
}
