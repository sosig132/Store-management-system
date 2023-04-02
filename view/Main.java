package view;

import models.*;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args){
        Store.setCurrent_id(0);
        Product.setCurrent_id(0);
        Store magazin = new Store("Magazin");

        magazin.getAvailable_products().put(new Food("Snickers","Nestle", 3, LocalDate.of(2024, 2, 1)), 100);
        magazin.getAvailable_products().put(new Food("Sickers","Nestle", 3, LocalDate.of(2024, 2, 1)), 100);
        System.out.println(magazin.toString());

    }
}
