package models;

import java.util.Map;
import java.util.HashMap;


public class Distributor {
    private final int idDistributor;
    private static int currentId;
    private String name;
    private Map<Product,Integer> products;


    public Distributor() {
        this.idDistributor = ++currentId;
        this.name = "Nothing";
        this.products = new HashMap<Product,Integer>();
    }

    public Distributor(String name, Map<Product,Integer> products) {
        this.idDistributor = ++currentId;
        this.name = name;
        this.products = products;
    }

    public Distributor(String name) {
        this.idDistributor = ++currentId;
        this.name = name;
        this.products = new HashMap<Product,Integer>();
    }

    public int getIdDistributor() {
        return this.idDistributor;
    }

    public static void setCurrentId(int id) {
        currentId = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<Product,Integer> getProducts() {
        return this.products;
    }

    public void setProducts(Map<Product,Integer> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "Distributor{" +
            " idDistributor='" + getIdDistributor() + "'" +
            ", name='" + getName() + "'" +
            ", products='" + getProducts() + "'" +
            "}";
    }

}
