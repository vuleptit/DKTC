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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Win7
 */
public class ChosenClassesDAO {

    public static boolean isChosenEmpty() {
        String query = "select * from chosen_classes";
        Connection connection = ConnectionManagement.getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement(query);
            ResultSet chosenSet = stmt.executeQuery();
            if (chosenSet.next()) {
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Loi trong catch isChosenEmpty: " + e.getMessage());
        } finally {
            try {
                connection.close();
                stmt.close();
            } catch (Exception e) {
                System.out.println("Loi trong finally isChosenEmpty: " + e.getMessage());
            }
        }
        return true;
    }
    
    public static void deleteDataFromChosen(){
        String deleteChosenClassQuery = "delete from chosen_classes";
        Connection connection = ConnectionManagement.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = connection.prepareStatement(deleteChosenClassQuery);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Loi trong catch delete ChosenDAO: " + e.getMessage());
        } finally {
            try {
                connection.close();
                stmt.close();
            } catch (Exception e) {
                System.out.println("Loi trong finally delete ChosenDAO: " + e.getMessage());
            }
        }
    }
    
    public static void copyDataFromTemp(){
        String copyFromTempQuery = "insert into chosen_classes select * from temp";
        Connection connection = ConnectionManagement.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = connection.prepareStatement(copyFromTempQuery);
            stmt.execute();
        } catch (SQLException e) {
            System.out.println("Loi trong catch copyDataFromTemp ChosenDAO: " + e.getMessage());
        } finally {
            try {
                connection.close();
                stmt.close();
            } catch (Exception e) {
                System.out.println("Loi trong finally copyDataFromTemp ChosenDAO: " + e.getMessage());
            }
        }
    }

}

