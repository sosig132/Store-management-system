package service;

import java.util.List;

import models.Client;
import models.Distributor;
import models.Product;
import models.Store;

public interface Controller {

    void createStore();

    void createProduct();

    void createDistributor(String name);

    void createClient();

    List<Store> getStores();

    List<Product> getProducts();

    List<Client> getClients();

    List<Distributor> getDistributors();



}