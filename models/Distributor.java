package models;

import java.util.Map;
import java.util.HashMap;


public class Distributor {
    private int id_distributor;
    private String name;
    private Map<Product,Integer> products;


    public Distributor() {
        this.id_distributor = 0;
        this.name = "Nothing";
        this.products = new HashMap<Product,Integer>();
    }

    public Distributor(int id_distributor, String name, Map<Product,Integer> products) {
        this.id_distributor = id_distributor;
        this.name = name;
        this.products = products;
    }

    public Distributor(int id_distributor, String name) {
        this.id_distributor = id_distributor;
        this.name = name;
        this.products = new HashMap<Product,Integer>();
    }

    public int getId_distributor() {
        return this.id_distributor;
    }

    public void setId_distributor(int id_distributor) {
        this.id_distributor = id_distributor;
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
            " id_distributor='" + getId_distributor() + "'" +
            ", name='" + getName() + "'" +
            ", products='" + getProducts() + "'" +
            "}";
    }

}
