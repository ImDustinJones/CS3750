package com.example.ProjectDemo;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

@WebServlet(name = "EditAssignmentServlet", value = "/EditAssignmentServlet")
public class EditAssignmentServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("We are very before assigned vars");

        HttpSession session = request.getSession();
        String assignmentName = request.getParameter("editAssignName");
        int possiblePoints = Integer.parseInt(request.getParameter("editAssignScore"));

        String assignDueDate = (String) request.getParameter("editAssignDate");
        StringBuilder sb = new StringBuilder();
        sb.append(assignDueDate);
        sb.deleteCharAt(sb.lastIndexOf("T"));
        sb.insert(10, ' ');

        assignDueDate = sb.toString();

        String editAssignFile;
        System.out.println("Printing out editText radio: " + request.getParameter("editTextRadio"));

        if(request.getParameter("editTextRadio") != "text") {
            editAssignFile = "text";
        }
        else {
            editAssignFile = "file";
        }
        String description = request.getParameter("editAssignDescription");

        String email = (String) session.getAttribute("email");
        int courseID = Integer.parseInt( request.getParameter( "courseIDMain" ));
        int assignID = Integer.parseInt(request.getParameter("assignID"));


        AssignmentDAO assignDAO = new AssignmentDAO();

        try {
            assignDAO.updateAssignmentDB(assignID, assignmentName, courseID, email, description, possiblePoints, assignDueDate, editAssignFile);

            String destPage = "course_main.jsp?courseID=" + courseID;
            response.sendRedirect(destPage);

//            RequestDispatcher dispatcher = request.getRequestDispatcher(destPage);
//            dispatcher.forward(request, response);
            return;

        } catch (SQLException | ClassNotFoundException | ParseException ex) {
            throw new ServletException(ex);
        }
    }
}
