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

/**
 *
 * @author Win7
 */
public class ClassTKBDAO {

    public static List<ClassTKB> listClassBySubjectId(int idSubject) {//search theo id cua mon hoc
        String query = "Select * from class where id_subject = ?";
        List<ClassTKB> classes = new ArrayList<>();
        ClassTKB classTKB = null;
        Connection connection = ConnectionManagement.getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement(query);
            stmt.setInt(1, idSubject);
            ResultSet rs = stmt.executeQuery();
            makeQuerry(rs, classTKB, classes);
        } catch (SQLException ex) {
            //in ra loi doc DB
        } finally {
            try {
                connection.close();
                stmt.close();
            } catch (SQLException ex) {
                //loi khong dong duoc connection hoac stmt
            }
        }
        return classes;
    }
//----------------------------------------------------------------------------    

    public static boolean isClassOnTemp(int idClass) {//kiem tra lop co ton tai trong temp khong
        List<ClassTKB> ClassTemp = TempDAO.listClassOnTemp();
        for (ClassTKB classTKB : ClassTemp) {
            if (classTKB.getId() == idClass) {
                return true;
            }
        }
        return false;
    }
//----------------------------------------------------------------------------    

    public static boolean isTimeConflict(int idClass) {//kiem tra trung thoi khoa bieu
        List<Integer> idClassOnTemp = TempDAO.listIdClassOnTemp();
        ClassTKB classNew = ClassTKBDAO.getClassById(idClass);
        for (int idTempClass : idClassOnTemp) {
            ClassTKB classTemp = ClassTKBDAO.getClassById(idTempClass);
            if ((classNew.getIdSubject() != classTemp.getIdSubject()) && (classNew.getTime()).equalsIgnoreCase(classTemp.getTime())) {
                return true;
            }
        }
        return false;
    }
//----------------------------------------------------------------------------    

    public static void updateSlotClass(String type) {//giam slot cua cac mon trong temp di 1 
        String getDataFromChosenQuery = "select * from chosen_classes";
        String updateSlotQuery = "update class set slot = ? where id = ? and id_subject = ?";
        String getSlotQuery = "select slot from class where id = ? and id_subject = ?";
        Connection connection = ConnectionManagement.getConnection();
        PreparedStatement getDataFromChosenstmt = null;
        PreparedStatement getSlotstmt = null;
        PreparedStatement updateSlotstmt = null;

        try {
            getDataFromChosenstmt = connection.prepareStatement(getDataFromChosenQuery);
            ResultSet chosenSet = getDataFromChosenstmt.executeQuery();
            while (chosenSet.next()) {
                int id_class = chosenSet.getInt("id_class");
                int id_subject = chosenSet.getInt("id_subject");
                getSlotstmt = connection.prepareStatement(getSlotQuery);
                getSlotstmt.setInt(1, id_class);
                getSlotstmt.setInt(2, id_subject);
                ResultSet classSet = getSlotstmt.executeQuery();
                while (classSet.next()) {
                    if (type.equals("up")) {
                        int slotLeft = classSet.getInt("slot") + 1;
                        updateSlotstmt = connection.prepareStatement(updateSlotQuery);
                        updateSlotstmt.setInt(1, slotLeft);
                        updateSlotstmt.setInt(2, id_class);
                        updateSlotstmt.setInt(3, id_subject);
                        updateSlotstmt.execute();
                    } else if (type.equals("down")) {
                        int slotLeft = classSet.getInt("slot") - 1;
                        updateSlotstmt = connection.prepareStatement(updateSlotQuery);
                        updateSlotstmt.setInt(1, slotLeft);
                        updateSlotstmt.setInt(2, id_class);
                        updateSlotstmt.setInt(3, id_subject);
                        updateSlotstmt.execute();
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Loi trong catch StudentDAO: " + e.getMessage());
        } finally {
            try {
                connection.close();
                getDataFromChosenstmt.close();
                getSlotstmt.close();
                updateSlotstmt.close();
            } catch (Exception e) {
                System.out.println("Loi trong finally StudentDAO: " + e.getMessage());
            }
        }
    }
//-----------------------------------------------------------------------------    

    public static void makeQuerry(ResultSet rs, ClassTKB classTKB, List<ClassTKB> classes) {
        try {
            while (rs.next()) {
                classTKB = new ClassTKB();
                classTKB.setId(rs.getInt("id"));
                classTKB.setIdSubject(rs.getInt("id_subject"));
                classTKB.setName(rs.getString("name"));
                classTKB.setSlot(rs.getInt("slot"));
                classTKB.setTeacher(rs.getString("teacher"));
                classTKB.setRoom(rs.getString("room"));
                classTKB.setName(rs.getString("name"));
                classTKB.setTime(rs.getString("time"));
                classes.add(classTKB);
            }
        } catch (SQLException ex) {
            //loi eo gi khong biet nua
        }
    }
//-----------------------------------------------------------------------------    

    public static ClassTKB getClassById(int idClass) {
        String query = "Select * from class where id = ?";
        ClassTKB classTKB = new ClassTKB();
        Connection connection = ConnectionManagement.getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement(query);
            stmt.setInt(1, idClass);
            ResultSet classSet = stmt.executeQuery();
            while (classSet.next()) {
                classTKB.setId(classSet.getInt("id"));
                classTKB.setIdSubject(classSet.getInt("id_subject"));
                classTKB.setName(classSet.getString("name"));
                classTKB.setSlot(classSet.getInt("slot"));
                classTKB.setTeacher(classSet.getString("teacher"));
                classTKB.setRoom(classSet.getString("room"));
                classTKB.setTime(classSet.getString("time"));
            }
        } catch (SQLException ex) {
            //in ra loi doc DB
        } finally {
            try {
                connection.close();
                stmt.close();
            } catch (SQLException ex) {
                //loi khong dong duoc connection hoac stmt
            }
        }
        return classTKB;
    }
}
