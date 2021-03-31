package com.example.ProjectDemo;

import org.junit.jupiter.api.Test;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;

class CoursesDAOTest {

    @Test
    void addCoursesDB() throws ClassNotFoundException, SQLException {
        //Setup Database Connection
        String jdbcURL = "jdbc:sqlserver://titan.cs.weber.edu:10433;database=LMS_RunTime";
        String dbUser = "LMS_RunTime";
        String dbPassword = "password1!";
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        Connection connection = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);

        //Count the current courses in the system
        String query = "SELECT COUNT(courseID) as count FROM courseList";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();
        int courseCount = 0;
        if(resultSet.next()) {
            courseCount = resultSet.getInt("count");
        }
        int firstCount = courseCount;
        connection.close();
        Connection connection2 = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);

        //Insert New Course Into System
        String courseNumber = "6789";
        String courseName = "Course Test";
        String departmentCode = "MATH";
        String instructorEmail = "dj@test.com";
        String instructorLastName = "Jones";
        String courseDescription = "testing course";
        String creditHours = "4";
        String startTime = "11:59:00";
        String endTime = "12:59:00";
        String studentCapacity = "10";
        String monday = "1";
        String tuesday = "0";
        String wednesday = "1";
        String thursday = "0";
        String friday = "0";
        String sqlInsert = "INSERT INTO courseList(courseNumber, courseName, departmentCode, instructorEmail, instructorLastName, courseDescription, creditHours, startTime, endTime, studentCapacity, monday, tuesday, wednesday, thursday, friday) VALUES('"+courseNumber+"','"+courseName+"','"+departmentCode+"','"+instructorEmail+"','"+instructorLastName+"','"+courseDescription+"','"+creditHours+"','"+startTime+"','"+endTime+"','"+studentCapacity+"','"+monday+"','"+tuesday+"','"+wednesday+"','"+thursday+"','"+friday+"');";
        PreparedStatement statementInsert = connection2.prepareStatement(sqlInsert);
        int result = statementInsert.executeUpdate();
        connection2.close();
        Connection connection3 = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);

        //Count the current courses in the system again
        String queryCount = "SELECT COUNT(courseID) as count FROM courseList";
        PreparedStatement statement2 = connection3.prepareStatement(queryCount);
        ResultSet resultSetCount = statement2.executeQuery();
        int courseCount2 = 0;
        if(resultSetCount.next()){
            courseCount2 = resultSetCount.getInt("count");
        }
        int secondCount = courseCount2;

        //Compare the counts to verify one is only one value larger
        if(firstCount != secondCount-1){
            fail("Database Values were not correct. The first count was "+firstCount+" and the second count was "+secondCount);
        }

        connection3.close();
        Connection connection4 = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
        //Remove the newest course
        String queryDelete = "DELETE FROM courseList WHERE courseNumber = '6789';";
        PreparedStatement statementDelete = connection4.prepareStatement(queryDelete);
        statementDelete.executeUpdate();
        connection4.close();

    }
}