package com.example.ProjectDemo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

@WebServlet(name = "EditCourseServlet", value = "/edit-course")
public class EditCourseServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String courseNumber = request.getParameter("CourseNumber1");
        String courseName = request.getParameter("CourseName1");
        String department = request.getParameter("Department1");
        String creditHours = request.getParameter("CreditHours1");
        String capacity = request.getParameter("Capacity1");
        String startTime = request.getParameter("StartTime1");
        String endTime = request.getParameter("EndTime1");
        String courseDescription = request.getParameter("CourseDescription1");
        String monday = request.getParameter("Monday1");
        String tuesday = request.getParameter("Tuesday1");
        String wednesday = request.getParameter("Wednesday1");
        String thursday = request.getParameter("Thursday1");
        String friday = request.getParameter("Friday1");
        String email = (String) session.getAttribute("email");
        String courseID = request.getParameter("CourseID1");
        int courseIDInt = Integer.parseInt(courseID);
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
            courseDao.updateCoursesDB(courseNumber, courseName, department, email, courseDescription, creditHoursInt, startTime, endTime, capacityInt, mondayBit, tuesdayBit, wednesdayBit, thursdayBit, fridayBit, courseIDInt);
            String destPage = "courses_register.jsp";

            //RequestDispatcher dispatcher = request.getRequestDispatcher(destPage);
            //dispatcher.forward(request, response);
            response.sendRedirect(destPage);

        } catch (SQLException | ClassNotFoundException | ParseException ex) {
            throw new ServletException(ex);
        }


    }
}
