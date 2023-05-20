package service;

import java.util.Scanner;

import models.Food;
import models.Clothing;
import models.Sport;
import models.Telephone;
import models.Product;


public class ProductFactory {
    private Scanner in = new Scanner(System.in);
    
    public Product createProduct(){
        System.out.println("What type of product do you want to create?");

        System.out.println("1.Food");
        System.out.println("2.Clothing");
        System.out.println("3.Sporting goods");
        System.out.println("4.Telephone");
        
        while(true){
            int input = 0;
            if(in.hasNextInt()){   
                input = in.nextInt();
                in.nextLine();
                
            }
            else{
                in.nextLine();
                System.out.println("Invalid input!");
            }

            if (input == 1){
                return new Food();
            }
            else if(input==2){
                
                return new Clothing();
            }
            else if(input==3){
                return new Sport();
            }
            else if(input==4){
                return new Telephone();
            }
            else{
                System.out.println("Invalid input!");
            }

    
        }
    }
}
