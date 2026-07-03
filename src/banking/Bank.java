package banking;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;

public class Bank {
    public static void main(String[] args) {
        Connection conn = DBConnection.getConnection();
        BankService bankService = new BankService(conn);
        DisplayScreen displayScreen = new DisplayScreen(conn);

        BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
        String name, email;
        int pin, choice;
        double balance;

        while (true){
            System.out.println("============================================");
            System.out.println("Welcome to Banko Ni Aldrin");
            System.out.println();
            System.out.println("1. Login Account");
            System.out.println("2. Create Account");
            System.out.println("3. Exit");

            try {
                System.out.print("Select a number: ");
                choice = Integer.parseInt(sc.readLine());

                switch (choice) {
                    case 1:{

                        System.out.print("Enter your EMAIL: ");
                        email = sc.readLine();
                        System.out.print("Enter your Pin: ");
                        pin = Integer.parseInt(sc.readLine());

                        Account loggedInUser = bankService.loginAccount(email, pin);
                        
                        if(loggedInUser != null){
                            System.out.print("\033[H\033[2J");
                            System.out.flush(); 
                            System.out.println("Hello, " + loggedInUser.getName() + "! What would you like to do?");
                            displayScreen.displayService(loggedInUser);
                        }
                        break;
                    }
                    case 2:{
                        System.out.print("Enter your NAME: ");
                        name = sc.readLine();
                        System.out.print("Enter your EMAIL: ");
                        email = sc.readLine();
                        System.out.print("Enter your Pin: ");
                        pin = Integer.parseInt(sc.readLine());
                        System.out.print("Enter initial BALANCE: ");
                        balance = Double.parseDouble(sc.readLine());

                        if(bankService.createAccount(name, email, pin, balance)){
                            System.out.println("Account created successfully!");
                        }

                        break;
                    }
                    case 3:{
                        System.out.println("Thank you for using BNA!");
                        System.exit(0);
                        break;
                    }
                    default:
                        System.out.println("Invalid Input! Please try again.");
                        break;
                }


            } catch (Exception e) {
                System.out.println("Please enter a valid input!");
            }
        }
    }
}
