/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.ChosenClasses;

/**
 *
 * @author Win7
 */
@WebServlet(name = "ClassChooseServlet", urlPatterns = {"/chon-lop"})
public class ClassChooseServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (request.getParameter("chosenClass") != null) {
            int idClassChosen = Integer.parseInt(request.getParameter("chosenClass"));
            if (model.TempDAO.isTempEmpty()) {
                model.TempDAO.addClassToTemp(idClassChosen);
                response.sendRedirect("registerPage.jsp");
            } else {
                if (!model.SubjectDAO.isSubjectOnTemp(idClassChosen)) {
                    if (!model.ClassTKBDAO.isTimeConflict(idClassChosen)) {
                        model.TempDAO.addClassToTemp(idClassChosen);
                        response.sendRedirect("registerPage.jsp");
                    } else {
                        session.setAttribute("conflict", "true");
                        response.sendRedirect("registerPage.jsp");
                    }
                } else {
                    if (!model.ClassTKBDAO.isTimeConflict(idClassChosen)) {
                        model.TempDAO.updateClassOnTemp(idClassChosen);
                        response.sendRedirect("registerPage.jsp");
                    } else {
                        session.setAttribute("conflict", "true");
                        response.sendRedirect("registerPage.jsp");
                    }
                }
            }
            if (session != null) {
                session.invalidate();
            }
        } else {
            response.sendRedirect("registerPage.jsp");

        }

    }

}
