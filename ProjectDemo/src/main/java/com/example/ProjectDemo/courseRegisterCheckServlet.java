package com.example.ProjectDemo;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "courseRegisterCheckServlet", urlPatterns = "/courseRegisterCheckServlet")
public class courseRegisterCheckServlet extends HttpServlet {
    public courseRegisterCheckServlet (){ super();}
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String userType = (String) session.getAttribute("userType");
        System.out.println(userType);
        if (userType.equals("instructor")){
            String destPage = "courses_register.jsp";
            response.sendRedirect("courses_register.jsp");
        }
        else{
            String destPage = "courses_register_student.jsp";
            response.sendRedirect("courses_register_student.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}