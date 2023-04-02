package models;

public abstract class Product {

    protected int id_product;
    protected String name;
    protected String brand;
    protected int cost;


    public Product() {
        this.id_product = 0;
        this.name = "Nothing";
        this.brand = "Nothing";
        this.cost = 0;
    }


    public Product(int id_product, String name, String brand, int cost) {
        this.id_product = id_product;
        this.name = name;
        this.brand = brand;
        this.cost = cost;
    }


    public int getId_product() {
        return this.id_product;
    }

    public void setId_product(int id_product) {
        this.id_product = id_product;
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
            " id_product='" + getId_product() + "'" +
            ", name='" + getName() + "'" +
            ", brand='" + getBrand() + "'" +
            ", cost='" + getCost() + "'" +
            "}";
    }

    
}
