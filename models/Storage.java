package models;

import java.util.Map;
import java.util.HashMap;

public class Storage {
    private Map<Product, Integer> storedProducts;
    private final int idStorage;
    private static int currentId;


    public Map<Product,Integer> getstoredProducts() {
        return this.storedProducts;
    }

    public void setstoredProducts(Map<Product,Integer> storedProducts) {
        this.storedProducts = storedProducts;
    }

    public int getIdStorage() {
        return this.idStorage;
    }

    public static void setCurrentId(int id) {
        currentId = id;
    }

    public Storage() {
        this.idStorage = ++currentId;
        this.storedProducts = new HashMap<Product, Integer>();
    }

    public Storage(Map<Product,Integer> storedProducts) {
        this.storedProducts = storedProducts;
        this.idStorage = ++currentId;
    }


    @Override
    public String toString() {
        return "Storage{" +
            " storedProducts='" + getstoredProducts() + "'" +
            ", idStorage='" + getIdStorage() + "'" +
            "}";
    }

    
}
