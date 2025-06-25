package Database;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectDatabase {
    private static final String URL = "jdbc:mysql://localhost:3306/ss1";
    private static final String USER = "root";
    private static final String PASSWORD = "123456";

    public static Connection getConnection(){
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        }catch (Exception e){
            System.out.println("Kết nối cơ sở giữ liệu thất bại");
            System.out.println(e.getMessage());
            return null;
        }
    }
}
