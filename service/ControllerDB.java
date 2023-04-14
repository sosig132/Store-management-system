package service;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import models.Client;
import models.Distributor;
import models.Product;
import models.Store;


public class ControllerDB implements ControllerInterface {

    private static ControllerDB controllerDB = null;
    private ControllerDB(){}
    private static final Scanner in = new Scanner(System.in);

    private static final String myFile = "./changes.csv";
    
    public static ControllerDB getInstance(){
        if(controllerDB == null){
            controllerDB = new ControllerDB();
        }
        return controllerDB;
    }
    

    private void write(String file, String content) throws IOException{
        FileWriter writer = new FileWriter(file, true);

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDate = now.format(formatter);

        writer.append(content);
        writer.append(", ");
        writer.append(formattedDate+"\n");
        writer.flush();
        writer.close();

    }

    public void putItemsInCart(int client_id, int store_id){}


    @Override
    public void createStore() {
        System.out.println("Enter the store's name: ");
        String name = in.nextLine();
        
        
        try{

            Connection connection = ConnectDB.getInstance();
            String sqlInsert = "INSERT INTO store (name) VALUES (?) ";
            PreparedStatement statement = connection.prepareStatement(sqlInsert);
            statement.setString(1,name);
            int rows = statement.executeUpdate();
            System.out.println(rows+" row affected");

            statement.close();
            connection.close();
            try{
                write(myFile, "Inserted "+name+" into table stores");
        
            }
            catch(IOException e){
                System.out.println("Coulnt't write to file");
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
    
    public void order(int store_id){}
    public void move(int store_id){}

    
    public void createStorage() {
    }

    
    public void createProduct() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createProduct'");
    }

    
    public void createDistributor(String name) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createDistributor'");
    }

    
    public void createClient() {
        System.out.println("Please input how much money the client has: ");
        int money = 0;

        boolean catch_error = true;
        
        do{
            if(in.hasNextInt()){   
                
                money = in.nextInt();
                
                in.nextLine();
                catch_error = false;
                if (money < 0){
                    catch_error=true;
                    System.out.println("Invalid input!");
                }
            }
            else{
                in.nextLine();
                System.out.println("Invalid input!");
            }
        }while(catch_error==true);
        
        
        try{

            Connection connection = ConnectDB.getInstance();
            String sqlInsert = "INSERT INTO clients (money) VALUES (?) ";
            PreparedStatement statement = connection.prepareStatement(sqlInsert);
            statement.setInt(1,money);
            int rows = statement.executeUpdate();
            System.out.println(rows+" row affected");

            statement.close();
            connection.close();
            try{
                write(myFile, "Inserted client into table clients");
        
            }
            catch(IOException e){
                System.out.println("Coulnt't write to file");
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    
    public List<Store> getStores() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getStores'");
    }

    
    public List<Product> getProducts() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getProducts'");
    }

    
    public List<Client> getClients() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getClients'");
    }

    
    public List<Distributor> getDistributors() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getDistributors'");
    }

    
    public void printClient(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'printClient'");
    }

    
    public void printProduct(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'printProduct'");
    }

    
    public void printDistributor(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'printDistributor'");
    }

    
    public void printStore(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'printStore'");
    }

    
    public void printStorage(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'printStorage'");
    }
    
}
