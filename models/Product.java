package models;

public abstract class Product {

    protected final int id_product;
    protected String name;
    protected String brand;
    protected int cost;
    protected static int current_id;


    public Product() {
        this.id_product = ++current_id;
        this.name = "Nothing";
        this.brand = "Nothing";
        this.cost = 0;
    }


    public Product( String name, String brand, int cost) {
        this.id_product = ++current_id;
        this.name = name;
        this.brand = brand;
        this.cost = cost;
    }


    public int getId_product() {
        return this.id_product;
    }

    public static void setCurrent_id(int id) {
        current_id = id;
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
