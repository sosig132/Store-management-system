package view;

//PENTRU LISTA SORTATA BAG BANNED CLIENTS IN FUNCTIE DE CAT VOIAU SA FURE
//ordine: creare magazin, creare produse, creare depozit, comandam de la depozit, creare client, client merge la magazin, client cumpara

import models.*;
import service.Controller;
import java.util.Scanner;
import java.util.Arrays;

public class Main {

    private static Controller controller = new Controller();

    private static Scanner in = new Scanner(System.in);

    static{
        Client.setCurrentId(0);
        Distributor.setCurrentId(0);
        Product.setCurrentId(0);
        Storage.setCurrentId(0);
        Store.setCurrentId(0);
    }
    public static void main(String[] args){
        start();

    }
    
    public static void start(){
            int[] inputs = {1,2,3,4,5,6,7,8,9,10,11};
            

            int input;
            
        do{    
            System.out.println("What do you want to do?: ");
            System.out.println("1.Create entity");
            System.out.println("2.Take client shopping(if you created one)");
            System.out.println("3.Print clients");
            System.out.println("4.Print a specific client");
            System.out.println("5.Manage a store's stock");
            System.out.println("6.Print stores");
            System.out.println("7.Print specific store");
            System.out.println("8.Print distributors");
            System.out.println("9.Print specific distributor");
            System.out.println("10.Print products");
            System.out.println("11.Print specific product");
            System.out.println("12.Exit");
            input = in.nextInt();
        
            switch(input){
                case 1:{
                    createEntity();
                    break;
                }
                case 2:{
                    shopping();
                    break;
                }
                case 3:{
                    controller.getClients().sort((client1,client2)->client2.getMoney()-client1.getMoney());
                    int j = 0;
                    for (Client client : controller.getClients())    
                        System.out.println(Integer.toString(++j)+'.'+"Client"+Integer.toString(j)+" money: "+Integer.toString(client.getMoney()));
                    break;
                }
                case 4:{
                    printSpecificClient();
                    break;
                }
                case 5:{
                    System.out.println("Which store do you want to manage?");
                    int j = 0;
                    for(Store store : controller.getStores()){
                        System.out.println(Integer.toString(++j)+'.'+store.getName());
                    }
                    int inpuut = in.nextInt()-1;

                    manageStore(controller.getStores().get(inpuut));
                    break;
                }
                case 6:{
                    System.out.println(controller.getStores());
                    break;
                }
                case 7:{
                    printSpecificStore();
                    break;
                }
                case 8:{
                    System.out.println(controller.getDistributors());
                    break;
                }
                case 9:{
                    printSpecificDistributor();
                    break;
                }
                case 10:{
                    System.out.println(controller.getProducts());
                    break;
                }
                case 11:{
                    printSpecificProduct();
                    break;
                }
                case 12:{
                    System.exit(1);
                }
                default:{
                    System.out.println("Invalid input!");
                }
            }
        }while (!Arrays.asList(inputs).contains(input));
    }

    public static void manageStore(Store store){
        System.out.println("What do you want to do?");

        while(true){
            System.out.println("1.Order product");
            System.out.println("2.Move product from storage to available stock");
            System.out.println("3.Return");
            int input = in.nextInt();
            switch(input){
                case 1:{
                    controller.order(store);
                    break;
                }
                case 2:{
                    controller.move(store);
                    break;
                }
                case 3:{
                    return;
                }
                default: {
                    System.out.println("Invalid input!");
                    break;
                }
            }

        }
    }

    public static void shopping(){
        System.out.println("Which client do you want to use?");
        int j = 1;
        for (Client client : controller.getClients()){
            System.out.println(Integer.toString(j)+'.'+client);
            ++j;
        }
        
        int clientNr = in.nextInt()-1;
        while (clientNr>=controller.getClients().size()||clientNr<0){
            System.out.println("Invalid input");
            System.out.println("Which client do you want to use?");
            j = 1;
            for (Client client : controller.getClients()){
                System.out.println(Integer.toString(j)+'.'+client);
                ++j;
            }
            clientNr = in.nextInt()-1;
        }

        int storeNr;
        
        do{
            System.out.println("Which store do you want the client to go to?");
            j = 1;
            for (Store store : controller.getStores()){
                System.out.println(Integer.toString(j)+'.'+store.getName());
                ++j;
            }
            
            storeNr = in.nextInt()-1;
            
            if (storeNr>=controller.getStores().size()||storeNr<0){
                System.out.println("Invalid input");
            }
        
        }while (storeNr>=controller.getStores().size()||storeNr<0);
        if(controller.getStores().get(storeNr).getBannedClients().contains(controller.getClients().get(clientNr)))
            System.out.println("Client is banned from this store");    
        else{
            controller.putItemsInCart(clientNr,storeNr);
    
        }
    }
    public static void printSpecificProduct(){
        System.out.println("Please input the product's id: ");
        int input = in.nextInt();
        controller.printProduct(input);
    }

    public static void printSpecificDistributor(){
        System.out.println("Please input the distributor's id: ");
        int input = in.nextInt();
        
            controller.printDistributor(input);
    }


    public static void printSpecificStore(){
        System.out.println("Please input the store's id: ");
        int input = in.nextInt();
        controller.printStore(input);
    }

    public static void printSpecificClient(){
        System.out.println("Please input the client's id: ");
        int input = in.nextInt();
        controller.printClient(input);
    }

    public static void createEntity(){
        int input;
        System.out.println("What kind of entity do you want to create?: ");
        System.out.println("1.Store");
        System.out.println("2.Client");
        System.out.println("3.Product");
        do{
            input = in.nextInt();

            switch(input){
                case 1:{
                    controller.createStore();
                    break;
                }

                case 2:{
                    controller.createClient();
                    break;
                }

                case 3:{
                    controller.createProduct();
                    break;
                }
                default:{
                    System.out.println("Invalid input!");
                    break;
                }

            }
        }while(input!=1 && input!=2 && input!=3);
    }
}
