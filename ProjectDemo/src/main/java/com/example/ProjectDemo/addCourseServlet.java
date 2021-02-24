package com.example.ProjectDemo;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

@WebServlet(name = "addCourseServlet", value = "/add-course")
public class addCourseServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String courseNumber = request.getParameter("CourseNumber");
        String courseName = request.getParameter("CourseName");
        String department = request.getParameter("Department");
        String creditHours = request.getParameter("CreditHours");
        String capacity = request.getParameter("Capacity");
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        String courseDescription = request.getParameter("CourseDescription");
        String monday = request.getParameter("Monday");
        String tuesday = request.getParameter("Tuesday");
        String wednesday = request.getParameter("Wednesday");
        String thursday = request.getParameter("Thursday");
        String friday = request.getParameter("Friday");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        int creditHoursInt = Integer.parseInt(creditHours);
        int capacityInt = Integer.parseInt(capacity);
        int mondayBit = 0;
        int tuesdayBit = 0;
        int wednesdayBit = 0;
        int thursdayBit = 0;
        int fridayBit = 0;

        System.out.println(startTime);

        if(monday != null){
            mondayBit = Integer.parseInt(monday);
        }
        if(tuesday != null){
            tuesdayBit = Integer.parseInt(tuesday);
        }
        if(wednesday != null){
            wednesdayBit = Integer.parseInt(wednesday);
        }
        if(thursday != null){
            thursdayBit = Integer.parseInt(thursday);
        }
        if(friday != null){
            fridayBit = Integer.parseInt(friday);
        }


        CoursesDAO courseDao = new CoursesDAO();

        try {
                courseDao.addCoursesDB(courseNumber, courseName, department, email, lastName, courseDescription, creditHoursInt, startTime, endTime, capacityInt, mondayBit, tuesdayBit, wednesdayBit, thursdayBit, fridayBit);
                String destPage = "courses_register.jsp";

            RequestDispatcher dispatcher = request.getRequestDispatcher(destPage);
            dispatcher.forward(request, response);

        } catch (SQLException | ClassNotFoundException | ParseException ex) {
            throw new ServletException(ex);
        }


    }

}