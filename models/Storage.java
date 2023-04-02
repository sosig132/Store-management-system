package models;

import java.util.Map;
import java.util.HashMap;

public class Storage {
    private Map<Product, Integer> stored_products;
    private final int id_storage;
    private static int current_id;


    public Map<Product,Integer> getstored_products() {
        return this.stored_products;
    }

    public void setstored_products(Map<Product,Integer> stored_products) {
        this.stored_products = stored_products;
    }

    public int getId_storage() {
        return this.id_storage;
    }

    public void setCurrent_id(int id) {
        current_id = id;
    }

    public Storage() {
        this.id_storage = ++current_id;
        this.stored_products = new HashMap<Product, Integer>();
    }

    public Storage(Map<Product,Integer> stored_products) {
        this.stored_products = stored_products;
        this.id_storage = ++current_id;
    }


    @Override
    public String toString() {
        return "Storage{" +
            " stored_products='" + getstored_products() + "'" +
            ", id_storage='" + getId_storage() + "'" +
            "}";
    }

    
}
