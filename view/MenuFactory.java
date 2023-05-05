package view;

import java.util.Scanner;

public class MenuFactory {

    private Scanner in = new Scanner(System.in);
    //private static Menu menu;
    public Menu createMenu(){
        System.out.println("What version do you want to use?");
        System.out.println("1. Local version");
        System.out.println("2. Database version");
        
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
                return new MenuInMemory();
            }
            else if(input==2){
                
                return new MenuDB();
            }
            else{
                System.out.println("Invalid input!");
            }

    
        }
    }
}
