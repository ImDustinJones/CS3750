package com.example.ProjectDemo;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.swing.*;

public class imageUploadServlet {
    @WebServlet("/imageUploadServlet")
    @MultipartConfig(maxFileSize = 16177215)    // upload file's size up to 16MB
    public class FileUploadDBServlet extends HttpServlet {


        public Connection connectDatabase() throws SQLException, ClassNotFoundException {
            String jdbcURL = "jdbc:sqlserver://titan.cs.weber.edu:10433;database=LMS_RunTime";
            String dbUser = "LMS_RunTime";
            String dbPassword = "password1!";

            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            return DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
        }

        protected void doPost(HttpServletRequest request,
                              HttpServletResponse response) throws ServletException, IOException {
            // gets values of text fields
            String email = request.getParameter("email");
            String lastName = request.getParameter("lastName");

            InputStream inputStream = null; // input stream of the upload file

            // obtains the upload file part in this multipart request
            Part filePart = request.getPart("photo");
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

                // constructs SQL statement
                String sql = "INSERT INTO contacts (email, photo) values (?, ?)";
                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setString(1, email);

                if (inputStream != null) {
                    // fetches input stream of the upload file for the blob column
                    statement.setBlob(2, inputStream);
                }

                // sends the statement to the database server
                int row = statement.executeUpdate();
                if (row > 0) {
                    JOptionPane.showMessageDialog(null, "File uploaded and saved into database");
                }
            } catch (SQLException | ClassNotFoundException ex) {
                message = "ERROR: " + ex.getMessage();
                ex.printStackTrace();
            } finally {
                if (conn != null) {
                    // closes the database connection
                    try {
                        conn.close();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
                // forwards to the profile page may need to update it to the correct jsp file
                getServletContext().getRequestDispatcher("/Profile.jsp").forward(request, response);
            }
        }
    }
}
