package service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDB {
    private static Connection connection=null;
    private static String user = "admin";
    private static String password = "password";
    private static String url = "jdbc:mysql://localhost:3306/lant_magazine";
    private ConnectDB(){}

    public static Connection getInstance(){
        if (connection == null) {
            try {

                // Create the database connection
                connection = DriverManager.getConnection(url, user, password);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        
        return connection;
    }
}
