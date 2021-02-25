package com.example.ProjectDemo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "displayInstructorCoursesServlet", value = "/display-instructors-course")
public class displayInstructorCoursesServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("Display Instructor Courses is Running");
        CoursesDAO courseDao = new CoursesDAO();
        HttpSession session = request.getSession();
        List<Courses> courseList = null;

        String email = (String) session.getAttribute("email");
        try {
            courseList = courseDao.getCourseList(email);
//            for(Courses course : courseList){
//                System.out.println(course.toString());
//            }
            session.setAttribute("courselist", courseList);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
