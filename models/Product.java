package models;

public abstract class Product {

    protected final int idProduct;
    protected String name;
    protected String brand;
    protected int cost;
    protected static int currentId;


    public Product() {
        this.idProduct = ++currentId;
        this.name = "Nothing";
        this.brand = "Nothing";
        this.cost = 0;
    }


    public Product( String name, String brand, int cost) {
        this.idProduct = ++currentId;
        this.name = name;
        this.brand = brand;
        this.cost = cost;
    }


    public int getIdProduct() {
        return this.idProduct;
    }

    public static void setCurrentId(int id) {
        currentId = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return this.brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getCost() {
        return this.cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }



    @Override
    public String toString() {
        return "Product{" +
            " idProduct='" + getIdProduct() + "'" +
            ", name='" + getName() + "'" +
            ", brand='" + getBrand() + "'" +
            ", cost='" + getCost() + "'" +
            "}";
    }

    
}
