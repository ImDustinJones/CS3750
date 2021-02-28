package com.example.ProjectDemo;

import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class RegistrationsDAO {
    public void registerStudent(String studentEmail, String courseID) throws SQLException, ClassNotFoundException, ParseException {

        Connection connection = connectDatabase();


        String sqlInsert = "INSERT INTO registrations(studentEmail, courseID) VALUES('"+studentEmail+"','"+courseID+"');";
        PreparedStatement statement = connection.prepareStatement(sqlInsert);
        int result = statement.executeUpdate();

        Regestrations reg;

        if(result > 0){
            reg = new Regestrations();
            reg.setCourseID(Integer.parseInt(courseID));
            reg.setStudentEmail(studentEmail);
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
