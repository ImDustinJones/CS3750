package com.example.ProjectDemo;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class WrongPasswordInstructorLogInTest {

    @Test //should not be able to log in, using incorrect password
    void checkLoginInstructor() throws SQLException, ClassNotFoundException {

        String email = "instructortesting@mail.com";
        String password = "notmypassword";
        try {
            if(InstructorDAO.checkLoginInstructor(email, password) !=null) {
                fail("Instructor was able to log in with wrong password");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            fail("DB Error");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            fail("Error: "+e);
        }

    }
}