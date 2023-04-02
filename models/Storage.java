package models;

import java.util.Map;
import java.util.HashMap;

public class Storage {
    private Map<Product, Integer> stored_products;
    private int id_storage;


    public Map<Product,Integer> getstored_products() {
        return this.stored_products;
    }

    public void setstored_products(Map<Product,Integer> stored_products) {
        this.stored_products = stored_products;
    }

    public int getId_storage() {
        return this.id_storage;
    }

    public void setId_storage(int id_storage) {
        this.id_storage = id_storage;
    }

    public Storage() {
        this.id_storage = 0;
        this.stored_products = new HashMap<Product, Integer>();
    }

    public Storage(int id_storage) {
        this.stored_products = new HashMap<Product, Integer>();
        this.id_storage = id_storage;
    }


    public Storage(Map<Product,Integer> stored_products, int id_storage) {
        this.stored_products = stored_products;
        this.id_storage = id_storage;
    }


    @Override
    public String toString() {
        return "Storage{" +
            " stored_products='" + getstored_products() + "'" +
            ", id_storage='" + getId_storage() + "'" +
            "}";
    }

    
}
