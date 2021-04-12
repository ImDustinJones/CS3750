package com.example.ProjectDemo;

import org.junit.jupiter.api.Test;

import java.sql.*;
import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.*;

class UserDAOJUTest {

    @Test
    void checkLogin() {
        String email = "jjmonk@gmail.com";
        String password = "password";
        try {
            if(UserDAO.checkLogin(email, password) == null) {
                fail("User credentials is null");
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
            fail("DataBase Error");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            fail("Error: " + ex);
        }
    }

    @Test
    void addUserDB() throws ClassNotFoundException, SQLException, ParseException {
        String email = "AddStudentJUTest@test.com";
        String firstname = "JUTestNewStudent";
        String lastname = "LastName";
        String password = "JUnitTest";
        String birthdate = "1998-01-01";

        // DB Connection
        String jdbcURL = "jdbc:sqlserver://titan.cs.weber.edu:10433;database=LMS_RunTime";
        String dbUser = "LMS_RunTime";
        String dbPassword = "password1!";
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        Connection DBConnection = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);

        // First test to make sure it is not null
        try {
            if(UserDAO.addUserDB(email, firstname, lastname, password, birthdate) == null) {
                fail("User creation is null");
            }
            else
            {
                // Get the amount of Users in the database
                String query = "SELECT COUNT(studentID) AS studentCount FROM students";
                PreparedStatement PStatement = DBConnection.prepareStatement(query);
                ResultSet ResSet = PStatement.executeQuery();
                int StudentInitialCountAmount = 0;
                ResSet.next();
                StudentInitialCountAmount = ResSet.getInt("studentCount");

                DBConnection.close();

                // Add the student to the database
                UserDAO.addUserDB(email, firstname, lastname, password, birthdate);

                Connection ConnectionAfterNewStudent = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);

                //Count the current instructors in the system again
                String queryCount = "SELECT COUNT(studentID) AS studentCount FROM students";
                PreparedStatement Stmt = ConnectionAfterNewStudent.prepareStatement(queryCount);
                ResultSet ResSetCount = Stmt.executeQuery();
                int StudentCountAfterInsert = 0;
                ResSetCount.next();
                StudentCountAfterInsert = ResSetCount.getInt("studentCount");


                //Compare the counts to verify there is now one more instructor
                if(StudentInitialCountAmount != StudentCountAfterInsert - 1){
                    fail("Database values were not correct. The first count before insert "+ StudentInitialCountAmount +" and the second count after test count "+ StudentCountAfterInsert);
                }
                ConnectionAfterNewStudent.close();

                Connection DBConnectionDEL = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);

                // Delete the student who was just added
                String queryDelete = "DELETE FROM students WHERE email = '" + email + "'";
                PreparedStatement SmtDelete = DBConnectionDEL.prepareStatement(queryDelete);
                SmtDelete.executeUpdate();
                DBConnectionDEL.close();

            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
            fail("DataBase Error");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            fail("Error: " + ex);
        }
    }
}