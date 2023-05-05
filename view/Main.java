package view;

//import java.util.Scanner;

public class Main {

    //private static final Menu menu = MenuInMemory.getInstance();
    //private static final MenuDB menuDB = MenuDB.getInstance();
    private static Menu menu;
    //private static Scanner in = new Scanner(System.in);
    public static void main(String[] args){

        menu = MenuFactory.createMenu();

        menu.start();
    }
}
