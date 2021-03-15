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

@WebServlet(name = "displayAssignmentsServlet", value = "/display-assignments")
public class displayAssignmentsServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //System.out.println("Display Instructor Courses is Running");
        AssignmentDAO assignmentDao = new AssignmentDAO();
        HttpSession session = request.getSession();
        List<Assignments> assignmentsList = null;

        String email = (String) session.getAttribute("email");
        String userTypeVariable = (String) session.getAttribute("userType");
        int pageCourseID = Integer.parseInt((String)session.getAttribute("courseID"));

        try {

            if(userTypeVariable.equals("student")){
                assignmentsList = assignmentDao.getStudentAssignmentsList(pageCourseID);
            }
            else {
                assignmentsList = assignmentDao.getAssignmentsList(pageCourseID);
            }

            session.setAttribute("assignmentList", assignmentsList);

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
