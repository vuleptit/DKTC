/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Win7
 */
public class ConnectionManagement {
    public static Connection getConnection(){
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager
                    .getConnection("jdbc:mysql://localhost:3306/dktcwebappdb?setUnicode=true&characterEncoding=UTF-8","Topper", "aichoai1997");
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return connection;
    }
    
}
