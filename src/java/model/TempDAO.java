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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static model.ClassTKBDAO.makeQuerry;

/**
 *
 * @author Win7
 */
public class TempDAO {

    public static void copyFromChosenClasses() {
        deleteDataTemp();
        if (!ChosenClassesDAO.isChosenEmpty()) {
            String copyFromTempQuery = "insert into temp select * from chosen_classes";
            Connection connection = ConnectionManagement.getConnection();
            PreparedStatement stmt = null;

            try {
                stmt = connection.prepareStatement(copyFromTempQuery);
                stmt.execute();
            } catch (SQLException e) {
                System.out.println("Loi trong catch copyFromChosenClasses TempDAO: " + e.getMessage());
            } finally {
                try {
                    connection.close();
                    stmt.close();
                } catch (Exception e) {
                    System.out.println("Loi trong finally copyFromChosenClasses TempDAO: " + e.getMessage());
                }
            }
        }
    }
//----------------------------------------------------------------------------
    public static void deleteDataTemp() {
        String deleteDataFromTempQuery = "delete from temp";
        Connection connection = ConnectionManagement.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = connection.prepareStatement(deleteDataFromTempQuery);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Loi trong catch TempDAO: " + e.getMessage());
        } finally {
            try {
                connection.close();
                stmt.close();
            } catch (Exception e) {
                System.out.println("Loi trong finally TempDAO: " + e.getMessage());
            }
        }
    }
//----------------------------------------------------------------------------    
    public static List<Integer> listIdClassOnTemp(){
        List<Integer> ids = new ArrayList<>();
        List<ClassTKB> classes;
        
        classes = listClassOnTemp();
        for(ClassTKB classTKB : classes){
            ids.add(classTKB.getId());
        }
        
        return ids;
    }
//----------------------------------------------------------------------------    
    public static List<Integer> listIdSubjectOnTemp(){
        List<Integer> ids = new ArrayList<>();
        List<ClassTKB> classes;
        
        classes = listClassOnTemp();
        for(ClassTKB classTKB : classes){
            ids.add(classTKB.getIdSubject());
        }
        
        return ids;
    }
//----------------------------------------------------------------------------    
    public static List<ClassTKB> listClassOnTemp(){
        String getAllDataOnTempQuery = "Select * from temp";
        String getClassOnTempQuery = "Select * from class where id = ? and id_subject = ?";
        List<ClassTKB> classes = new ArrayList<>();
        Connection connection = ConnectionManagement.getConnection();
        PreparedStatement getTempSetstmt = null;
        PreparedStatement getClassSetstmt = null;
        try {
            getTempSetstmt = connection.prepareStatement(getAllDataOnTempQuery);
            ResultSet tempSet = getTempSetstmt.executeQuery();
            while(tempSet.next()){
                getClassSetstmt = connection.prepareStatement(getClassOnTempQuery);
                getClassSetstmt.setInt(1, tempSet.getInt("id_class"));
                getClassSetstmt.setInt(2, tempSet.getInt("id_subject"));
                ResultSet classSet = getClassSetstmt.executeQuery();
                while(classSet.next()){
                    ClassTKB classTKB = new ClassTKB();
                    
                    classTKB.setId(classSet.getInt("id"));
                    classTKB.setIdSubject(classSet.getInt("id_subject"));
                    classTKB.setName(classSet.getString("name"));
                    classTKB.setSlot(classSet.getInt("slot"));
                    classTKB.setTeacher(classSet.getString("teacher"));
                    classTKB.setRoom(classSet.getString("room"));
                    classTKB.setTime(classSet.getString("time"));
                    
                    classes.add(classTKB);
                }
            }
        } catch (SQLException ex) {
            //in ra loi doc DB
        } finally {
            try {
                connection.close();
//                getTempSetstmt.close();
//                getClassSetstmt.close();
            } catch (SQLException ex) {
                //loi khong dong duoc connection hoac stmt
            }
        }
        return classes;
    }

    //------------------------------------------------------------------------
    public static boolean isTempEmpty() {
        String query = "select * from temp";
        Connection connection = ConnectionManagement.getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement(query);
            ResultSet tempSet = stmt.executeQuery();
            if (tempSet.next()) {
                return false;
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
        return true;
    }
//----------------------------------------------------------------------------    
    public static void addClassToTemp(int idClass){
        String query = "insert into temp (id_class, id_subject) values (?, ?)";
        Connection connection = ConnectionManagement.getConnection();
        PreparedStatement stmt = null;
        
        ClassTKB classTKB = ClassTKBDAO.getClassById(idClass);
        
        try {
            stmt = connection.prepareStatement(query);
            stmt.setInt(1, classTKB.getId());
            stmt.setInt(2, classTKB.getIdSubject());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(TempDAO.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                connection.close();
                stmt.close();
            } catch (SQLException ex) {
                Logger.getLogger(TempDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
//----------------------------------------------------------------------------
    public static void updateClassOnTemp(int idClassNew){
        String updateQuery = "update temp set id_class = ? where id_subject = ?";
        Connection connection = ConnectionManagement.getConnection();
        PreparedStatement updateStatement = null;
        
        try {
            updateStatement = connection.prepareStatement(updateQuery);
            updateStatement.setInt(1, idClassNew);
            ClassTKB classTKB = ClassTKBDAO.getClassById(idClassNew);
            updateStatement.setInt(2, classTKB.getIdSubject());
            updateStatement.execute();
        } catch (SQLException ex) {
            Logger.getLogger(TempDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
//-----------------------------------------------------------------------------
    public static boolean isSlotTempEmpty() {
        List<Integer> idClassOnTemp = listIdClassOnTemp();
        for(int idTempClass : idClassOnTemp){
            ClassTKB classTKB = ClassTKBDAO.getClassById(idTempClass);
            if(classTKB.getSlot() <= 0){
                return true;
            }
        }
        return false;
    }
//-----------------------------------------------------------------------------
    public static int TCSumOnTemp(){
        int sum = 0;
        
        List<ClassTKB> listClassOnTemp = TempDAO.listClassOnTemp();
        for(ClassTKB classTemp : listClassOnTemp){
            Subject subject = SubjectDAO.getSubjectByIdClass(classTemp.getId());
            sum += subject.getTCNum();
        }
        
        return sum;
    }
//-----------------------------------------------------------------------------
    public static void deleteClassOnTemp(int idClass){
        String deleteDataFromTempQuery = "delete from temp where id_class = ?";
        Connection connection = ConnectionManagement.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = connection.prepareStatement(deleteDataFromTempQuery);
            stmt.setInt(1, idClass);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Loi trong catch TempDAO: " + e.getMessage());
        } finally {
            try {
                connection.close();
                stmt.close();
            } catch (Exception e) {
                System.out.println("Loi trong finally TempDAO: " + e.getMessage());
            }
        }
    }
}

