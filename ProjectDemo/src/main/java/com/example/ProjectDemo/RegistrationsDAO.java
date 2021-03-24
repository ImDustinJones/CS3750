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


    public void unRegisterStudent(String studentEmail, String courseID) throws SQLException, ClassNotFoundException, ParseException {
        Connection connection = connectDatabase();
        String sqlInsert = "DELETE FROM registrations WHERE studentEmail = '"+studentEmail+"' AND courseID = '"+Integer.parseInt(courseID)+"';";
        PreparedStatement statement = connection.prepareStatement(sqlInsert);
        statement.execute();
        connection.close();
    }


    public Connection connectDatabase() throws SQLException, ClassNotFoundException {
        String jdbcURL = "jdbc:sqlserver://titan.cs.weber.edu:10433;database=LMS_RunTime";
        String dbUser = "LMS_RunTime";
        String dbPassword = "password1!";

        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

        return DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
    }

    public void updateTuition(String studentEmail) throws SQLException, ClassNotFoundException {
        Connection connection = connectDatabase();
        Statement stmt = connection.createStatement();


        String sqlQuery = "SELECT creditHours FROM courseList FULL OUTER JOIN registrations ON courseList.courseID = registrations.courseID WHERE studentEmail = '"+studentEmail+"';";
        ResultSet rs = stmt.executeQuery(sqlQuery);

        int totalCredits = 0;

        while(rs.next()){
            totalCredits += rs.getInt("creditHours");

        }
        rs.close();

        int totalAmount = totalCredits*100;

        String sqlInsert = "UPDATE students SET totalTuition = '"+totalAmount+"', remainingTuition = '"+totalAmount+"' WHERE email = '"+studentEmail+"';";
        PreparedStatement statement = connection.prepareStatement(sqlInsert);
        statement.executeUpdate();

    }
}
