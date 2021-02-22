package com.example.ProjectDemo;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.annotation.WebServlet;


@WebServlet(name = "LoginServlet", value = "/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public LoginServlet() {
        super();
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        UserDAO userDao = new UserDAO();
        InstructorDAO instructorDao = new InstructorDAO();

        try {
            Users user = userDao.checkLogin(email, password);
            Instructors instructor = instructorDao.checkLoginInstructor(email, password);
            request.setAttribute("loginResult", "true");
            String destPage = "index.jsp";

            if (user != null) { // here it will set the attributes for profile and home page
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                session.setAttribute("email", email);
                session.setAttribute("firstName", user.getFirstname());
                session.setAttribute("lastName", user.getLastname());
                session.setAttribute("bio", user.getBio());

                session.setAttribute("address", user.getAddress());
                session.setAttribute("city", user.getCity());
                session.setAttribute("state", user.getState());
                session.setAttribute("zip", user.getZip());
                session.setAttribute("phoneNumber", user.getPhonenumber());

                destPage = "home.jsp";
            } 
            else if (instructor != null) {
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                session.setAttribute("email", email);
                session.setAttribute("firstName", instructor.getFirstname());
                session.setAttribute("lastName", instructor.getLastname());
                session.setAttribute("bio", instructor.getBio());

                session.setAttribute("address", instructor.getAddress());
                session.setAttribute("city", instructor.getCity());
                session.setAttribute("state", instructor.getState());
                session.setAttribute("zip", instructor.getZip());
                session.setAttribute("phoneNumber", instructor.getPhonenumber());

                destPage = "home.jsp";
            }
            else {
                String message = "Invalid email/password";
                request.setAttribute("message", message);
            }

            RequestDispatcher dispatcher = request.getRequestDispatcher(destPage);
            dispatcher.forward(request, response);

        } catch (SQLException | ClassNotFoundException ex) {
            throw new ServletException(ex);
        }
    }
}
