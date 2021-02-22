package com.example.ProjectDemo;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.text.ParseException;

@WebServlet(name = "EditProfileDAO", value = "/EditProfileDAO")
public class EditProfileDAO extends HttpServlet {

    public EditProfileDAO(){ super();}
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {

        String email = request.getParameter("email");
        String firstname = request.getParameter("firstName");
        String lastname = request.getParameter("lastName");

        String address = request.getParameter("address");
        String city = request.getParameter("city");
        String state = request.getParameter("state");
        String zip = request.getParameter("zip");
        String phonenumber = request.getParameter("phoneNumber");

        String bio = request.getParameter("bioBox");

        UserDAO userDao = new UserDAO();
        InstructorDAO instructorDao = new InstructorDAO();

        try {
            Users user = userDao.updateUser(email, firstname, lastname, bio, address, city,
                    state, zip, phonenumber);

            Instructors instructor = instructorDao.updateUser(email, firstname, lastname, bio, address, city,
                    state, zip, phonenumber);
            String destPage = "edit_profile.jsp";

            if (user != null) {
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
                destPage = "edit_profile.jsp";
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
                destPage = "edit_profile.jsp";
            }
            else {
                String message = "Invalid email/password";
                request.setAttribute("message", message);
            }

            RequestDispatcher dispatcher = request.getRequestDispatcher(destPage);
            dispatcher.forward(request, response);

        } catch (SQLException | ClassNotFoundException | ParseException ex) {
            throw new ServletException(ex);
        }







    }
//Working Now.
}
