package com.example.ProjectDemo;

import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class NotificationDAO {

    public void addNewAssignmentNotification(int courseID, String assignmentName)
            throws SQLException, ClassNotFoundException, ParseException {

        Connection connection = connectDatabase();
        Statement stmt = connection.createStatement();

        String sqlQuery = "SELECT * FROM assignments WHERE assignmentName LIKE '" + assignmentName +"'";
        ResultSet rs = stmt.executeQuery(sqlQuery);
        if(rs.next()) {
            int assignmentID = rs.getInt("assignmentID");//get the assignment's id
            //get all the students in the class
            sqlQuery = "SELECT * FROM registrations INNER JOIN students ON students.email = registrations.studentEmail" +
                    " WHERE courseID LIKE '"+courseID+"';";
            rs = stmt.executeQuery(sqlQuery);

            while(rs.next()){//create a notification for each student

                String sqlInsert = "INSERT INTO notifications(notificationType, studentID, courseID, assignmentID) VALUES('newAssignment', " +
                        rs.getInt("studentID") + ", " + courseID + ", " + assignmentID + ")";
                PreparedStatement statement = connection.prepareStatement(sqlInsert);
                statement.executeUpdate();

            }
        }

        connection.close();

    }

    public void addGradedNotification(int submissionID, String courseID, String assignmentID) throws SQLException, ClassNotFoundException {

        Connection connection = connectDatabase();
        Statement stmt = connection.createStatement();

        String sqlQuery = "SELECT * FROM studentSubmission WHERE submissionID LIKE '" + submissionID +"'";
        ResultSet rs = stmt.executeQuery(sqlQuery);
        rs.next();

        String sqlInsert = "INSERT INTO notifications(notificationType, studentID, courseID, assignmentID) VALUES('gradedAssignment', " +
                rs.getInt("studentID") + ", " + courseID + ", " + assignmentID + ")";
        PreparedStatement statement = connection.prepareStatement(sqlInsert);
        statement.executeUpdate();
        connection.close();

    }

    public void deleteNotifications(int studentID) throws SQLException, ClassNotFoundException {
        Connection connection = connectDatabase();
        Statement stmt = connection.createStatement();

        String sqlQuery = "SELECT * FROM notifications WHERE studentID LIKE '" + studentID +"' AND flagToDelete = 1";
        ResultSet rs = stmt.executeQuery(sqlQuery);
        if(rs.next()) {
            //delete all of the old notifications that have been flagged for deletion
            String sqlInsert  = "DELETE FROM notifications WHERE studentID = " + studentID + " AND flagToDelete = 1";
            PreparedStatement statement = connection.prepareStatement(sqlInsert);
            statement.executeUpdate();
        }
        connection.close();
    }

    public void updateFlags(int studentID) throws SQLException, ClassNotFoundException {
        Connection connection = connectDatabase();
        Statement stmt = connection.createStatement();

        String sqlQuery = "SELECT * FROM notifications WHERE studentID LIKE '" + studentID +"' AND flagToDelete = 0";
        ResultSet rs = stmt.executeQuery(sqlQuery);
        if(rs.next()){
            String sqlInsert  = "UPDATE notifications SET flagToDelete = 1 WHERE studentID LIKE '" + studentID + "'";
            PreparedStatement statement = connection.prepareStatement(sqlInsert);
            statement.executeUpdate();
        }

        connection.close();

    }

    public Connection connectDatabase() throws SQLException, ClassNotFoundException {
        String jdbcURL = "jdbc:sqlserver://titan.cs.weber.edu:10433;database=LMS_RunTime";
        String dbUser = "LMS_RunTime";
        String dbPassword = "password1!";

        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

        return DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
    }

}
