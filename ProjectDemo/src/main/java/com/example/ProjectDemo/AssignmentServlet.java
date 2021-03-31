package com.example.ProjectDemo;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

@WebServlet(name = "AssignmentServlet", value = "/AssignmentServlet")
public class AssignmentServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String assignmentName = request.getParameter("assignName");
        int possiblePoints = Integer.parseInt(request.getParameter("assignScore"));

        String assignDueDate = (String) request.getParameter("assignDate");
        StringBuilder sb = new StringBuilder();
        sb.append(assignDueDate);
        sb.deleteCharAt(sb.lastIndexOf("T"));
        sb.insert(10, ' ');


        assignDueDate = sb.toString();
        String assignFile = request.getParameter("submissionType");
        String description = request.getParameter("assignDescription");

        String lastName = (String) session.getAttribute("lastName");
        String email = (String) session.getAttribute("email");
        int courseID = Integer.parseInt(request.getParameter( "courseIDMain2" ));

        AssignmentDAO assignDAO = new AssignmentDAO();
        NotificationDAO notificationDAO = new NotificationDAO();

        try {
            assignDAO.addAssignmentDB(assignmentName, courseID, email, lastName, description, possiblePoints, assignDueDate, assignFile);
            notificationDAO.addNewAssignmentNotification(courseID, assignmentName);
            String destPage = "course_main.jsp?courseID=" + courseID;
            response.sendRedirect(destPage);

        } catch (SQLException | ClassNotFoundException | ParseException ex) {
            throw new ServletException(ex);
        }
    }
}
