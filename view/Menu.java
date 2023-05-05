package view;

import models.Store;

public interface Menu {

    void start();

    void manageStore(Store store);

    void shopping();

    void printSpecificProduct();

    void printSpecificDistributor();

    void printSpecificStore();

    void printSpecificClient();

}