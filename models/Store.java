package models;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;


public class Store {
    private String name;
    private static int currentId;
    private final int idStore;
    private List<Client> bannedClients;
    private Map<Product, Integer> availableProducts;
    private Storage storage;


    //constructors
    
    


    public Store() {
        this.name = "Nothing";
        this.idStore = ++currentId;
        this.bannedClients = new ArrayList<Client>();
        this.availableProducts=new HashMap<Product, Integer>();
        this.storage = new Storage();
    }

    public Store(String name, List<Client> bannedCustomers, Map<Product, Integer> availableProducts, Storage storage) {
        this.name = name;
        this.idStore = ++currentId;
        this.bannedClients = bannedCustomers;
        this.availableProducts=availableProducts;
        this.storage = storage;
    }

    public Store(String name, Map<Product, Integer> availableProducts) {
        this.name = name;
        this.idStore = ++currentId;
        this.bannedClients = new ArrayList<Client>();
        this.availableProducts=availableProducts;
        this.storage = new Storage();
    }

    public Store(String name) {
        this.name = name;
        this.idStore = ++currentId;
        this.bannedClients = new ArrayList<Client>();
        this.availableProducts=new HashMap<Product, Integer>();
        this.storage = new Storage();
    }
    
    //getters setters

    public String getName() {
        return this.name;
    }


    public void setName(String name) {
        this.name = name;
    }

    public Integer getIdStore() {

        return this.idStore;
    }



    public Storage getStorage() {
        return this.storage;
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }


    public static void setCurrentId(Integer id) {
        currentId = id;
    }

    public List<Client> getBannedClients() {
        return this.bannedClients;
    }

    public void setBannedClients(List<Client> bannedClients) {
        this.bannedClients = bannedClients;
    }

    public Map<Product,Integer> getAvailableProducts() {
        return this.availableProducts;
    }

    public void setAvailableProducts(Map<Product,Integer> availableProducts) {
        this.availableProducts = availableProducts;
    }
        

    

    

    @Override
    public String toString() {
        return "Store{" +
            " name='" + getName() + "'" +
            ", idStore='" + getIdStore() + "'" +
            ", bannedClients='" + getBannedClients() + "'" +
            ", availableProducts='" + getAvailableProducts() + "'" +
            ", storage='" + getStorage() + "'" +
            "}";
    }


    
}
