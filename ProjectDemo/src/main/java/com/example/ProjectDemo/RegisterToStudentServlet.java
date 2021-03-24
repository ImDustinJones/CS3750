package com.example.ProjectDemo;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

@WebServlet(name = "RegisterToStudentServlet", value = "/RegisterToStudentServlet")
public class RegisterToStudentServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String courseID = request.getParameter("courseID");
        String email = request.getParameter("email");

        RegistrationsDAO regDAO = new RegistrationsDAO();

        try {

            regDAO.registerStudent(email, courseID);
            regDAO.updateTuition(email);;

            String destPage = "courses_register_student.jsp";
            response.sendRedirect(destPage);

        } catch (SQLException | ClassNotFoundException | ParseException ex) {
            throw new ServletException(ex);
        }
    }
}
