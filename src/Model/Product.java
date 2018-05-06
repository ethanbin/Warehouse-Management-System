package Model;

import java.util.Objects;

public class Product {
    private int id;
    private String name;
    private String description;
    private double price;
    private boolean discontinued;
    private boolean stock_exists;
    private int stock;

    public Product(int id, String name, String description, double price, boolean discontinued,
                   boolean stock_exists, int stock) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.discontinued = discontinued;
        this.stock_exists = stock_exists;
        this.stock = stock;
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

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
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
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id &&
                Double.compare(product.price, price) == 0 &&
                discontinued == product.discontinued &&
                stock_exists == product.stock_exists &&
                stock == product.stock &&
                Objects.equals(name, product.name) &&
                Objects.equals(description, product.description);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, description, price, discontinued, stock_exists, stock);
    }
}
