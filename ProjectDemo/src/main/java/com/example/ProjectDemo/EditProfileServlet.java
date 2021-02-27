package com.example.ProjectDemo;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.text.ParseException;

@WebServlet(name = "EditProfileDAO", value = "/edit-profile-s")
public class EditProfileServlet extends HttpServlet {

    public EditProfileServlet(){ super();}
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
        String linkOne = request.getParameter("linkOne");
        String linkTwo = request.getParameter("linkTwo");
        String linkThree = request.getParameter("linkThree");

        //if block to catch blanks
        if(firstname.equals("")){//if the parameter was empty
            try {
                firstname = getValue(email, "firstName");//get the value already stored in db
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        if(lastname.equals("")){//if the parameter was empty
            try {
                lastname = getValue(email, "lastName");//get the value already stored in db
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        if(address.equals("")){//if the parameter was empty
            try {
                address = getValue(email, "address");//get the value already stored in db
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        if(city.equals("")){//if the parameter was empty
            try {
                city = getValue(email, "city");//get the value already stored in db
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        if(state.equals("")){//if the parameter was empty
            try {
                state = getValue(email, "state");//get the value already stored in db
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        if(zip.equals("")){//if the parameter was empty
            try {
                zip = getValue(email, "zip");//get the value already stored in db
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        if(phonenumber.equals("")){//if the parameter was empty
            try {
                phonenumber = getValue(email, "phoneNumber");//get the value already stored in db
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        if(bio.equals("")){//if the parameter was empty
            try {
                bio = getValue(email, "bio");//get the value already stored in db
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        if(linkOne.equals("")){//if the parameter was empty
            try {
                linkOne = getValue(email, "link1");//get the value already stored in db
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        if(linkTwo.equals("")){//if the parameter was empty
            try {
                linkTwo = getValue(email, "link2");//get the value already stored in db
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        if(linkThree.equals("")){//if the parameter was empty
            try {
                linkThree = getValue(email, "link3");//get the value already stored in db
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        //end block

        UserDAO userDao = new UserDAO();
        InstructorDAO instructorDao = new InstructorDAO();

        try {
            Users user = userDao.updateUser(email, firstname, lastname, bio, address, city,
                    state, zip, phonenumber, linkOne, linkTwo, linkThree);

            Instructors instructor = instructorDao.updateUser(email, firstname, lastname, bio, address, city,
                    state, zip, phonenumber, linkOne, linkTwo, linkThree);
            //String destPage = "edit_profile.jsp";

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

                session.setAttribute("link1", linkOne);
                session.setAttribute("link2", linkTwo);
                session.setAttribute("link3", linkThree);

                //destPage = "edit_profile.jsp";
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

                session.setAttribute("link1", linkOne);
                session.setAttribute("link2", linkTwo);
                session.setAttribute("link3", linkThree);
                //destPage = "edit_profile.jsp";
            }
            else {
                String message = "Invalid email/password";
                request.setAttribute("message", message);
            }

            //RequestDispatcher dispatcher = request.getRequestDispatcher(destPage);
            //dispatcher.forward(request, response);
            response.sendRedirect("edit_profile.jsp");

        } catch (SQLException | ClassNotFoundException | ParseException ex) {
            throw new ServletException(ex);
        }







    }

    public Connection connectDatabase() throws SQLException, ClassNotFoundException {
        String jdbcURL = "jdbc:sqlserver://titan.cs.weber.edu:10433;database=LMS_RunTime";
        String dbUser = "LMS_RunTime";
        String dbPassword = "password1!";

        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

        return DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
    }

    private String getValue(String email, String column) throws SQLException, ClassNotFoundException {
        Connection connection = connectDatabase();
        String sql = "SELECT * FROM students WHERE email = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, email);
        ResultSet result = statement.executeQuery();

        if (result.next()) {
            return result.getString(column);
        }
        else{
            sql = "SELECT * FROM instructors WHERE email = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, email);
            result = statement.executeQuery();
            result.next();
            return result.getString(column);
        }


    }

//Working Now.
}
