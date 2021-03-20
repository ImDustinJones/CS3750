package com.example.ProjectDemo;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

@WebServlet(name = "SignUpServlet", urlPatterns = "/addUser")
public class SignUpServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String birthDate = request.getParameter("birthDate");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String userType = request.getParameter("userType");
        // get paramters from signup.jsp

        // creating a database object that is able to create user objects as well as connect to database
        UserDAO userDao = new UserDAO();
        InstructorDAO instructorDao = new InstructorDAO();

        // checking email and age before redirecting and executing DAO add user to database method
        try {
            String destPage ="signup.jsp";
            if(userType.equals("student")){
                if(!userDao.checkEmail(email) || !userDao.checkAge(birthDate)){
                    request.setAttribute("emailAgeFail", "true");
                }
                else{

                    Users user = userDao.addUserDB(email, firstName, lastName, password, birthDate);
                    HttpSession session = request.getSession();
                    session.setAttribute("user", user);
                    session.setAttribute("firstName", firstName);
                    session.setAttribute("lastName", lastName);
                    session.setAttribute("email", email);
                    session.setAttribute("studentID", user.getUserId());
                    destPage = "home.jsp";
                }
            }

            if(userType.equals("instructor")){
                if(!instructorDao.checkEmail(email) || !instructorDao.checkAge(birthDate)){
                    request.setAttribute("emailAgeFail", "true");
                }
                else{
                    Instructors instructor = instructorDao.addInstructorDB(email, firstName, lastName, password, birthDate);
                    HttpSession session = request.getSession();
                    session.setAttribute("instructor", instructor);
                    session.setAttribute("firstName", firstName);
                    session.setAttribute("lastName", lastName);
                    session.setAttribute("email", email);
                    destPage = "home.jsp";
                }
            }

            //redirects to new page
            RequestDispatcher dispatcher = request.getRequestDispatcher(destPage);
            dispatcher.forward(request, response);

        } catch (SQLException | ClassNotFoundException | ParseException ex) {
            throw new ServletException(ex);
        }


    }
}
