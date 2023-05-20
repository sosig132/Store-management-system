package service;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import models.Client;
import models.Clothing;
import models.Distributor;
import models.Food;
import models.Product;
import models.Sport;
import models.Store;
import models.Telephone;


public class ControllerDB implements Controller {

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
        
        Store store = new Store(name);
        
        try{

            Connection connection = ConnectDB.getInstance();
            String sqlInsert = "INSERT INTO store (name) VALUES (?) ";
            PreparedStatement statement = connection.prepareStatement(sqlInsert);
            statement.setString(1,store.getName());
            int rows = statement.executeUpdate();
            System.out.println(rows+" row affected");

            statement.close();
            ;
            try{
                write(myFile, "Inserted "+store.getName()+" into table stores");
        
            }
            catch(IOException e){
                System.out.println("Coulnt't write to file");
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        int store_id=0;
        try{
            Connection connection = ConnectDB.getInstance();
            String sqlSelect = "SELECT store_id FROM store ORDER BY store_id DESC LIMIT 1";
            PreparedStatement statement = connection.prepareStatement(sqlSelect);

            ResultSet rs = statement.executeQuery();
            while(rs.next())
                store_id = rs.getInt("store_id");

            statement.close();
            
            try{
                write(myFile, "Selected store_id from table store");   
            }
            catch(IOException e){
                System.out.println("Coulnt't write to file");
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        createStorage(store_id);
        
        
    }
    
    public void order(int store_id){}
    public void move(int store_id){}

    
    public void createStorage(int i) {
        try{

            Connection connection = ConnectDB.getInstance();
            String sqlInsert = "INSERT INTO storages(store_id) VALUES (?) ";
            PreparedStatement statement = connection.prepareStatement(sqlInsert);
            statement.setInt(1, i);
            int rows = statement.executeUpdate();
            System.out.println(rows+" row affected");

            statement.close();
            ;
            try{
                write(myFile, "Inserted storage into table storages");
        
            }
            catch(IOException e){
                System.out.println("Coulnt't write to file");
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    
    public void createProduct() {
        ProductFactory productFactory = new ProductFactory();
        Product product = productFactory.createProduct();

        System.out.println("Please input your product's name: ");
        //in.nextLine();
        product.setName(in.nextLine());


        System.out.println("Please input your product's brand: ");
        //in.nextLine();
        product.setBrand(in.nextLine());

        System.out.println("Please input your product's price: ");
        Boolean catch_error = true;
        
        do{
            if(in.hasNextInt()){   
                product.setCost(in.nextInt());
                in.nextLine();
                catch_error = false;
            }
            else{
                in.nextLine();
                System.out.println("Invalid input!");
            }
        }while(catch_error);

        try{
            Connection connection = ConnectDB.getInstance();
            String sqlInsert = "INSERT INTO product(name, brand, cost) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sqlInsert);
            statement.setString(1,product.getName());
            statement.setString(2,product.getBrand());
            statement.setInt(3, product.getCost());

            int rows = statement.executeUpdate();
            System.out.println(rows+" row affected");

            statement.close();
            ;
            try{
                write(myFile, "Inserted "+ product.getName() +" into table products");
        
            }
            catch(IOException e){
                System.out.println("Coulnt't write to file");
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        int product_id=0;
        try{
            Connection connection = ConnectDB.getInstance();
            String sqlSelect = "SELECT product_id FROM product ORDER BY product_id DESC LIMIT 1";
            PreparedStatement statement = connection.prepareStatement(sqlSelect);

            ResultSet rs = statement.executeQuery();
            while(rs.next())
                product_id = rs.getInt("product_id");

            statement.close();
            
            try{
                write(myFile, "Selected product_id from table product");   
            }
            catch(IOException e){
                System.out.println("Coulnt't write to file");
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        
        if (product instanceof Food){
            System.out.println("Please input how much protein your food has: ");
                catch_error = true;

                do {
                    if (in.hasNextInt()) {
                        ((Food)product).setProtein(in.nextInt());
                        //in.nextLine();
                        catch_error = false;
                    } else {
                        in.nextLine();
                        System.out.println("Invalid input!");
                    }
                } while (catch_error);
            try{
                Connection connection = ConnectDB.getInstance();
                String sqlInsert = "INSERT INTO food(protein, product_id) VALUES (?, ?) ";
                PreparedStatement statement = connection.prepareStatement(sqlInsert);
                statement.setInt(1,((Food)product).getProtein());
                statement.setInt(2,product_id);
    
                int rows = statement.executeUpdate();
                System.out.println(rows+" row affected");
    
                statement.close();
                ;
                try{
                    write(myFile, "Inserted "+ product.getName() +" into table food");               
                }
                catch(IOException e){
                    System.out.println("Coulnt't write to file");
                }
            }
            catch(SQLException e){
                e.printStackTrace();
            }
        }

        else if (product instanceof Clothing){
            System.out.println("Please input your clothing article's material: ");
            ((Clothing) product).setMaterial(in.nextLine());

            System.out.println("Please input your clothing article's size: ");
            ((Clothing) product).setSize(in.nextLine());
            try{
                Connection connection = ConnectDB.getInstance();
                String sqlInsert = "INSERT INTO clothing(material, size, product_id) VALUES (?, ?, ?) ";
                PreparedStatement statement = connection.prepareStatement(sqlInsert);
                statement.setString(1,((Clothing)product).getMaterial());
                statement.setString(2,((Clothing)product).getSize());
                statement.setInt(3, product_id);
                int rows = statement.executeUpdate();
                System.out.println(rows+" row affected");
    
                statement.close();
                
                try{
                    write(myFile, "Inserted "+ product.getName() +" into table clothing");               
                }
                catch(IOException e){
                    System.out.println("Coulnt't write to file");
                }
            }
            catch(SQLException e){
                e.printStackTrace();
            }
        }
        else if(product instanceof Sport){
            in.nextLine();
            System.out.println("Please input what sport your product is for: ");
            ((Sport) product).setSport(in.nextLine());
            try{
                Connection connection = ConnectDB.getInstance();
                String sqlInsert = "INSERT INTO sport(sport, product_id) VALUES (?, ?) ";
                PreparedStatement statement = connection.prepareStatement(sqlInsert);
                statement.setString(1,((Sport)product).getSport());
                statement.setInt(2, product_id);
                int rows = statement.executeUpdate();
                System.out.println(rows+" row affected");
    
                statement.close();
                ;
                try{
                    write(myFile, "Inserted "+ product.getName() +" into table sport");               
                }
                catch(IOException e){
                    System.out.println("Coulnt't write to file");
                }
            }
            catch(SQLException e){
                e.printStackTrace();
            }
        }
        else if(product instanceof Telephone){
            in.nextLine();
            System.out.println("Please input the phone's height: ");
            catch_error = true;

            do {
                if (in.hasNextInt()) {
                    ((Telephone) product).setHeight(in.nextInt());
                    in.nextLine();
                    catch_error = false;
                } else {
                    in.nextLine();
                    System.out.println("Invalid input!");
                }
            } while (catch_error);
            System.out.println("Please input the phone's width: ");
            catch_error = true;

            do {
                if (in.hasNextInt()) {
                    ((Telephone) product).setWidth(in.nextInt());
                    in.nextLine();
                    catch_error = false;
                } else {
                    in.nextLine();
                    System.out.println("Invalid input!");
                }
            } while (catch_error);
            try{
                Connection connection = ConnectDB.getInstance();
                String sqlInsert = "INSERT INTO telephone(height, witdh, product_id) VALUES (?, ?, ?) ";
                PreparedStatement statement = connection.prepareStatement(sqlInsert);
                statement.setInt(1,((Telephone)product).getHeight());
                statement.setInt(2,((Telephone)product).getWidth());
                statement.setInt(3, product_id);
                int rows = statement.executeUpdate();
                System.out.println(rows+" row affected");
    
                statement.close();
                ;
                try{
                    write(myFile, "Inserted "+ product.getName() +" into table telephone");               
                }
                catch(IOException e){
                    System.out.println("Coulnt't write to file");
                }
            }
            catch(SQLException e){
                e.printStackTrace();
            }
        }

        createDistributor(product.getBrand());
    
    }

    
    public void createDistributor(String name) {
        int check = 0;
        try{
            Connection connection = ConnectDB.getInstance();
            String sqlSelect = "SELECT * FROM distributor WHERE name = '" + name+"'";
            PreparedStatement statement = connection.prepareStatement(sqlSelect);

            ResultSet rs = statement.executeQuery();
            
            if (rs.next() == false){
                check = 1;
            }

            statement.close();
            
            try{
                write(myFile, "Selected product_id from table distributor");   
            }
            catch(IOException e){
                System.out.println("Coulnt't write to file");
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        if(check == 1){
            try{
                Connection connection = ConnectDB.getInstance();
                String sqlInsert = "INSERT INTO distributor(name) VALUES (?) ";
                PreparedStatement statement = connection.prepareStatement(sqlInsert);
                statement.setString(1, name);
                int rows = statement.executeUpdate();
                System.out.println(rows+" row affected");
    
                statement.close();
                ;
                try{
                    write(myFile, "Inserted "+ name +" into table distributor");               
                }
                catch(IOException e){
                    System.out.println("Coulnt't write to file");
                }
            }
            catch(SQLException e){
                e.printStackTrace();
            }
        }
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
        throw new UnsupportedOperationException("Unimplemented method 'getStores'");
    }

    
    public List<Product> getProducts() {
        throw new UnsupportedOperationException("Unimplemented method 'getProducts'");
    }

    
    public List<Client> getClients() {
        throw new UnsupportedOperationException("Unimplemented method 'getClients'");
    }

    
    public List<Distributor> getDistributors() {
        throw new UnsupportedOperationException("Unimplemented method 'getDistributors'");
    }

    public void printClients(){
        try{
            Connection connection = ConnectDB.getInstance();
            String sqlInsert = "select * from clients";
            PreparedStatement statement = connection.prepareStatement(sqlInsert);
            ResultSet rs = statement.executeQuery();

            while(rs.next()){
                int id = rs.getInt("client_id");
                int money = rs.getInt("money");
                System.out.println("ID: " + id + ", Money: "+money);
            }
            rs.close();
            statement.close();
       }catch(SQLException e){
        e.printStackTrace();
       }
    }
    public void printClient(int id) {
        throw new UnsupportedOperationException("Unimplemented method 'printClient'");
    }

    
    public void printProduct(int id) {
        throw new UnsupportedOperationException("Unimplemented method 'printProduct'");
    }

    
    public void printDistributor(int id) {
        throw new UnsupportedOperationException("Unimplemented method 'printDistributor'");
    }

    
    public void printStore(int id) {
        throw new UnsupportedOperationException("Unimplemented method 'printStore'");
    }

    
    public void printStorage(int id) {
        throw new UnsupportedOperationException("Unimplemented method 'printStorage'");
    }
    
}
