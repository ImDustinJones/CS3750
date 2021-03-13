package com.example.ProjectDemo;

import com.example.ProjectDemo.Courses;
import com.example.ProjectDemo.CoursesDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "displayAssignmentPageServlet", value = "/display-assignment-page")
public class DisplayAssignmentPageServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //System.out.println("Display Instructor Courses is Running");
        AssignmentDAO assignmentDao = new AssignmentDAO();
        HttpSession session = request.getSession();
        Assignments assignment = null;

        String email = (String) session.getAttribute("email");
        String userTypeVariable = (String) session.getAttribute("userType");
        int pageAssignmentID = Integer.parseInt((String)session.getAttribute("assignmentID"));
        int pageCourseID = Integer.parseInt((String)session.getAttribute("courseID"));

        try {
            assignment = assignmentDao.getAssignment(pageCourseID, pageAssignmentID);
            session.setAttribute("theAssignment", assignment);

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }


    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

}
