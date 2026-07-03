package banking;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DBConnection {
    
    static Connection con;
    static Properties prop = new Properties();

    public static Connection getConnection(){
        try {
            InputStream input = DBConnection.class.getClassLoader().getResourceAsStream("config.properties");
            prop.load(input);

            if(input == null){
                System.out.println("config.properties not found!");
                return null;
            }

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
