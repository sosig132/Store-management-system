package models;

import java.time.LocalDate;

public class Food extends Product{
    private LocalDate expirationDate;


    public Food() {
        super();
        expirationDate =  LocalDate.now();
    }

    public Food(String name, String brand, int cost){
        super(name, brand, cost);
        this.expirationDate =  LocalDate.now();
    }

    public Food(String name, String brand, int cost, LocalDate expirationDate) {
        super(name, brand, cost);
        this.expirationDate = expirationDate;
    }


    public LocalDate getExpirationDate() {
        return this.expirationDate;
    }

    public void setExpirationDate(LocalDate expirationLocalDate) {
        this.expirationDate = expirationLocalDate;
    }


    @Override
    public String toString() {
        return "Food{" +
            " idProduct='" + getIdProduct() + "'" +
            ", name='" + getName() + "'" +
            ", brand='" + getBrand() + "'" +
            ", cost='" + getCost() + "'" +
            ", expirationLocalDate='" + getExpirationDate() + "'" +
            "}";
    }


}
