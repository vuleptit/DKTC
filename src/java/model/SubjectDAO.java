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
import java.util.List;

/**
 *
 * @author Win7
 */
public class SubjectDAO {

    public static boolean isSubjectOnTemp(int idClass) {//kiem tra lop co ton tai trong temp khong
        List<Integer> idsSubjectOnTemp = TempDAO.listIdSubjectOnTemp();
        ClassTKB classTKB = ClassTKBDAO.getClassById(idClass);
        for (int id : idsSubjectOnTemp) {
            if (classTKB.getIdSubject() == id) {
                return true;
            }
        }
        return false;
    }

    public static Subject getSubjectByIdClass(int idClass) {
        ClassTKB classTKB = ClassTKBDAO.getClassById(idClass);
        Subject subject = new Subject();
        
        String copyFromTempQuery = "select * from subject where id = ?";
        Connection connection = ConnectionManagement.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = connection.prepareStatement(copyFromTempQuery);
            stmt.setInt(1, classTKB.getIdSubject());
            ResultSet subjectSet = stmt.executeQuery();
            while(subjectSet.next()){
                subject.setId(subjectSet.getInt("id"));
                subject.setName(subjectSet.getString("name"));
                subject.setTCNum(subjectSet.getInt("so_tin_chi"));
            }
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
        
        return subject;
    }
}
