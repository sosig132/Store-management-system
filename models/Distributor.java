package models;

import java.util.Map;
import java.util.HashMap;


public class Distributor {
    private final int id_distributor;
    private static int current_id;
    private String name;
    private Map<Product,Integer> products;


    public Distributor() {
        this.id_distributor = ++current_id;
        this.name = "Nothing";
        this.products = new HashMap<Product,Integer>();
    }

    public Distributor(String name, Map<Product,Integer> products) {
        this.id_distributor = ++current_id;
        this.name = name;
        this.products = products;
    }

    public Distributor(String name) {
        this.id_distributor = ++current_id;
        this.name = name;
        this.products = new HashMap<Product,Integer>();
    }

    public int getId_distributor() {
        return this.id_distributor;
    }

    public void setCurrent_id(int id) {
        current_id = id;
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
