package view;

import models.*;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args){

        Store magazin = new Store("Magazin", 0);

        magazin.getAvailable_products().put(new Food(1, "Snickers","Nestle", 3, LocalDate.of(2024, 2, 1)), 100);

        System.out.println(magazin.toString());

    }
}
