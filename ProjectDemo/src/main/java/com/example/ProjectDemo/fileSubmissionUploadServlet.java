package com.example.ProjectDemo;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.swing.*;

@WebServlet(value = "/fileSubmissionUploadServlet", name = "fileSubmissionUploadServlet")
@MultipartConfig(maxFileSize = 16177215)    // upload file's size up to 16MB

public class fileSubmissionUploadServlet extends HttpServlet{

    public fileSubmissionUploadServlet (){ super();}
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

    }
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        //Setup Session Value for CourseID
        HttpSession session = request.getSession();
        String courseID = (String) session.getAttribute("courseID");
        String assignmentID = (String) session.getAttribute("assignmentID");
        String email = (String) session.getAttribute("email");

        Part filePart = request.getPart("fileSubmission");
        InputStream fileInputStream = null; // input stream of the upload file

        //get the InputStream to store the file somewhere
        fileInputStream = filePart.getInputStream();
        //URL url = getClass().getResource("/main/webapp/FileSubmissions/");
        String url = "C:\\FileSubmissions\\";
       // File fileToSave = new File(this.getServletContext().getRealPath("/FileSubmissions/") + filePart.getSubmittedFileName());
        File fileToSave = new File(url + filePart.getSubmittedFileName());
        Files.copy(fileInputStream, fileToSave.toPath(), StandardCopyOption.REPLACE_EXISTING);;
        //get the URL of the uploaded file
        System.out.println(url);
        url = "FileSubmissions\\" + filePart.getSubmittedFileName();
        session.setAttribute("fileURL", url);

        //Now need to update the entry in the database with this information
        Connection conn = null; // connection to the database
        String message = null;  // message will be sent back to client
        try {
            // connects to the database
            Connection connection = connectDatabase();
            String sql = "SELECT studentSubmission.submissionID FROM studentSubmission JOIN students on students.studentID = studentSubmission.studentID WHERE studentSubmission.assignmentID = ? AND students.email = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, assignmentID);
            statement.setString(2, email);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                //Update the current entry
                String submissionID = result.getString("submissionID");
                String sqlUpdate = "UPDATE  studentSubmission SET fileSubmissionPointer = ? WHERE submissionID = ?";
                PreparedStatement statement2 = connection.prepareStatement(sqlUpdate);
                statement2.setString(1, (String) session.getAttribute("fileURL"));
                statement2.setString(2, submissionID);
                int row = statement2.executeUpdate();
                if (row > 0) {
                    System.out.println("File Submission Path was saved to database");
                }
            }
            else{
                //Insert a new entry
                String students = "Select * From Students WHERE email = ?";
                PreparedStatement statement3 = connection.prepareStatement(students);
                statement3.setString(1, email);
                ResultSet result2 = statement3.executeQuery();
                if(result2.next()){
                    session.setAttribute("studentID", result2.getString("studentID"));
                }
                String sqlInsert = "INSERT INTO studentSubmission(studentID,assignmentID,submissionType,fileSubmissionPointer, turnInTime) "+
                        "Values('"+(String) session.getAttribute("studentID")+"', "+assignmentID+", 'F', '"+(String) session.getAttribute("fileURL")+"', GETDATE())";
                PreparedStatement statementInsert = connection.prepareStatement(sqlInsert);
                int row = statementInsert.executeUpdate();
                if (row > 0) {
                    System.out.println("File Submission Path was saved to database");
                }
            }
            System.out.println(url);
            connection.close();
        } catch (SQLException | ClassNotFoundException ex) {
            //JOptionPane.showMessageDialog(null, "The error was: "+ex);
            ex.printStackTrace();
        } finally {
            // forwards to the profile page may need to update it to the correct jsp file
            response.sendRedirect("course_main.jsp?courseID="+courseID);
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

