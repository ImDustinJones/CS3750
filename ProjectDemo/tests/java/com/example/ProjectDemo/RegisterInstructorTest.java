package com.example.ProjectDemo;

import org.junit.jupiter.api.Test;

import java.sql.*;
import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.*;

class RegisterInstructorTest {

    @Test
    void addInstructorDB() throws ClassNotFoundException, SQLException, ParseException {

        //Setup Database Connection
        String jdbcURL = "jdbc:sqlserver://titan.cs.weber.edu:10433;database=LMS_RunTime";
        String dbUser = "LMS_RunTime";
        String dbPassword = "password1!";
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        Connection connection = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);

        //Count the current instructors in the system
        String query = "SELECT COUNT(instructorID) as count FROM instructors";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();
        int instructorCount = 0;
        resultSet.next();
        instructorCount = resultSet.getInt("count");
        int firstCount = instructorCount;
        connection.close();

        //insert to the db using the DAO
        InstructorDAO dao = new InstructorDAO();
        dao.addInstructorDB("asdasdasd@asd.com", "First", "Last", "password", "1900-10-10");

        Connection connection3 = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);

        //Count the current instructors in the system again
        String queryCount = "SELECT COUNT(instructorID) as count FROM instructors";
        PreparedStatement statement2 = connection3.prepareStatement(queryCount);
        ResultSet resultSetCount = statement2.executeQuery();
        int instructorCount2 = 0;
        resultSetCount.next();
        instructorCount2 = resultSetCount.getInt("count");
        int secondCount = instructorCount2;

        //Compare the counts to verify there is now one more instructor
        if(firstCount != secondCount-1){
            fail("Database Values were not correct. The first count was "+firstCount+" and the second count was "+secondCount);
        }
        connection3.close();

        Connection connection4 = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
        //Remove the newest instructor
        String queryDelete = "DELETE FROM instructors WHERE email = 'asdasdasd@asd.com';";
        PreparedStatement statementDelete = connection4.prepareStatement(queryDelete);
        statementDelete.executeUpdate();
        connection4.close();

    }

}