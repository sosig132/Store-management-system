package models;

import java.time.LocalDate;

public class Food extends Product{
    private LocalDate expiration_Date;


    public Food() {
        super();
        expiration_Date =  LocalDate.now();
    }

    public Food(String name, String brand, int cost){
        super(name, brand, cost);
        this.expiration_Date =  LocalDate.now();
    }

    public Food(String name, String brand, int cost, LocalDate expiration_Date) {
        super(name, brand, cost);
        this.expiration_Date = expiration_Date;
    }


    public LocalDate getExpiration_Date() {
        return this.expiration_Date;
    }

    public void setExpiration_Date(LocalDate expiration_LocalDate) {
        this.expiration_Date = expiration_LocalDate;
    }


    @Override
    public String toString() {
        return "Food{" +
            " id_product='" + getId_product() + "'" +
            ", name='" + getName() + "'" +
            ", brand='" + getBrand() + "'" +
            ", cost='" + getCost() + "'" +
            ", expiration_LocalDate='" + getExpiration_Date() + "'" +
            "}";
    }


}
