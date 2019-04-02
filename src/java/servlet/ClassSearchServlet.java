/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Win7
 */
@WebServlet(name = "ClassSearchServlet", urlPatterns = {"/tim-lop"})
public class ClassSearchServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //doGet
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int subjectId = Integer.parseInt(request.getParameter("subjectId"));
        List<model.ClassTKB> classes = model.ClassTKBDAO.listClassBySubjectId(subjectId);
        
        if(!classes.isEmpty()){
            response.sendRedirect("registerPage.jsp");
            HttpSession session = request.getSession();
            session.setAttribute("classes", classes);
            session.setAttribute("subjectId", subjectId);
        }else{
            
        }
    }

}
