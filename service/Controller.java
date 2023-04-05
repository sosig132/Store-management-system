package service;

import models.*;
//pentru produse, utilizatorul creeaza produse, din asta fac eu distribuitori 
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;


public class Controller implements InterfaceController{

    private Scanner in;
    private List<Store> stores;
    private List<Storage> storages;
    private List<Product> products;
    private List<Client> clients;
    private List<Distributor> distributors;

    public Controller(){
        stores = new ArrayList<Store>();
        storages = new ArrayList<Storage>();
        products = new ArrayList<Product>();
        clients = new ArrayList<Client>();
        distributors = new ArrayList<Distributor>();

        in = new Scanner(System.in);
    }

    @Override
    public void createStore(){
        System.out.println("Enter the store's name: ");
        String storeName = in.nextLine();
        stores.add(new Store(storeName));
    }
    @Override 
    public void createStorage(){
        storages.add(new Storage());
    }
    @Override
    public void createProduct(){
        products.add(inputProduct());
    }
    @Override
    public void createDistributor(){
        distributors.add(new Distributor());//todo
    }
    @Override
    public void createClient(){
        clients.add(inputClient());
    }

    public Client inputClient(){
        return new Client();//todo
    }

    public Product inputProduct(){
        return new Food();//todo
    }

    public List<Store> getStores(){
        return stores;
    }

    public List<Product> getProducts(){
        return products;
    }

    public List<Client> getClients() {
        return clients;
    }

    public List<Distributor> getDistributors() {
        return distributors;
    }

    

}
