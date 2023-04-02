package models;

public class Telephones extends Product {
    private String dimensions;


    public Telephones() {
        super();
        dimensions = "Nothing";
    }


    public Telephones(int id_product, String name, String brand, int cost, String dimensions) {
        super(id_product, name, brand, cost);
        this.dimensions = dimensions;
    }


    public String getDimensions() {
        return this.dimensions;
    }

    public void setDimensions(String dimensions) {
        this.dimensions = dimensions;
    }
    

    @Override
    public String toString() {
        return "{" +
            " id_product='" + getId_product() + "'" +
            ", name='" + getName() + "'" +
            ", brand='" + getBrand() + "'" +
            ", cost='" + getCost() + "'" +
            ", dimensions='" + getDimensions() + "'" +
            "}";
    }

}
