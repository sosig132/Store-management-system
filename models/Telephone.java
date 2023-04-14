package models;



public class Telephone extends Product {
    
    
    private int height;
    private int width;


    public Telephone() {
        super();
        height = 0;
        width = 0;
    }


    public Telephone(String name, String brand, int cost, int height, int width) {
        super(name, brand, cost);
        this.height = height;
        this.width = width;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
    

    @Override
    public String toString() {
        return "Telephone{" +
            " idProduct='" + getIdProduct() + "'" +
            ", name='" + getName() + "'" +
            ", brand='" + getBrand() + "'" +
            ", cost='" + getCost() + "'" +
            ", dimensions='" + getHeight() + 'x' + getWidth() + "'" +
            "}";
    }

}
