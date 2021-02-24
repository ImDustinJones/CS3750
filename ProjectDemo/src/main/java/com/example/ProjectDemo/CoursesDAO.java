package com.example.ProjectDemo;


import java.sql.*;
import java.text.ParseException;



public class CoursesDAO {

    public void addCoursesDB(String courseNumber, String courseName, String departmentCode, String instructorEmail,
                                String instructorLastName, String courseDescription, int creditHours, String startTime,
                                String endTime, int studentCapacity, int monday, int tuesday, int wednesday,
                                int thursday, int friday) throws SQLException, ClassNotFoundException, ParseException {

        Connection connection = connectDatabase();


        String sqlInsert = "INSERT INTO courseList(courseNumber, courseName, departmentCode, instructorEmail, instructorLastName, courseDescription, creditHours, startTime, endTime, studentCapacity, monday, tuesday, wednesday, thursday, friday) VALUES('"+courseNumber+"','"+courseName+"','"+departmentCode+"','"+instructorEmail+"','"+instructorLastName+"','"+courseDescription+"','"+creditHours+"','"+startTime+"','"+endTime+"','"+studentCapacity+"','"+monday+"','"+tuesday+"','"+wednesday+"','"+thursday+"','"+friday+"');";
        PreparedStatement statement = connection.prepareStatement(sqlInsert);
        int result = statement.executeUpdate();

        Courses course;

        if(result > 0){
            course = new Courses();
            course.setCourseName(courseName);
            course.setDepartmentCode(departmentCode);
            course.setInstructorEmail(instructorEmail);
            course.setInstructorLastName(instructorLastName);
            course.setCourseDescription(courseDescription);
            course.setCreditHours(creditHours);
            course.setStartTime(startTime);
            course.setEndTime(endTime);
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
