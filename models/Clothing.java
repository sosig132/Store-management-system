package models;

public class Clothing extends Product {
    private String material;
    private String size;


    public Clothing() {
        super();
        this.material = "Nothing";
        this.size = "Nothing";
    }

    public Clothing(String name, String brand, int cost){
        super(name, brand, cost);
        this.material = "Nothing";
        this.size = "Nothing";
    }

    public Clothing(String name, String brand, int cost,String material, String size) {
        super(name, brand, cost);
        
        this.material = material;
        this.size = size;
    }

    public String getMaterial() {
        return this.material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getSize() {
        return this.size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "{" +
            " id_product='" + getId_product() + "'" +
            ", name='" + getName() + "'" +
            ", brand='" + getBrand() + "'" +
            ", cost='" + getCost() + "'" +
            ", material='" + getMaterial() + "'" +
            ", size='" + getSize() + "'" +
            "}";
    }

}
