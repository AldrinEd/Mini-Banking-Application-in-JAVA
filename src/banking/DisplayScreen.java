package banking;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;

public class DisplayScreen {
    private Connection connection;

    public DisplayScreen(Connection conn){
        this.connection = conn;
    }
    public void displayService(Account account){

        BankService bankService = new BankService(connection);
        Transaction transaction = new Transaction(connection);
        BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
        int ch;
        boolean login = true;

        int userId = account.getId();

        while(login){
            try {
                System.out.println("1. Check Account");
                System.out.println("2. Transfer Money");
                System.out.println("3. Logout");
                System.out.print("Select a number: ");
                ch = Integer.parseInt(sc.readLine());

                switch (ch) {
                    case 1:{
                        System.out.println("============================");
                        System.out.println("ID: " + userId);
                        System.out.println("Name: " + account.getName());
                        System.out.println("Balance: " + bankService.getBalance(userId));
                        System.out.println("============================");
                        break;
                    }
                    case 2:{
                        System.out.println("Enter receiver id: ");
                        int receiverId = Integer.parseInt(sc.readLine());
                        System.out.println("Enter Amount to transfer: ");
                        double amount = Double.parseDouble(sc.readLine());

                        if(transaction.transfer(userId, receiverId, amount)){
                            System.out.println("Transfer Complete!");
                        }
                        else{
                            System.out.println("Transfer Failed!");
                        }
                        break;
                    }
                    case 3:{
                        System.out.println("Logged out successfully! Returning to main menu.");
                        login = false;
                        break;
                    }

                    default:
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
    }
}
