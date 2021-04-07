package com.example.ProjectDemo;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class WrongPasswordStudentLoginTest {

    @Test
    void checkLogin() {

        String email = "studenttesting@mail.com";
        String password = "notmypassword";
        try {
            if(UserDAO.checkLogin(email, password) !=null) {
                fail("Student was able to log in with wrong password");
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