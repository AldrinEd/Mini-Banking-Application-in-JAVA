package banking;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLIntegrityConstraintViolationException;

public class BankService {
    private Connection connection;

    public BankService(Connection conn){
        this.connection = conn;
    }

    public boolean createAccount(String name, String email, int pin, double balance){

        try {
            String sql = "INSERT INTO user(full_name, email, pin, balance) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setInt(3, pin);
            ps.setDouble(4, balance);
            int rows = ps.executeUpdate();

            if(rows > 0 ){
                System.out.println("Account created successfully! You can now login.");
                return true;
            }

        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Username already exists! Try another one.");
            return false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public Account loginAccount(String email, int pin){
        try {

            String sql = "SELECT * FROM user WHERE email = ? AND pin = ? ";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, email);
            ps.setInt(2, pin);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                Account acc = new Account();
                acc.setEmail(rs.getString("email"));
                acc.setPin(rs.getInt("pin"));
                acc.setName(rs.getString("full_name"));
                acc.setBalance(rs.getDouble("balance"));
                acc.setId(rs.getInt("id"));

                return acc;
            }
            else{
                System.out.println("Invalid username or password!");
                return null;
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return null;
    }

    public Double getBalance(int accountID){
        try {
            String sql = "SELECT * FROM user WHERE id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, accountID);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getDouble("balance");

        } catch (Exception e) {
             e.printStackTrace();
        }
        return null;
    }

}
