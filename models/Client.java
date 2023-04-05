package models;

import java.util.ArrayList;
import java.util.List;

public class Client {
    private final int idClient;
    private static int currentId;
    private int money;
    private List<Product> shoppingCart;


    public Client() {
        this.idClient = ++currentId;
        this.money = 0;
        this.shoppingCart = new ArrayList<Product>();
    }


    public Client(int idClient, int money, List<Product> shoppingCart) {
        this.idClient = ++currentId;
        this.money = money;
        this.shoppingCart = shoppingCart;
    }


    public int getIdClient() {
        return this.idClient;
    }

    public static void setCurrentId(int id) {
        currentId = id;
    }

    public int getMoney() {
        return this.money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public List<Product> getShoppingCart() {
        return this.shoppingCart;
    }

    public void setShoppingCart(List<Product> shoppingCart) {
        this.shoppingCart = shoppingCart;
    }



    @Override
    public String toString() {
        return "Client{" +
            " idClient='" + getIdClient() + "'" +
            ", money='" + getMoney() + "'" +
            ", shoppingCart='" + getShoppingCart() + "'" +
            "}";
    }


}
