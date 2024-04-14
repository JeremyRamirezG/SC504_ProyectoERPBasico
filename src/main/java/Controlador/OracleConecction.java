package Controlador;

import java.sql.Connection;
import java.sql.DriverManager;
public class OracleConecction {
//    String url="jdbc:oracle:thin:@localhost:1521:orcl";
    String url="jdbc:oracle:thin:@192.168.100.248:1521:orcl";
    
    String user="ADMIN_TIENDA",pass="admin_tienda_proyecto";    
    Connection con;
    public Connection getConnection(){
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con=DriverManager.getConnection(url,user,pass);
        } catch (Exception e) {   
            System.out.println("********"+e.getMessage());
        }
        return con;
    }
}