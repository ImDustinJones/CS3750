package com.example.ProjectDemo;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.*;
import java.text.ParseException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.swing.*;

@WebServlet(value = "/SubmitGradeServlet", name = "SubmitGradeServlet")

public class SubmitGradeServlet extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

    }
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();

        // get the sub id of the submission currently
        //String submissionID = (String) session.getAttribute("SubID");
        int submissionID = Integer.parseInt(request.getParameter("studentSubID"));
        String courseID = (String) session.getAttribute("courseID");
        String assignmentID = (String) session.getAttribute("assignmentID");
        //System.out.println("supposed to be graded score: " + request.getParameter("gradePointsBox" + submissionID));
        String gradedScore = request.getParameter("gradePointsBox" + submissionID);

//        System.out.println("gs: " + gradedScore);
//        System.out.println("sID: " + submissionID);
//        System.out.println("Parse ints");
//        System.out.println("p gs: " + Integer.parseInt(gradedScore));
//        System.out.println("p sID: " + Integer.parseInt(submissionID));

        try {
            Connection connection = connectDatabase();

            String sqlInsert;
            sqlInsert = "UPDATE studentSubmission SET grade = "+ Integer.parseInt(gradedScore) + " WHERE submissionID = " + submissionID + "";
            PreparedStatement statement = connection.prepareStatement(sqlInsert);
            statement.execute();

            //create graded notification
            NotificationDAO notificationDAO = new NotificationDAO();
            notificationDAO.addGradedNotification(submissionID, courseID, assignmentID);

            connection.close();

            // ?assignmentID=13&courseID=1011
            response.sendRedirect("assignment_main.jsp?courseID="+courseID + "&assignmentID="+assignmentID);

        } catch (ClassNotFoundException  | SQLException e) {
            e.printStackTrace();
        }
    }
    public Connection connectDatabase() throws SQLException, ClassNotFoundException {
        String jdbcURL = "jdbc:sqlserver://titan.cs.weber.edu:10433;database=LMS_RunTime";
        String dbUser = "LMS_RunTime";
        String dbPassword = "password1!";

        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

        return DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
    }
}

