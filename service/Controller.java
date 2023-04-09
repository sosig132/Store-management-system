package service;

import models.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class Controller implements ControllerInterface{

    private static Controller controller = null;
    private Scanner in;
    private List<Store> stores;
    private List<Storage> storages;
    private List<Product> products;
    private List<Client> clients;
    private List<Distributor> distributors;

    private Controller(){
        stores = new ArrayList<Store>();
        storages = new ArrayList<Storage>();
        products = new ArrayList<Product>();
        clients = new ArrayList<Client>();
        distributors = new ArrayList<Distributor>();

        in = new Scanner(System.in);
    }

    public static Controller getInstance(){
        if(controller == null){
            controller = new Controller();
        }
        return controller;
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
    public void createDistributor(String name){
        int check = 0;
        for(Distributor di : distributors){
            if(di.getName().equals(name)){
                check=1;
            }
        }
        if (check==0){
            distributors.add(new Distributor(name));
        }
    }
    @Override
    public void createClient(){
        clients.add(inputClient());
    }

    private Client inputClient(){
        
        Client client = new Client();
        
        System.out.println("Please input how much money the client has:");
        boolean catch_error = true;
        
        do{
            if(in.hasNextInt()){   
                client.setMoney(in.nextInt());
                in.nextLine();
                catch_error = false;
            }
            else{
                in.nextLine();
                System.out.println("Invalid input! Try again");
            }
        }while(catch_error==true);
        return client;
    }

    private Product inputProduct(){
        
        Product product = new Product();
        System.out.println("Please input what kind of product you want to create: ");
        System.out.println("1.Food ");
        System.out.println("2.Clothing ");
        System.out.println("3.Sport ");
        System.out.println("4.Telephone ");
        int typeOfProduct=0;
        do{
            boolean catch_error = true;

            do{
                if(in.hasNextInt()){    
                    typeOfProduct = in.nextInt();
                    in.nextLine();
                    catch_error = false;
                }
                else{
                    in.nextLine();
                    System.out.println("Invalid input!");
                }
            }while(catch_error==true);
            
            switch(typeOfProduct){
                case 1 :{
                    product = new Food();
                    break;
                }

                case 2:{
                    product = new Clothing();

                    break;
                }
                case 3:{
                    product = new Sport();
                    break;
                }
                case 4:{
                    product = new Telephone();
                    break;
                }

                default:{
                    System.out.println("Invalid input!");

                }
            }
        }while(typeOfProduct!=1 && typeOfProduct!=2 && typeOfProduct!=3 && typeOfProduct!=4);

        System.out.println("Please input your product's name: ");
        in.nextLine();
        product.setName(in.nextLine());


        System.out.println("Please input your product's brand: ");
        //in.nextLine();
        product.setBrand(in.nextLine());

        System.out.println("Please input your product's price: ");
        //in.nextLine();
        boolean catch_error = true;
        
        do{
            if(in.hasNextInt()){   
                product.setCost(in.nextInt());
                in.nextLine();
                catch_error = false;
            }
            else{
                in.nextLine();
                System.out.println("Invalid input! Try again");
            }
        }while(catch_error==true);
        
        createDistributor(product.getBrand());
        

        switch(typeOfProduct){
            case 1 :{
                
                System.out.println("Please input how much protein your food has: ");
                ((Food)product).setProtein(in.nextInt());

                break;

            }
            case 2:{
                System.out.println("Please input your clothing article's material: ");
                in.nextLine();
                ((Clothing)product).setMaterial(in.nextLine());

                System.out.println("Please input your clothing article's size: ");
                ((Clothing)product).setSize(in.nextLine());
                break;
            }
            case 3:{
                in.nextLine();
                System.out.println("Please input what sport your product is for: ");
                ((Sport)product).setSport(in.nextLine());
                break;
            }
            case 4:{
                in.nextLine();
                System.out.println("Please input the phone's height: ");
                ((Telephone)product).setHeight(in.nextInt());
                System.out.println("Please input the phone's width: ");
                ((Telephone)product).setWidth(in.nextInt());
                break;
            }
        }
        return product;
    }



    @Override
    public List<Store> getStores(){
        return stores;
    }

    @Override
    public List<Product> getProducts(){
        return products;
    }

    @Override
    public List<Client> getClients() {
        return clients;
    }

    @Override
    public List<Distributor> getDistributors() {
        return distributors;
    }

    @Override
    public void printClient(int id){
        int finished=0;

        for(Client client : clients){
            if(client.getIdClient() == id){
                System.out.println(client);
                finished=1;
            }
        }
        if (finished==0){
            System.out.println("There is no client with that id");
        }
    }
    @Override
    public void printProduct(int id){
        int finished=0;
        for(Product product : products){
            if(product.getIdProduct() == id){
                System.out.println(product);
                finished=1;
            }
        }
        if (finished==0){
            System.out.println("There is no product with that id");
        }
    }
    @Override
    public void printDistributor(int id){
        int finished=0;

        for(Distributor distr : distributors){
            if(distr.getIdDistributor() == id){
                System.out.println(distr);
                finished=1;
            }
        }
        if (finished==0){
            System.out.println("There is no distributor with that id");
        }
    }
    @Override
    public void printStore(int id){
        int finished=0;

        for(Store store : stores){
            if(store.getIdStore() == id){
                System.out.println(store);
                finished=1;
            }
        };
        if (finished==0){
            System.out.println("There is no store with that id");
        }
    }
    @Override
    public void printStorage(int id){
        int finished = 0;
        for(Storage storage : storages){
            if(storage.getIdStorage() == id){
                System.out.println(storage);
                finished=1;
            }
        };
        if (finished==0){
            System.out.println("There is no storage with that id");
        }
    }

    public void putItemsInCart(int clientId, int storeId){
        Client client = clients.get(clientId);
        Store store = stores.get(storeId);
        int cost = 0;
        do{
            System.out.println("What do you want to buy?:");
            int j = 1;
            for(Product product : store.getAvailableProducts().keySet()){
                System.out.println(Integer.toString(j)+'.'+product);
                ++j;
            }


            int input = in.nextInt();
            j=0;
            for(Product product : store.getAvailableProducts().keySet()){
                System.out.println(Integer.toString(++j)+'.'+product);
                if(j==input){
                    if(store.getAvailableProducts().get(product)>0){
                        client.getShoppingCart().add(product);
                          
                        store.getAvailableProducts().put(product,store.getAvailableProducts().get(product)-1);
                        cost+=product.getCost();
                    }
                    else{
                        System.out.println("The product is not in stock");
                    }
                    break;
                }
            }
            
            if (cost > client.getMoney()){
                store.getBannedClients().add(client);
                System.out.println("Client has been banned from store");
                return;
            }

            System.out.println("If you want to go pay, type "+ Integer.toString(store.getAvailableProducts().size()+2));

            in.nextLine();
            input = in.nextInt();

            if (input == store.getAvailableProducts().size()+2){
                pay(client, cost);
                return;
            }

        }while(true);
    }

    private void pay(Client client, int cost){
        client.setMoney(client.getMoney()-cost);
    }

    public void order(Store store){
        Storage storage = store.getStorage();

        System.out.println("What do you want to order? ");
        int j = 0;
        for(Product product: products){
            System.out.println(Integer.toString(++j)+'.'+product);
        }

            int input = in.nextInt()-1;
            if(input<0 || j<input){
                System.out.println("Invalid input!");
                return;
            }

        System.out.println("How many?");

        in.nextLine();

        int inputt = in.nextInt();

        if(inputt <= 0){
            System.out.println("Invalid input!");
            return;
        }

        storage.getstoredProducts().put(products.get(input), inputt);
    }

    public void move(Store store){
        Storage storage = store.getStorage();
        System.out.println("What do you want to move?");
        int j = 0;
        for(Product product : storage.getstoredProducts().keySet()){
            System.out.println(Integer.toString(++j)+'.'+product+" quantity: "+storage.getstoredProducts().get(product));
        }

        int input = in.nextInt()-1;
        if(input<0 || j<input){
            System.out.println("Invalid input!");
            return;
        }
        System.out.println("How many do you want to move?");

        in.nextLine();
        int inpuut = in.nextInt();

        if(inpuut <= 0){
            System.out.println("Invalid input!");
            return;
        }
        
        int toMove=Math.min(inpuut, storage.getstoredProducts().get(products.get(input)));

        store.getAvailableProducts().put(products.get(input), toMove);

        if(storage.getstoredProducts().get(products.get(input))-toMove>0){    
            storage.getstoredProducts().put(products.get(input), storage.getstoredProducts().get(products.get(input))-toMove);
        }
        else{
            storage.getstoredProducts().remove(products.get(input));
        }
    }

}


