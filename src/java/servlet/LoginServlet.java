/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.StudentDAO;

/**
 *
 * @author Win7
 */

@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet{

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userName = req.getParameter("userName");
        String password = req.getParameter("password");
        HttpSession session = req.getSession();
        
        if(session.getAttribute("userName")==null){//để đảm bảo rằng hiện nay chưa có nick nào đã đăng nhập vào hệ thống
            if(StudentDAO.loginCheck(userName, password)){
                model.TempDAO.deleteDataTemp();
                if(!model.ChosenClassesDAO.isChosenEmpty()){
                    model.TempDAO.copyFromChosenClasses();
                }
                resp.sendRedirect("registerPage.jsp");
                
            }else{
                session.setAttribute("isValid", "false");
                resp.sendRedirect("login.jsp");
            }
        }else{
            resp.sendRedirect("register.jsp");
        }
    }
    
}
