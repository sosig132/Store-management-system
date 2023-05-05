package view;

import java.util.Scanner;

public class Main {

    private static final Menu menu = MenuInMemory.getInstance();
    private static final MenuDB menuDB = MenuDB.getInstance();

    private static Scanner in = new Scanner(System.in);
    public static void main(String[] args){
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
                menu.start();
                return;
            }
            else if(input==2){
                menuDB.start();
                return;
            }
            else{
                System.out.println("Invalid input!");
            }

    
        }
    }
}
