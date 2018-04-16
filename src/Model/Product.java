package Model;

public class Product {
    private int id;
    private String name;
    private String description;
    private double price;
    private boolean discontinued;
    private boolean stock_exists;

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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isDiscontinued() {
        return discontinued;
    }

    public void setDiscontinued(boolean discontinued) {
        this.discontinued = discontinued;
    }

    public boolean isStock_exists() {
        return stock_exists;
    }

    public void setStock_exists(boolean stock_exists) {
        this.stock_exists = stock_exists;
    }
}
