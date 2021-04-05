package com.example.ProjectDemo;

import org.junit.jupiter.api.Test;

import java.sql.*;
import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.*;

class AssignmentDAOTest {

    @Test
    void addAssignmentDB() throws ClassNotFoundException, SQLException, ParseException {
        //Setup Database Connection
        String jdbcURL = "jdbc:sqlserver://titan.cs.weber.edu:10433;database=LMS_RunTime";
        String dbUser = "LMS_RunTime";
        String dbPassword = "password1!";
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        Connection connection = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);

        //Count the current courses in the system
        String query = "SELECT COUNT(assignmentID) as count FROM assignments";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();
        int assignmentCount = 0;
        if(resultSet.next()) {
            assignmentCount = resultSet.getInt("count");
        }
        int firstCount = assignmentCount;
        connection.close();
        Connection connection2 = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);

        //Insert New Course Into System
        String assignmentName = "AssignmentTest";
        int courseID = 1008;
        String instructorEmail = "test42@test.com";
        String instructorLastName = "test";
        String assignmentDescription = "testing assignment test";
        int points = 10;
        String dueDate = "2021-03-30 20:58:00.000";
        String submissionType = "text";
        AssignmentDAO.addAssignmentDB(assignmentName, courseID, instructorEmail, instructorLastName, assignmentDescription, points, dueDate, submissionType);
        Connection connection3 = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);

        //Count the current courses in the system again
        String queryCount = "SELECT COUNT(assignmentID) as count FROM assignments";
        PreparedStatement statement2 = connection3.prepareStatement(queryCount);
        ResultSet resultSetCount = statement2.executeQuery();
        int assignmentCount2 = 0;
        if(resultSetCount.next()){
            assignmentCount2 = resultSetCount.getInt("count");
        }
        int secondCount = assignmentCount2;

        //Compare the counts to verify one is only one value larger
        if(firstCount != secondCount-1){
            fail("Database Values were not correct. The first count was "+firstCount+" and the second count was "+secondCount);
        }

        connection3.close();
        Connection connection4 = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
        //Remove the newest course
        String queryDelete = "DELETE FROM assignments WHERE assignmentName = 'AssignmentTest';";
        PreparedStatement statementDelete = connection4.prepareStatement(queryDelete);
        statementDelete.executeUpdate();
        connection4.close();
    }
}