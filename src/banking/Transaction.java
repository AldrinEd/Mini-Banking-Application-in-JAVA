package banking;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Transaction {
    private Connection connection;

    public Transaction(Connection conn){
        this.connection = conn;
    }

    public boolean transfer(int senderId, int receiverId, double amount){
        try {
            connection.setAutoCommit(false);

            String checkBalance = "SELECT * FROM user where id = ?";
            PreparedStatement ps = connection.prepareStatement(checkBalance);
            ps.setInt(1, senderId);
            ResultSet rs = ps.executeQuery();

            if(rs.next() && rs.getDouble("balance") < amount){
                System.out.println("Insufficient Balance!");
                return false;
            }

            String deductToSender = "UPDATE user SET balance = balance - ? WHERE id = ?";
            PreparedStatement psSender = connection.prepareStatement(deductToSender);
            psSender.setDouble(1, amount);
            psSender.setInt(2, senderId);   
            psSender.executeUpdate();

            String addToReceiver = "UPDATE user SET balance = balance + ? WHERE id = ?";
            PreparedStatement psReceiver = connection.prepareStatement(addToReceiver);
            psReceiver.setDouble(1, amount);
            psReceiver.setInt(2, receiverId);
            psReceiver.executeUpdate();

            String transaction = "INSERT INTO transactions (sender_id, receiver_id, amount) VALUES (?, ?, ?)";
            PreparedStatement psTransaction = connection.prepareStatement(transaction);
            psTransaction.setInt(1, senderId);
            psTransaction.setInt(2, receiverId);
            psTransaction.setDouble(3, amount);
            psTransaction.executeUpdate();

            connection.commit();
            return true;
  
        } catch (Exception e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }

        return false;
    }

    public boolean withdraw (int senderId, double amount){
        try {
            String checkBalance = "SELECT * FROM user where id = ?";
            PreparedStatement ps = connection.prepareStatement(checkBalance);
            ps.setInt(1, senderId);
            ResultSet rs = ps.executeQuery();

            if(rs.next() && rs.getDouble("balance") < amount){
                System.out.println("Insufficient Balance!");
                return false;
            }

            String deductToSender = "UPDATE user SET balance = balance - ? WHERE id = ?";
            PreparedStatement psSender = connection.prepareStatement(deductToSender);
            psSender.setDouble(1, amount);
            psSender.setInt(2, senderId);   
            psSender.executeUpdate();

            connection.commit();
            return true;

        } catch (Exception e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }

        return false;
    }
}
