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

        client.setMoney(in.nextInt());
        
        return client;//todo
    }

    public Product inputProduct(){
        
        Product product = new Product();
        System.out.println("Please input what kind of product you want to create: ");
        System.out.println("1.Food ");
        System.out.println("2.Clothing ");
        System.out.println("3.Sport ");
        System.out.println("4.Telephone ");
        int typeOfProduct=0;
        do{
            typeOfProduct = in.nextInt();
            
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
        product.setCost(in.nextInt());
        
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
        do{
            System.out.println("What do you want to buy?:");
            int j = 1;
            for(Product product : store.getAvailableProducts().keySet()){
                System.out.println(Integer.toString(j)+'.'+product);
                ++j;
            }

            int cost = 0;

            int input = in.nextInt();
            j=1;
            for(Product product : store.getAvailableProducts().keySet()){
                System.out.println(Integer.toString(j)+'.'+product);
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

    public void pay(Client client, int cost){
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

        

        storage.getstoredProducts().put(products.get(input), in.nextInt());
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
        
        store.getAvailableProducts().put(products.get(input), Math.max(inpuut, storage.getstoredProducts().get(products.get(input))));

        storage.getstoredProducts().put(products.get(input), storage.getstoredProducts().get(products.get(input))-Math.max(inpuut, storage.getstoredProducts().get(products.get(input))));

    }
}


