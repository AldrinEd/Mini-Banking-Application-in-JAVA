package banking;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DBConnection {
    
    static Connection con;
    static Properties prop = new Properties();

    public static Connection getConnection(){
        try {
            String mysqlJDBCDriver = "com.mysql.cj.jdbc.Driver";
            String url = prop.getProperty("db.url");
            String user = prop.getProperty("db.user");
            String password = prop.getProperty("db.password");

            Class.forName(mysqlJDBCDriver);
            con = DriverManager.getConnection(url, user, password);
            
        } catch (Exception e) {
            System.out.println("Connection Failed! " + e.getMessage() );
        }
        return con;
    }
}
