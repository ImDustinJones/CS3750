package com.example.ProjectDemo;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

@WebServlet(name = "UnRegisterToStudentServlet", value = "/UnRegisterToStudentServlet")
public class UnRegisterToStudentServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String courseID = request.getParameter("regCourseID");
        String email = request.getParameter("regEmail");

        RegistrationsDAO regDAO = new RegistrationsDAO();

        try {

            regDAO.unRegisterStudent(email, courseID);

            String destPage = "courses_register_student.jsp";
            response.sendRedirect(destPage);

        } catch (SQLException | ClassNotFoundException | ParseException ex) {
            throw new ServletException(ex);
        }
    }
}
