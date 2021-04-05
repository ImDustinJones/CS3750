package com.example.ProjectDemo;

import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class InstructorDAOTest {

    @Test
    void checkLoginInstructor() throws SQLException, ClassNotFoundException {
        String email = "dj@test.com";
        String password = "123";
        try {
            if(InstructorDAO.checkLoginInstructor("dj@test.com", "123")==null) {
                fail("instructor was null");
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