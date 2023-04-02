package models;

import java.util.ArrayList;
import java.util.List;

public class Client {
    private int id_client;
    private int money;
    private List<Product> shopping_cart;


    public Client() {
        this.id_client = 0;
        this.money = 0;
        this.shopping_cart = new ArrayList<Product>();
    }


    public Client(int id_client, int money, List<Product> shopping_cart) {
        this.id_client = id_client;
        this.money = money;
        this.shopping_cart = shopping_cart;
    }


    public int getId_client() {
        return this.id_client;
    }

    public void setId_client(int id_client) {
        this.id_client = id_client;
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
