package models;

import java.util.ArrayList;
import java.util.List;

public class Client {
    private final int id_client;
    private static int current_id;
    private int money;
    private List<Product> shopping_cart;


    public Client() {
        this.id_client = ++current_id;
        this.money = 0;
        this.shopping_cart = new ArrayList<Product>();
    }


    public Client(int id_client, int money, List<Product> shopping_cart) {
        this.id_client = ++current_id;
        this.money = money;
        this.shopping_cart = shopping_cart;
    }


    public int getId_client() {
        return this.id_client;
    }

    public void setCurrent_id(int id) {
        current_id = id;
    }

    public int getMoney() {
        return this.money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public List<Product> getShopping_cart() {
        return this.shopping_cart;
    }

    public void setShopping_cart(List<Product> shopping_cart) {
        this.shopping_cart = shopping_cart;
    }



    @Override
    public String toString() {
        return "Client{" +
            " id_client='" + getId_client() + "'" +
            ", money='" + getMoney() + "'" +
            ", shopping_cart='" + getShopping_cart() + "'" +
            "}";
    }


}
