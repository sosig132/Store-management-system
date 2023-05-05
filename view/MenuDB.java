package view;
import java.util.Scanner;

import models.*;
import service.*;

public class MenuDB implements Menu {
    private  ControllerDB controller = ControllerDB.getInstance();
    private static MenuDB menu = null;
    private MenuDB(){}

    public static MenuDB getInstance(){

        if(menu==null){
            menu = new MenuDB();
        }
        return menu;

    }
    
    private static Scanner in = new Scanner(System.in);

    public void start(){
        
        

        int input=-1;
            
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
            boolean catch_error=true;
            do{
                if(in.hasNextInt()){   
                    input = in.nextInt();
                    in.nextLine();
                    catch_error = false;
                }
                else{
                    in.nextLine();
                    System.out.println("Invalid input!");
                }
            }while(catch_error==true);
        
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
                    /*controller.getClients().sort((client1,client2)->client2.getMoney()-client1.getMoney());
                    int j = 0;
                    for (Client client : controller.getClients())    
                        System.out.println(Integer.toString(++j)+'.'+"Client"+Integer.toString(j)+" money: "+Integer.toString(client.getMoney()));
                    break;*/
                    controller.printClients();
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
                    int inpuut =-1;

                    catch_error=true;
                    do{
                        if(in.hasNextInt()){   
                            inpuut = in.nextInt()-1;
                            in.nextLine();
                            catch_error = false;
                        }
                        else{
                            in.nextLine();
                            System.out.println("Invalid input!");
                        }
                    }while(catch_error==true);

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
        }while (true);
    }

    public void manageStore(Store store){
        System.out.println("What do you want to do?");

        while(true){
            System.out.println("1.Order product");
            System.out.println("2.Move product from storage to available stock");
            System.out.println("3.Return");
            int input = -1;
            boolean catch_error=true;
            do{
                if(in.hasNextInt()){   
                    input = in.nextInt();
                    in.nextLine();
                    catch_error = false;
                }
                else{
                    in.nextLine();
                    System.out.println("Invalid input!");
                }
            }while(catch_error==true);
            
            
            int store_id=0;
            switch(input){
                case 1:{
                    controller.order(store_id);
                    break;
                }
                case 2:{
                    controller.move(store_id);
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

    public void shopping(){
        if(controller.getClients().isEmpty()){
            System.out.println("You didn't create any clients!");
            return;
        }

        if(controller.getStores().isEmpty()){
            System.out.println("You didn't create any stores!");
            return;
        }

        if(controller.getProducts().isEmpty()){
            System.out.println("You didn't create any products!");
            return;
        }
        
        System.out.println("Which client do you want to use?");
        int j = 1;
        for (Client client : controller.getClients()){
            System.out.println(Integer.toString(j)+'.'+client);
            ++j;
        }
        
        int clientNr = -1;

        boolean catch_error=true;
            do{
                if(in.hasNextInt()){   
                    clientNr = in.nextInt()-1;
                    in.nextLine();
                    catch_error = false;
                }
                else{
                    in.nextLine();
                    System.out.println("Invalid input!");
                }
            }while(catch_error==true);
        while (clientNr>=controller.getClients().size()||clientNr<0){
            System.out.println("Invalid input");
            System.out.println("Which client do you want to use?");
            j = 1;
            for (Client client : controller.getClients()){
                System.out.println(Integer.toString(j)+'.'+client);
                ++j;
            }
            catch_error=true;
            do{
                if(in.hasNextInt()){   
                    clientNr = in.nextInt()-1;
                    in.nextLine();
                    catch_error = false;
                }
                else{
                    in.nextLine();
                    System.out.println("Invalid input!");
                }
            }while(catch_error==true);
        }

        int storeNr=-1;
        
        do{
            System.out.println("Which store do you want the client to go to?");
            j = 1;
            for (Store store : controller.getStores()){
                System.out.println(Integer.toString(j)+'.'+store.getName());
                ++j;
            }
            

            catch_error=true;
            do{
                if(in.hasNextInt()){   
                    storeNr = in.nextInt()-1;
                    in.nextLine();
                    catch_error = false;
                }
                else{
                    in.nextLine();
                    System.out.println("Invalid input!");
                }
            }while(catch_error==true);
            
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
    public void printSpecificProduct(){
        if(controller.getProducts().isEmpty()){
            System.out.println("There are no products!");
            return;
        }
        for(Product p : controller.getProducts()){
            System.out.println(Integer.toString(p.getIdProduct())+". "+p.getName());
        }

        System.out.println("Please input the product's id: ");
        int input = -1;
        boolean catch_error=true;
        do{
            if(in.hasNextInt()){   
                input = in.nextInt();
                in.nextLine();
                catch_error = false;
            }
            else{
                in.nextLine();
                System.out.println("Invalid input!");
            }
        }while(catch_error==true);
        controller.printProduct(input);
    }

    public void printSpecificDistributor(){
        if(controller.getDistributors().isEmpty()){
            System.out.println("There are no distributors!");
            return;
        }
        for(Distributor d : controller.getDistributors()){
            System.out.println(Integer.toString(d.getIdDistributor())+". "+d.getName());
        }

        System.out.println("Please input the distributor's id: ");
        int input = -1;
        boolean catch_error=true;
        do{
            if(in.hasNextInt()){   
                input = in.nextInt();
                in.nextLine();
                catch_error = false;
            }
            else{
                in.nextLine();
                System.out.println("Invalid input!");
            }
        }while(catch_error==true);
        
        controller.printDistributor(input);
    }


    public void printSpecificStore(){
        if(controller.getStores().isEmpty()){
            System.out.println("There are no stores!");
            return;
        }

        for(Store s : controller.getStores()){
            System.out.println(Integer.toString(s.getIdStore())+". "+s.getName());
        }

        System.out.println("Please input the store's id: ");
        int input = -1;
        boolean catch_error=true;
        do{
            if(in.hasNextInt()){   
                input = in.nextInt();
                in.nextLine();
                catch_error = false;
            }
            else{
                in.nextLine();
                System.out.println("Invalid input!");
            }
        }while(catch_error==true);
        controller.printStore(input);
    }

    public void printSpecificClient(){
        if(controller.getClients().isEmpty()){
            System.out.println("There are no clients!");
            return;
        }

        for(Client c : controller.getClients()){
            System.out.println(Integer.toString(c.getIdClient())+". Client" + Integer.toString(c.getIdClient()));
        }

        System.out.println("Please input the client's id: ");
        int input = -1;
        boolean catch_error=true;
        do{
            if(in.hasNextInt()){   
                input = in.nextInt();
                in.nextLine();
                catch_error = false;
            }
            else{
                in.nextLine();
                System.out.println("Invalid input!");
            }
        }while(catch_error==true);
        controller.printClient(input);
    }

    public void createEntity(){
        int input=-1;
        System.out.println("What kind of entity do you want to create?: ");
        System.out.println("1.Store");
        System.out.println("2.Client");
        System.out.println("3.Product");
        do{
            boolean catch_error=true;
            do{
                if(in.hasNextInt()){   
                    input = in.nextInt();
                    in.nextLine();
                    catch_error = false;
                }
                else{
                    in.nextLine();
                    System.out.println("Invalid input!");
                }
            }while(catch_error==true);

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


