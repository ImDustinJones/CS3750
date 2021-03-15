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

@WebServlet(name = "SortAssignmentsServlet", value = "/sort-assignments")
public class SortServletAssignments extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {


    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //System.out.println("Display Instructor Courses is Running");
        AssignmentDAO assignmentDao = new AssignmentDAO();
        HttpSession session = request.getSession();
        List<Assignments> assignmentsList = null;

        String email = (String) session.getAttribute("email");
        String userTypeVariable = (String) session.getAttribute("userType");
        String filterType = request.getParameter("filter");
        int pageCourseID = Integer.parseInt((String)session.getAttribute("courseID"));

        try {

            assignmentsList = assignmentDao.DateSortListAssignment(pageCourseID, filterType);
            session.setAttribute("assignmentList", assignmentsList);
            String destPage = "course_main.jsp";

            response.sendRedirect(destPage);

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

    }

}
