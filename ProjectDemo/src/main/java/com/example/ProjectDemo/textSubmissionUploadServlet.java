package com.example.ProjectDemo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.*;
import java.text.ParseException;

@WebServlet(name = "textSubmissionServlet", value = "/text-assignment-submission")
public class textSubmissionUploadServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {



    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AssignmentDAO assignmentDao = new AssignmentDAO();
        HttpSession session = request.getSession();

        String textSubmission = request.getParameter("textSubmission");
        String userTypeVariable = (String) session.getAttribute("userType");

        String studentsID = request.getParameter("studentIDMain");
        int studentIDD = Integer.parseInt(request.getParameter("studentIDMain"));
        int assignmentsID= Integer.parseInt((String)session.getAttribute("assignmentID"));
        int coursesID = Integer.parseInt((String)session.getAttribute("courseID"));

        System.out.println(studentsID);

        if(studentsID.equals("")){
            studentsID = (String)session.getAttribute("studentID");
            System.out.println(studentsID);
        }
        request.setAttribute("submitResult", "true");
        try {

                assignmentDao.submitTextAssignment(assignmentsID, studentIDD, "T", textSubmission);

                response.sendRedirect("assignment_main.jsp?assignmentID="+assignmentsID+"&courseID="+coursesID);


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
