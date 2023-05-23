package view;
import java.util.Scanner;

import service.*;

public class MenuDB implements Menu {
    private  ControllerDB controller = ControllerDB.getInstance();
    
    private static Scanner in = new Scanner(System.in);


    public void start(){
        
        

        int input=-1;
            
        do{    
            System.out.println("What do you want to do?: ");
            System.out.println("1.Create entity");
            System.out.println("2.Take client shopping(if you created one)");
            System.out.println("3.Print clients");
            System.out.println("4.Update client");
            System.out.println("5.Manage a store's stock");
            System.out.println("6.Print stores");
            System.out.println("7.Update store");
            System.out.println("8.Print distributors");
            System.out.println("9.Print products");
            System.out.println("10.Delete store");
            System.out.println("11.Delete client");
            System.out.println("12.Delete product");
            System.out.println("13.Delete distributor");
            
            System.out.println("14.Exit");
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
                    controller.printClients();
                    break;
                }
                case 4:{
                    controller.updateClient();
                    break;
                }
                case 5:{
                    System.out.println("Which store do you want to manage?");
                    controller.printStores();
                    int inpuut =-1;

                    catch_error=true;
                    do{
                        if(in.hasNextInt()){   
                            inpuut = in.nextInt();
                            in.nextLine();
                            catch_error = false;
                        }
                        else{
                            in.nextLine();
                            System.out.println("Invalid input!");
                        }
                    }while(catch_error==true);
                    
                    manageStore(inpuut);
                    break;
                }
                case 6:{
                    controller.printStores();
                    break;
                }
                case 7:{
                    controller.updateStore();
                    break;
                }
                case 8:{
                    controller.printDistributors();
                    break;
                }

                case 9:{
                    controller.printProducts();
                    break;
                }

                case 10:{
                    controller.deleteStore();
                    break;
                }
                case 11:{
                    controller.deleteClient();
                    break;
                }
                case 12:{
                    controller.deleteProduct();
                    break;
                }
                case 13:{
                    controller.deleteDistributor();
                    break;
                }
                case 14:{
                    System.exit(1);
                }
                default:{
                    System.out.println("Invalid input!");
                }
            }
        }while (true);
    }

    public void manageStore(int inpuut){

        if (controller.checkStore(inpuut) == false){
            System.out.println("No store with that id");
            return;
        }
        
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
            
            
            
            switch(input){
                case 1:{
                    controller.order(inpuut);
                    break;
                }
                case 2:{
                    controller.move(inpuut);
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
        if(controller.checkClients()==false){
            System.out.println("You didn't create any clients!");
            return;
        }

        if(controller.checkStores()==false){
            System.out.println("You didn't create any stores!");
            return;
        }

        if(controller.checkProducts()==false){
            System.out.println("You didn't create any products!");
            return;
        }
        
        System.out.println("Which client do you want to use?");
        controller.printClients();
        
        int clientNr = -1;

        boolean catch_error=true;
        do{
            if(in.hasNextInt()){   
                clientNr = in.nextInt();
                in.nextLine();
                catch_error = false;
            }
            else{
                in.nextLine();
                System.out.println("Invalid input!");
            }
        }while(catch_error==true);
        


        int storeNr=-1;
        
        do{
            System.out.println("Which store do you want the client to go to?");
            
            catch_error=true;
            do{
                if(in.hasNextInt()){   
                    storeNr = in.nextInt();
                    in.nextLine();
                    catch_error = false;
                }
                else{
                    in.nextLine();
                    System.out.println("Invalid input!");
                }
            }while(catch_error==true);
            
            if (storeNr>controller.storesSize()||storeNr<0){
                System.out.println("Invalid input");
                return;
            }
        
        }while (storeNr>controller.storesSize()||storeNr<0);
        if(controller.checkIfClientBanned(clientNr, storeNr)==true){
            System.out.println("Client is banned from this store");    
            return;
        }
        controller.putItemsInCart(clientNr,storeNr);
    
        
    }

    @Override
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


