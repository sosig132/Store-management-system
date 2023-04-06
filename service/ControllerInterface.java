package service;

import java.util.List;

import models.Client;
import models.Distributor;
import models.Product;
import models.Store;

public interface ControllerInterface {

    void createStore();

    void createStorage();

    void createProduct();

    void createDistributor(String name);

    void createClient();

    List<Store> getStores();

    List<Product> getProducts();

    List<Client> getClients();

    List<Distributor> getDistributors();

    void printClient(int id);

    void printProduct(int id);

    void printDistributor(int id);

    void printStore(int id);

    void printStorage(int id);

}