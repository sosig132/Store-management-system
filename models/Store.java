package models;
import java.util.*;


public class Store {
    private String name;
    private Integer id_store;
    private List<Integer> banned_customers = new ArrayList<Integer>();
    
    //constructors
    
    public Store() {
        this.name = "Nothing";
        this.id_store = 0;
    }

    public Store(String name, Integer id_store) {
        this.name = name;
        this.id_store = id_store;
    }
    
    //getters setters

    public String getName() {
        return this.name;
    }


    public void setName(String name) {
        this.name = name;
    }

    public Integer getId_store() {
        return this.id_store;
    }

    public void setId_store(Integer id_store) {
        this.id_store = id_store;
    }
    public List<Integer> getBanned_customers() {
        return this.banned_customers;
    }

    public void setBanned_customers(List<Integer> banned_customers) {
        this.banned_customers = banned_customers; 



        

    @Override
    public String toString() {
        return "Store{" +
            " name='" + getName() + "'" +
            ", id_store='" + getId_store() + "'" +
            "}";
    }



    
}
