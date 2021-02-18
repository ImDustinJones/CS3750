package com.example.ProjectDemo;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.swing.*;

@WebServlet(value = "/imageUploadServlet", name = "imageUploadServlet")
@MultipartConfig(maxFileSize = 16177215)    // upload file's size up to 16MB

public class imageUploadServlet extends HttpServlet{

    public imageUploadServlet (){ super();}
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

    }
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        // gets values of text fields
        String email = request.getParameter("email");
        InputStream inputStream = null; // input stream of the upload file

        // obtains the upload file part in this multipart request
        Part filePart = request.getPart("profilePic");
        if (filePart != null) {
            // prints out some information for debugging
            System.out.println(filePart.getName());
            System.out.println(filePart.getSize());
            System.out.println(filePart.getContentType());

            // obtains input stream of the upload file
            inputStream = filePart.getInputStream();
        }

        Connection conn = null; // connection to the database
        String message = null;  // message will be sent back to client
        try {
            // connects to the database
            Connection connection = connectDatabase();
            String sql = "SELECT * FROM students WHERE email = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, email);
            ResultSet result = statement.executeQuery();

            //Student
            if (result.next()) {
                String sqlInsert = "UPDATE  students SET image = ? WHERE email = ?";
                PreparedStatement statementInsert = connection.prepareStatement(sqlInsert);
                statementInsert.setString(2, email);
                if (inputStream != null) {
                    // fetches input stream of the upload file for the blob column
                    statementInsert.setBlob(1, inputStream);
                }
                int row = statementInsert.executeUpdate();
                if (row > 0) {
                    JOptionPane.showMessageDialog(null, "File uploaded and saved into database");
                }
                connection.close();
            }
            //Instructors
            else{
                String sqlInsert = "UPDATE  instructors SET image = ? WHERE email = ?";
                PreparedStatement statementInsert = connection.prepareStatement(sqlInsert);
                statementInsert.setString(2, email);
                if (inputStream != null) {
                    // fetches input stream of the upload file for the blob column
                    statementInsert.setBlob(1, inputStream);
                }
                int row = statementInsert.executeUpdate();
                if (row > 0) {
                    JOptionPane.showMessageDialog(null, "File uploaded and saved into database");
                }
                connection.close();
            }

        } catch (SQLException | ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "The error was: "+ex);
            ex.printStackTrace();
        } finally {
            // forwards to the profile page may need to update it to the correct jsp file
            getServletContext().getRequestDispatcher("/edit_profile.jsp").forward(request, response);
        }
    }

        public Connection connectDatabase() throws SQLException, ClassNotFoundException {
            String jdbcURL = "jdbc:sqlserver://titan.cs.weber.edu:10433;database=LMS_RunTime";
            String dbUser = "LMS_RunTime";
            String dbPassword = "password1!";

            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            return DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
        }


    }
