package service;

public interface InterfaceController {
    void createStore();

    void createStorage();

    void createProduct();

    void createDistributor(String name);

    void createClient();
    
    void printStore(int id);

    void printStorage(int id);

    void printProduct(int id);

    void printDistributor(int id);

    void printClient(int id);
}
