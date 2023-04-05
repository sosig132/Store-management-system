package models;

public class Telephones extends Product {
    
    
    private String dimensions;


    public Telephones() {
        super();
        dimensions = "Nothing";
    }


    public Telephones(String name, String brand, int cost, String dimensions) {
        super(name, brand, cost);
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
            " idProduct='" + getIdProduct() + "'" +
            ", name='" + getName() + "'" +
            ", brand='" + getBrand() + "'" +
            ", cost='" + getCost() + "'" +
            ", dimensions='" + getDimensions() + "'" +
            "}";
    }

}
