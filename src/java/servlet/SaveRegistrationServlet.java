/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet(name = "SaveRegistration", urlPatterns = {"/luu-dang-ky"})
public class SaveRegistrationServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet SaveRegistration</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SaveRegistration at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
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
        HttpSession session = request.getSession();
        
        int TCSum = model.TempDAO.TCSumOnTemp();
        if((TCSum >= 14 && TCSum <= 30) || (TCSum == 0)){//tổng tín chỉ thoả mãn
            if(model.TempDAO.isSlotTempEmpty()){
                session.setAttribute("sum", TCSum);
                session.setAttribute("savingStatus", "no");
                model.TempDAO.deleteDataTemp();
                model.ClassTKBDAO.updateSlotClass("up");//trả lại slot cho các class sau khi bị xoá khỏi ChosenClasses
                model.ChosenClassesDAO.deleteDataFromChosen();
                response.sendRedirect("registerPage.jsp");
            }else{
                model.ClassTKBDAO.updateSlotClass("up");
                model.ChosenClassesDAO.deleteDataFromChosen();
                model.ChosenClassesDAO.copyDataFromTemp();
                model.ClassTKBDAO.updateSlotClass("down");
                session.setAttribute("savingStatus", "success");
                response.sendRedirect("registerPage.jsp");
            }
            
        }else if(TCSum < 14){
            session.setAttribute("savingStatus", "less");
            response.sendRedirect("registerPage.jsp");
        }else if(TCSum > 30){
            session.setAttribute("savingStatus", "more");
            response.sendRedirect("registerPage.jsp");
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
