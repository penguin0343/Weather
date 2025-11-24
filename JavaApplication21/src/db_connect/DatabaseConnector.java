/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package db_connect;
import java.sql.*;

/**
 *
 * @author Admin
 */
public class DatabaseConnector {
    String host = null;
    String port = null;
    String database = "oop";
    String user = "myuser";
    String password = "mypassword";
    
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    
    public DatabaseConnector(String host, String port) {
        try {
            String connectUrl = "jdbc:mysql://" + host + ":" + port + "/" + this.database;
            System.out.println("Ket noi thanh cong den mysql");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
