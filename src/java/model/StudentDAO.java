/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Win7
 */
public class StudentDAO {

    public static boolean loginCheck(String userName, String password) {
        String query = "select * from student where name = ? and password = ?";
        Connection connection = ConnectionManagement.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = connection.prepareStatement(query);
            stmt.setString(1, userName);
            stmt.setString(2, password);
            ResultSet studentSet = stmt.executeQuery();
            while (studentSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Loi trong catch StudentDAO: " + e.getMessage());
        } finally {
            try {
                connection.close();
                stmt.close();
            } catch (Exception e) {
                System.out.println("Loi trong finally StudentDAO: " + e.getMessage());
            }
        }
        return false;
    }
}
