package models;

public class Food extends Product{
    private int protein;


    public Food() {
        super();
        protein = 0;
    }

    public Food(String name, String brand, int cost){
        super(name, brand, cost);
        this.protein = 0;
    }

    public Food(String name, String brand, int cost, int protein) {
        super(name, brand, cost);
        this.protein = protein;
    }


    public int getProtein() {
        return this.protein;
    }

    public void setProtein(int protein) {
        this.protein = protein;
    }


    @Override
    public String toString() {
        return "Food{" +
            " idProduct='" + getIdProduct() + "'" +
            ", name='" + getName() + "'" +
            ", brand='" + getBrand() + "'" +
            ", cost='" + getCost() + "'" +
            ", protein='" + getProtein() + "'" +
            "}";
    }


}
