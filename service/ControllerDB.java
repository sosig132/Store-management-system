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

    public void putItemsInCart(int client_id, int store_id){
        System.out.println("What product do you want to buy?");
        printStore(store_id);
        Boolean catch_error = true;
        int input = 0;
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
        }while(catch_error);
        if(checkProductInStore(input,store_id)==false){
            System.out.println("There is no product in the store with that id");
            return;
        }
        System.out.println("How many do you want to buy?");
        int quantity = 0;
        do{
            if(in.hasNextInt()){   
                quantity = in.nextInt();
                in.nextLine();
                if(quantity>=0){
                    catch_error = false;
                    
                }   
                else{
                    in.nextLine();
                    System.out.println("Invalid input!");
                }             
            }
            else{
                in.nextLine();
                System.out.println("Invalid input!");
            }
        }while(catch_error);
        int quant = 0;
        try{
            Connection connection = ConnectDB.getInstance();
            String sqlStatement = "Select quantity FROM available_products where store_id = ? and product_id = ?";
            PreparedStatement statement = connection.prepareStatement(sqlStatement);
            statement.setInt(1, store_id);
            statement.setInt(2, input);
            ResultSet rs = statement.executeQuery();
            
            if(rs.next()){
                quant = rs.getInt("quantity");
            }

            statement.close();
        }
        catch(SQLException e){
            e.printStackTrace();
        }

        int quantityToMove = Math.min(quant, quantity);

        int client_money = 0;

        try{
            Connection connection = ConnectDB.getInstance();
            String sqlStatement = "Select money FROM clients where client_id = ?";
            PreparedStatement statement = connection.prepareStatement(sqlStatement);
            statement.setInt(1, client_id);
            ResultSet rs = statement.executeQuery();
            
            if(rs.next()){
                client_money = rs.getInt("money");
            }

            statement.close();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        int cost = 0;
        try{
            Connection connection = ConnectDB.getInstance();
            String sqlStatement = "Select cost FROM product where product_id = ?";
            PreparedStatement statement = connection.prepareStatement(sqlStatement);
            statement.setInt(1, input);
            ResultSet rs = statement.executeQuery();
            
            if(rs.next()){
                cost = rs.getInt("cost");
            }

            statement.close();
        }
        catch(SQLException e){
            e.printStackTrace();
        }

        if (client_money < quantityToMove*cost){
            System.out.println("You wanted to steal huh? Banned");
            
            try{
                Connection connection = ConnectDB.getInstance();
                String sqlStatement = "Insert into banned_clients(client_id, store_id) values(?, ?)";
                PreparedStatement statement = connection.prepareStatement(sqlStatement);
                statement.setInt(1, client_id);
                statement.setInt(2, store_id);
                statement.executeUpdate();
    
                statement.close();
            }
            catch(SQLException e){
                e.printStackTrace();
            }
            return;
        }
        try{
            Connection connection = ConnectDB.getInstance();
            String sqlStatement = "Update clients set money = money - ? where client_id = ?";
            PreparedStatement statement = connection.prepareStatement(sqlStatement);
            statement.setInt(1, quantityToMove*cost);
            statement.setInt(2, client_id);

            statement.executeUpdate();

            statement.close();
        }
        catch(SQLException e){
            e.printStackTrace();
        }

        try{
            Connection connection = ConnectDB.getInstance();
            String sqlStatement = "Update available_products set quantity = quantity - ? where store_id = ? and product_id = ?";
            PreparedStatement statement = connection.prepareStatement(sqlStatement);
            statement.setInt(1, quantityToMove);
            statement.setInt(2, store_id);
            statement.setInt(3, input);
            statement.executeUpdate();

            statement.close();
        }
        catch(SQLException e){
            e.printStackTrace();
        }

        int checkk = 0;
        try{
            Connection connection = ConnectDB.getInstance();
            String sqlStatement = "Select quantity FROM shopping_cart where client_id = ? and product_id = ?";
            PreparedStatement statement = connection.prepareStatement(sqlStatement);
            statement.setInt(1, client_id);
            statement.setInt(2, input);
            ResultSet rs = statement.executeQuery();

            if(rs.next()){
                checkk=1;
            }

            statement.close();
        }
        catch(SQLException e){
            e.printStackTrace();
        }

       
        if (checkk==1){
            try{
                Connection connection = ConnectDB.getInstance();
                String sqlStatement = "Update shopping_cart set quantity = quantity + ? where client_id = ? and product_id = ?";
                PreparedStatement statement = connection.prepareStatement(sqlStatement);
                statement.setInt(1, quantityToMove);
                statement.setInt(2, client_id);
                statement.setInt(3, input);
                statement.executeUpdate();
                statement.close();
            }
            catch(SQLException e){
                e.printStackTrace();
            }
        }
        else{
            try{
                Connection connection = ConnectDB.getInstance();
                String sqlStatement = "Insert into shopping_cart(quantity, client_id, product_id) values (?, ?, ?)";
                PreparedStatement statement = connection.prepareStatement(sqlStatement);
                statement.setInt(1, quantityToMove);
                statement.setInt(2, client_id);
                statement.setInt(3, input);
                statement.executeUpdate();
                statement.close();
            }
            catch(SQLException e){
                e.printStackTrace();
            }
        }
    }


    @Override
    public void createStore() {
        System.out.println("Enter the store's name: ");
        String name = in.nextLine();
        
        Store store = new Store(name);
        
        try{

            Connection connection = ConnectDB.getInstance();
            String sqlStatement = "INSERT INTO store (name) VALUES (?) ";
            PreparedStatement statement = connection.prepareStatement(sqlStatement);
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
            String sqlStatement = "SELECT store_id FROM store ORDER BY store_id DESC LIMIT 1";
            PreparedStatement statement = connection.prepareStatement(sqlStatement);

            ResultSet rs = statement.executeQuery();
            while(rs.next())
                store_id = rs.getInt("store_id");

            statement.close();
            
            try{
                write(myFile, "Selected " + store_id + " from table store");   
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
    
    public void order(int store_id){
        System.out.println("What do you want to order?");
        printProducts();
        Boolean catch_error = true;
        int input = 0;
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
        }while(catch_error);
        if(checkProduct(input)==false){
            System.out.println("There is no product with that id");
            return;
        }
        System.out.println("How many do you want to order?");
        int quantity = 0;
        do{
            if(in.hasNextInt()){   
                quantity = in.nextInt();
                in.nextLine();
                if(quantity>=0){
                    catch_error = false;
                    
                }   
                else{
                    in.nextLine();
                    System.out.println("Invalid input!");
                }             
            }
            else{
                in.nextLine();
                System.out.println("Invalid input!");
            }
        }while(catch_error);
        System.out.println(quantity +" "+ input+ " " +store_id);
        
        int check = 0;
        try{
            Connection connection = ConnectDB.getInstance();
            String sqlStatement = "Select * FROM stored_products where storage_id = ? and product_id = ?";
            PreparedStatement statement = connection.prepareStatement(sqlStatement);
            statement.setInt(1, store_id);
            statement.setInt(2, input);
            ResultSet rs = statement.executeQuery();
            
            if(rs.next()){
                check = 1;
            }

            statement.close();
        }
        catch(SQLException e){
            e.printStackTrace();
        }

        if(check == 0){
            try{
                Connection connection = ConnectDB.getInstance();
                String sqlStatement = "INSERT INTO stored_products(quantity, product_id, storage_id) VALUES (?, ?, ?) ";
                PreparedStatement statement = connection.prepareStatement(sqlStatement);
                statement.setInt(1, quantity);
                statement.setInt(2, input);
                statement.setInt(3, store_id);
                int rows = statement.executeUpdate();
                System.out.println(rows+" row affected");
                statement.close();
            }
            catch(SQLException e){
                e.printStackTrace();
            }
        }
        else{
            try{
                Connection connection = ConnectDB.getInstance();
                String sqlStatement = "UPDATE stored_products SET quantity = quantity + ? WHERE product_id = ? and storage_id = ?";
                PreparedStatement statement = connection.prepareStatement(sqlStatement);
                statement.setInt(1, quantity);
                statement.setInt(2, input);
                statement.setInt(3, store_id);
                int rows = statement.executeUpdate();
                System.out.println(rows+" row affected");
                statement.close();
            }
            catch(SQLException e){
                e.printStackTrace();
            }
        }
    }
    public void move(int store_id){
        System.out.println("What product do you want to move?");
        printStorage(store_id);
        Boolean catch_error = true;
        int input = 0;
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
        }while(catch_error);
        if(checkProductInStorage(input,store_id)==false){
            System.out.println("You don't have that product in storage");
            return;
        }
        System.out.println("How many do you want to move?");
        int quantity = 0;
        do{
            if(in.hasNextInt()){   
                quantity = in.nextInt();
                in.nextLine();
                if(quantity>=0){
                    catch_error = false;
                    
                }   
                else{
                    in.nextLine();
                    System.out.println("Invalid input!");
                }             
            }
            else{
                in.nextLine();
                System.out.println("Invalid input!");
            }
        }while(catch_error);
        int quant = 0;
        try{
            Connection connection = ConnectDB.getInstance();
            String sqlStatement = "Select quantity FROM stored_products where storage_id = ? and product_id = ?";
            PreparedStatement statement = connection.prepareStatement(sqlStatement);
            statement.setInt(1, store_id);
            statement.setInt(2, input);
            ResultSet rs = statement.executeQuery();
            
            if(rs.next()){
                quant = rs.getInt("quantity");
            }

            statement.close();
        }
        catch(SQLException e){
            e.printStackTrace();
        }

        int quantityToMove = Math.min(quant, quantity);
        
        try{
            Connection connection = ConnectDB.getInstance();
            String sqlStatement = "Update stored_products set quantity = quantity - ? where storage_id = ? and product_id = ?";
            PreparedStatement statement = connection.prepareStatement(sqlStatement);
            statement.setInt(1, quantityToMove);
            statement.setInt(2, store_id);
            statement.setInt(3, input);
            statement.executeUpdate();

            statement.close();
        }
        catch(SQLException e){
            e.printStackTrace();
        }

        int checkk = 0;
        try{
            Connection connection = ConnectDB.getInstance();
            String sqlStatement = "Select quantity FROM available_products where store_id = ? and product_id = ?";
            PreparedStatement statement = connection.prepareStatement(sqlStatement);
            statement.setInt(1, store_id);
            statement.setInt(2, input);
            ResultSet rs = statement.executeQuery();

            if(rs.next()){
                checkk=1;
            }

            statement.close();
        }
        catch(SQLException e){
            e.printStackTrace();
        }

       
        if (checkk==1){
            try{
                Connection connection = ConnectDB.getInstance();
                String sqlStatement = "Update available_products set quantity = quantity + ? where store_id = ? and product_id = ?";
                PreparedStatement statement = connection.prepareStatement(sqlStatement);
                statement.setInt(1, quantityToMove);
                statement.setInt(2, store_id);
                statement.setInt(3, input);
                statement.executeUpdate();
                statement.close();
            }
            catch(SQLException e){
                e.printStackTrace();
            }
        }
        else{
            try{
                Connection connection = ConnectDB.getInstance();
                String sqlStatement = "Insert into available_products(quantity, store_id, product_id) values (?, ?, ?)";
                PreparedStatement statement = connection.prepareStatement(sqlStatement);
                statement.setInt(1, quantityToMove);
                statement.setInt(2, store_id);
                statement.setInt(3, input);
                statement.executeUpdate();
                statement.close();
            }
            catch(SQLException e){
                e.printStackTrace();
            }
        }
    }

    
    public void createStorage(int i) {
        try{

            Connection connection = ConnectDB.getInstance();
            String sqlStatement = "INSERT INTO storages(store_id) VALUES (?) ";
            PreparedStatement statement = connection.prepareStatement(sqlStatement);
            statement.setInt(1, i);
            int rows = statement.executeUpdate();
            System.out.println(rows+" row affected");

            statement.close();
            
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
            String sqlStatement = "INSERT INTO product(name, brand, cost) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sqlStatement);
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
            String sqlStatement = "SELECT product_id FROM product ORDER BY product_id DESC LIMIT 1";
            PreparedStatement statement = connection.prepareStatement(sqlStatement);

            ResultSet rs = statement.executeQuery();
            while(rs.next())
                product_id = rs.getInt("product_id");

            statement.close();
            
            try{
                write(myFile, "Selected "+  product_id + " from table product");   
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
                        in.nextLine();
                        catch_error = false;
                    } else {
                        in.nextLine();
                        System.out.println("Invalid input!");
                    }
                } while (catch_error);
            try{
                Connection connection = ConnectDB.getInstance();
                String sqlStatement = "INSERT INTO food(protein, product_id) VALUES (?, ?) ";
                PreparedStatement statement = connection.prepareStatement(sqlStatement);
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
                String sqlStatement = "INSERT INTO clothing(material, size, product_id) VALUES (?, ?, ?) ";
                PreparedStatement statement = connection.prepareStatement(sqlStatement);
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
                String sqlStatement = "INSERT INTO sport(sport, product_id) VALUES (?, ?) ";
                PreparedStatement statement = connection.prepareStatement(sqlStatement);
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
                String sqlStatement = "INSERT INTO telephone(height, witdh, product_id) VALUES (?, ?, ?) ";
                PreparedStatement statement = connection.prepareStatement(sqlStatement);
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
            String sqlStatement = "SELECT * FROM distributor WHERE name = '" + name+"'";
            PreparedStatement statement = connection.prepareStatement(sqlStatement);

            ResultSet rs = statement.executeQuery();
            
            if (rs.next() == false){
                check = 1;
            }

            statement.close();
            
            try{
                write(myFile, "Selected "+ name + " from table distributor");   
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
                String sqlStatement = "INSERT INTO distributor(name) VALUES (?) ";
                PreparedStatement statement = connection.prepareStatement(sqlStatement);
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
            String sqlStatement = "INSERT INTO clients (money) VALUES (?) ";
            PreparedStatement statement = connection.prepareStatement(sqlStatement);
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
            String sqlStatement = "select * from clients";
            PreparedStatement statement = connection.prepareStatement(sqlStatement);
            ResultSet rs = statement.executeQuery();

            while(rs.next()){
                int id = rs.getInt("client_id");
                int money = rs.getInt("money");
                System.out.println("ID: " + id + ", Money: "+money);
                try{
                    Connection connectio = ConnectDB.getInstance();
                    String sqlStatemen = "select quantity, product_id from shopping_cart where client_id = ?";
                    PreparedStatement statemen = connectio.prepareStatement(sqlStatemen);
                    statemen.setInt(1, id);
                    ResultSet r = statemen.executeQuery();

                    while(r.next()){
                        int product_id = r.getInt("product_id");
                        int quantity = r.getInt("quantity");
                        System.out.println("       Product ID: "+product_id + ", Quantity: "+quantity);
                    }
                }
                catch(SQLException e){
                    e.printStackTrace();
                }
            }
            rs.close();
            statement.close();
            try{
                write(myFile, "Printed clients");
        
            }
            catch(IOException e){
                System.out.println("Coulnt't write to file");
            }
       }catch(SQLException e){
        e.printStackTrace();
       }
    }
    public void printStores(){
        try{
            Connection connection = ConnectDB.getInstance();
            String sqlStatement = "select * from store";
            PreparedStatement statement = connection.prepareStatement(sqlStatement);
            ResultSet rs = statement.executeQuery();
            int id=0;
            String name="";
            while(rs.next()){
                id = rs.getInt("store_id");
                name = rs.getString("name");
                System.out.println("ID: " + id + ", Name: "+name);
                
                try{
                    Connection connectio = ConnectDB.getInstance();
                    String sqlStatemen = "select quantity, product_id from available_products where store_id = ?";
                    PreparedStatement statemen = connectio.prepareStatement(sqlStatemen);
                    statemen.setInt(1, id);
                    ResultSet r = statemen.executeQuery();
                    System.out.println("       Available products:");

                    while(r.next()){
                        int product_id = r.getInt("product_id");
                        int quantity = r.getInt("quantity");
                        System.out.println("       Product ID: "+product_id + ", Quantity: "+quantity);
                    }

                }
                catch(SQLException e){
                    e.printStackTrace();
                }
                try{
                    Connection connectio = ConnectDB.getInstance();
                    String sqlStatemen = "select quantity, product_id from stored_products where storage_id = ? and quantity>0";
                    PreparedStatement statemen = connectio.prepareStatement(sqlStatemen);
                    statemen.setInt(1, id);
                    ResultSet r = statemen.executeQuery();
                    System.out.println("       Stored products:");
                    while(r.next()){
                        int product_id = r.getInt("product_id");
                        int quantity = r.getInt("quantity");
                        System.out.println("       Product ID: "+product_id + ", Quantity: "+quantity);
                    }

                }
                catch(SQLException e){
                    e.printStackTrace();
                }
            }
            rs.close();
            statement.close();
            try{
                write(myFile, "Printed stores");
        
            }
            catch(IOException e){
                System.out.println("Coulnt't write to file");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }    
    }

    public void printStore(int store_id){
        try{
            Connection connection = ConnectDB.getInstance();
            String sqlStatement = "select * from store where store_id = ?";
            PreparedStatement statement = connection.prepareStatement(sqlStatement);
            statement.setInt(1, store_id);
            ResultSet rs = statement.executeQuery();
            int id=0;
            String name="";
            System.out.println(store_id);
            while(rs.next()){
                id = rs.getInt("store_id");
                name = rs.getString("name");
                System.out.println("ID: " + id + ", Name: "+name);
                
                try{
                    Connection connectio = ConnectDB.getInstance();
                    String sqlStatemen = "select quantity, product_id from available_products where store_id = ?";
                    PreparedStatement statemen = connectio.prepareStatement(sqlStatemen);
                    statemen.setInt(1, id);
                    ResultSet r = statemen.executeQuery();

                    while(r.next()){
                        int product_id = r.getInt("product_id");
                        int quantity = r.getInt("quantity");
                        System.out.println("       Product ID: "+product_id + ", Quantity: "+quantity);
                    }

                }
                catch(SQLException e){
                    e.printStackTrace();
                }
            }
            rs.close();
            statement.close();
            try{
                write(myFile, "Printed store");
        
            }
            catch(IOException e){
                System.out.println("Coulnt't write to file");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }    
    }
    

    public void printDistributors(){
        try{
            Connection connection = ConnectDB.getInstance();
            String sqlStatement = "select * from distributors";
            PreparedStatement statement = connection.prepareStatement(sqlStatement);
            ResultSet rs = statement.executeQuery();

            while(rs.next()){
                int id = rs.getInt("distributor_id");
                String name = rs.getString("name");
                System.out.println("ID: " + id + ", Name: "+ name);
            }
            rs.close();
            statement.close();
            try{
                write(myFile, "Printed clients");
        
            }
            catch(IOException e){
                System.out.println("Coulnt't write to file");
            }
       }catch(SQLException e){
        e.printStackTrace();
       }
    }

    public void printProducts(){
        try{
            Connection connection = ConnectDB.getInstance();
            String sqlStatement = "SELECT product.product_id, name, brand, cost, "+
            "food.product_id is not null as isFood, "+
            "protein, "+
            "clothing.product_id is not null as isClothing, "+
            "size, "+
            "material, "+
            "telephone.product_id is not null as isTelephone, "+
            "height, "+
            "width, "+
            "sport.product_id is not null as isSport, "+
            "sport.sport "+
          
          "FROM product "+
            "LEFT JOIN food on product.product_id = food.product_id "+
            "LEFT JOIN clothing on product.product_id = clothing.product_id "+
            "LEFT JOIN sport on product.product_id = sport.product_id "+
            "LEFT JOIN telephone on product.product_id = telephone.product_id";
            PreparedStatement statement = connection.prepareStatement(sqlStatement);
            ResultSet rs = statement.executeQuery();

            while(rs.next()){
                int id = rs.getInt("product_id");
                String name = rs.getString("name");
                String brand = rs.getString("brand");
                int cost = rs.getInt("cost");
                
                if (rs.getInt("isFood")==1){
                    int protein = rs.getInt("protein");
                    System.out.println("Type: Food");
                    System.out.println("ID: " + id + ", Name: "+ name + ", Brand: " + brand + ", Cost: " + cost + ", Protein: " + protein);
                }
                else if (rs.getInt("isClothing")==1){
                    String material = rs.getString("material");
                    String size = rs.getString("size");
                    System.out.println("Type: Clothing");
                    System.out.println("ID: " + id + ", Name: "+ name + ", Brand: " + brand + ", Cost: " + cost + ", Material: " + material + ", Size: " + size);
                }
                else if (rs.getInt("isTelephone")==1){
                    String height = rs.getString("height");
                    String width = rs.getString("width");
                    System.out.println("Type: Telephone");
                    System.out.println("ID: " + id + ", Name: "+ name + ", Brand: " + brand + ", Cost: " + cost + ", Height: " + height + ", Width: " + width);
                }
                else if (rs.getInt("isSport")==1){
                    String sport = rs.getString("sport");
                    System.out.println("Type: Sporting good");
                    System.out.println("ID: " + id + ", Name: "+ name + ", Brand: " + brand + ", Cost: " + cost +  ", Sport: "  + sport );
                }
            }
            rs.close();
            statement.close();
            try{
                write(myFile, "Printed clients");
        
            }
            catch(IOException e){
                System.out.println("Coulnt't write to file");
            }
       }catch(SQLException e){
        e.printStackTrace();
       }
    }
    
    public void deleteStore(){
        System.out.println("Input the id of the store to delete: ");
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
        int checkIfExists = 0;
        try{
            Connection connection = ConnectDB.getInstance();
            String sqlStatement = "select * from store where store_id = ?";
            
            PreparedStatement statement = connection.prepareStatement(sqlStatement);
            statement.setInt(1, input);
            ResultSet rs = statement.executeQuery();
            if (rs.next()){
                checkIfExists=1;
            }
            
       }
        catch(SQLException e){
            e.printStackTrace();
        }
       if(checkIfExists==0){
            System.out.println("There is no store with that id");
            return;
       }
       try{
            Connection connection = ConnectDB.getInstance();
            String sqlStatement = "delete from store where store_id = ?";

            PreparedStatement statement = connection.prepareStatement(sqlStatement);
            statement.setInt(1, input);
            statement.execute();
            try{
                write(myFile, "Deleted store with id" + input);
        
            }
            catch(IOException e){
                System.out.println("Coulnt't write to file");
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
    public void deleteClient(){
        System.out.println("Input the id of the client to delete: ");
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
        int checkIfExists = 0;
        try{
            Connection connection = ConnectDB.getInstance();
            String sqlStatement = "select * from clients where client_id = ?";
            
            PreparedStatement statement = connection.prepareStatement(sqlStatement);
            statement.setInt(1, input);
            ResultSet rs = statement.executeQuery();
            if (rs.next()){
                checkIfExists=1;
            }
            
       }
        catch(SQLException e){
            e.printStackTrace();
        }
       if(checkIfExists==0){
            System.out.println("There is no client with that id");
            return;
       }
       try{
            Connection connection = ConnectDB.getInstance();
            String sqlStatement = "delete from clients where client_id = ?";

            PreparedStatement statement = connection.prepareStatement(sqlStatement);
            statement.setInt(1, input);
            statement.execute();
            try{
                write(myFile, "Deleted client with id" + input);
        
            }
            catch(IOException e){
                System.out.println("Coulnt't write to file");
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
    public void deleteDistributor(){
        System.out.println("Input the id of the distributor to delete: ");
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
        int checkIfExists = 0;
        String name="";
        try{
            Connection connection = ConnectDB.getInstance();
            String sqlStatement = "select * from distributor where distributor_id = ?";
            
            PreparedStatement statement = connection.prepareStatement(sqlStatement);
            statement.setInt(1, input);
            ResultSet rs = statement.executeQuery();
            if (rs.next()){
                checkIfExists=1;
                name = rs.getString("name");
            }
            
       }
        catch(SQLException e){
            e.printStackTrace();
        }
       if(checkIfExists==0){
            System.out.println("There is no distributor with that id");
            return;
       }
       try{
            Connection connection = ConnectDB.getInstance();
            String sqlStatement = "delete from distributor where distributor_id = ?";

            PreparedStatement statement = connection.prepareStatement(sqlStatement);
            statement.setInt(1, input);
            statement.execute();
            try{
                write(myFile, "Deleted distributor with id" + input);
        
            }
            catch(IOException e){
                System.out.println("Coulnt't write to file");
            }
            deleteProducts(name);
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
    public void deleteProducts(String brand){
        try{
            Connection connection = ConnectDB.getInstance();
            String sqlStatement = "delete from product where brand = ?";

            PreparedStatement statement = connection.prepareStatement(sqlStatement);
            statement.setString(1, brand);
            statement.execute();
            
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
    public void deleteProduct(){
        System.out.println("Input the id of the product to delete: ");
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
        int checkIfExists = 0;
        try{
            Connection connection = ConnectDB.getInstance();
            String sqlStatement = "select * from product where product_id = ?";
            
            PreparedStatement statement = connection.prepareStatement(sqlStatement);
            statement.setInt(1, input);
            ResultSet rs = statement.executeQuery();
            if (rs.next()){
                checkIfExists=1;
            }
            
       }
        catch(SQLException e){
            e.printStackTrace();
        }
       if(checkIfExists==0){
            System.out.println("There is no product with that id");
            return;
       }
       try{
            Connection connection = ConnectDB.getInstance();
            String sqlStatement = "delete from product where product_id = ?";

            PreparedStatement statement = connection.prepareStatement(sqlStatement);
            statement.setInt(1, input);
            statement.execute();
            try{
                write(myFile, "Deleted product with id" + input);
        
            }
            catch(IOException e){
                System.out.println("Coulnt't write to file");
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
    public boolean checkStores(){
        try{
            Connection connection = ConnectDB.getInstance();
            String sqlStatement = "select * from store";
            PreparedStatement statement = connection.prepareStatement(sqlStatement);
            ResultSet rs = statement.executeQuery();

            return rs.next();
       }catch(SQLException e){
        e.printStackTrace();
       }
        return false;
    }

    public boolean checkClients(){
        try{
            Connection connection = ConnectDB.getInstance();
            String sqlStatement = "select * from clients";
            PreparedStatement statement = connection.prepareStatement(sqlStatement);
            ResultSet rs = statement.executeQuery();

            return rs.next();
       }catch(SQLException e){
        e.printStackTrace();
       }
        return false;
    }
    public boolean checkProducts(){
        try{
            Connection connection = ConnectDB.getInstance();
            String sqlStatement = "select * from product";
            PreparedStatement statement = connection.prepareStatement(sqlStatement);
            ResultSet rs = statement.executeQuery();

            return rs.next();
       }catch(SQLException e){
            e.printStackTrace();
       }
        return false;
    }

    public boolean checkIfClientBanned(int id, int storeId){
        try{
            Connection connection = ConnectDB.getInstance();
            String sqlStatement = "select * from banned_clients Where store_id = ? and client_id = ?";
            PreparedStatement statement = connection.prepareStatement(sqlStatement);
            statement.setInt(1, storeId);
            statement.setInt(2, id);
            ResultSet rs = statement.executeQuery();

            return rs.next();
       }catch(SQLException e){
            e.printStackTrace();
       }
        return false;
    }

    public int storesSize(){
        try{
            Connection connection = ConnectDB.getInstance();
            String sqlStatement = "select * from store order by store_id desc limit 1";
            PreparedStatement statement = connection.prepareStatement(sqlStatement);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                return rs.getInt("store_id");
            }
       }catch(SQLException e){
            e.printStackTrace();
       }
        return 0;
    }

    public void updateStore(){
        System.out.println("Which store's name do you want to change(input id)");
        int input = 0;
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

        int checkIfValid = 0;

        try{
            Connection connection = ConnectDB.getInstance();
            String sqlStatement = "select * from store where store_id = ?";
            PreparedStatement statement = connection.prepareStatement(sqlStatement);
            statement.setInt(1,input);
            ResultSet rs = statement.executeQuery();
            if(rs.next()==true){
                checkIfValid=1;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        if (checkIfValid==0){
            System.out.println("There is no store with that id");
            return;
        }

        System.out.println("What do you want the new name to be?: ");
        String name = in.nextLine();

        try{
            Connection connection = ConnectDB.getInstance();
            String sqlStatement = "update stores set name = ? where store_id = ?";
            PreparedStatement statement = connection.prepareStatement(sqlStatement);
            statement.setString(1,name);
            statement.setInt(2, input);
            int rows = statement.executeUpdate();
            System.out.println(rows + " rows affected");
            try{
                write(myFile, "Updated store with id " + input);
        
            }
            catch(IOException e){
                System.out.println("Coulnt't write to file");
            }

        }catch(SQLException e){
            e.printStackTrace();
        }
       
    }

    public void updateClient(){
        System.out.println("Which client's money do you want to change(input id)");
        int input = 0;
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

        int checkIfValid = 0;

        try{
            Connection connection = ConnectDB.getInstance();
            String sqlStatement = "select * from clients where client_id = ?";
            PreparedStatement statement = connection.prepareStatement(sqlStatement);
            statement.setInt(1,input);
            ResultSet rs = statement.executeQuery();
            if(rs.next()==true){
                checkIfValid=1;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        if (checkIfValid==0){
            System.out.println("There is no client with that id");
            return;
        }

        System.out.println("What do you want the new value to be?: ");
        int value = 0;
        do{
            if(in.hasNextInt()){   
                value = in.nextInt();
                in.nextLine();
                catch_error = false;
            }
            else{
                in.nextLine();
                System.out.println("Invalid input!");
            }
        }while(catch_error==true);
        try{
            Connection connection = ConnectDB.getInstance();
            String sqlStatement = "update clients set money = ? where client_id = ?";
            PreparedStatement statement = connection.prepareStatement(sqlStatement);
            statement.setInt(1,value);
            statement.setInt(2, input);
            int rows = statement.executeUpdate();
            System.out.println(rows + " rows affected");
            try{
                write(myFile, "Updated client with id " + input);
        
            }
            catch(IOException e){
                System.out.println("Coulnt't write to file");
            }

        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    public boolean checkStore(int id){
        try{
            Connection connection = ConnectDB.getInstance();
            String sqlStatement = "select * from store where store_id = ?";
            PreparedStatement statement = connection.prepareStatement(sqlStatement);
            statement.setInt(1,id);
            ResultSet rs = statement.executeQuery();

            return rs.next();
       }catch(SQLException e){
        e.printStackTrace();
       }
        return false;
    }
    public boolean checkProduct(int id){
        try{
            Connection connection = ConnectDB.getInstance();
            String sqlStatement = "select * from product where product_id = ?";
            PreparedStatement statement = connection.prepareStatement(sqlStatement);
            statement.setInt(1,id);
            ResultSet rs = statement.executeQuery();

            return rs.next();
       }catch(SQLException e){
        e.printStackTrace();
       }
        return false;
    }
    public void printStorage(int storageId){
        try{
            Connection connection = ConnectDB.getInstance();
            String sqlStatement = "select * from stored_products WHERE storage_id = ?";
            PreparedStatement statement = connection.prepareStatement(sqlStatement);
            statement.setInt(1, storageId);
            ResultSet rs = statement.executeQuery();
            int c = 0;
            while(rs.next()){
                if (c == 0){
                    System.out.println("Items in storage:");
                    c=1;
                }
                int product = rs.getInt("product_id");
                int quant = rs.getInt("quantity");
                System.out.println( "Product ID: "+ product + ", Quantity: " + quant);
            }
            rs.close();
            statement.close();
            try{
                write(myFile, "Printed clients");
        
            }
            catch(IOException e){
                System.out.println("Coulnt't write to file");
            }
       }catch(SQLException e){
        e.printStackTrace();
       }
    }
    public boolean checkProductInStorage(int id, int store_id){
        try{
            Connection connection = ConnectDB.getInstance();
            String sqlStatement = "select * from stored_products where product_id = ? and storage_id = ? ";
            PreparedStatement statement = connection.prepareStatement(sqlStatement);
            statement.setInt(1,id);
            statement.setInt(2, store_id);
            ResultSet rs = statement.executeQuery();

            if (rs.next()){
                if(rs.getInt("quantity")>0){
                    return true;
                }
            }
            return false;
       }catch(SQLException e){
        e.printStackTrace();
       }
        return false;
    }
    public boolean checkProductInStore(int id, int store_id){
        try{
            Connection connection = ConnectDB.getInstance();
            String sqlStatement = "select * from available_products where product_id = ? and store_id = ? ";
            PreparedStatement statement = connection.prepareStatement(sqlStatement);
            statement.setInt(1,id);
            statement.setInt(2, store_id);
            ResultSet rs = statement.executeQuery();

            if (rs.next()){
                if(rs.getInt("quantity")>0){
                    return true;
                }
            }
            return false;
       }catch(SQLException e){
        e.printStackTrace();
       }
        return false;
    }
}
