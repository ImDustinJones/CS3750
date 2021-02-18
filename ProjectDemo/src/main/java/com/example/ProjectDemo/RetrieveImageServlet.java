package com.example.ProjectDemo;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Base64;
import javax.servlet.annotation.WebServlet;


@WebServlet(name = "RetrieveImageServlet", value = "/RetrieveImageServlet")
public class RetrieveImageServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public RetrieveImageServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");

        try {
            Connection connection = connectDatabase();
            String sql = "SELECT * FROM students WHERE email = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, email);
            ResultSet result = statement.executeQuery();

            if (result.next()) {//student
                Blob blob = result.getBlob("image");

                if(blob == null || blob.equals("0x")){//set default
                    sql = "SELECT * FROM students WHERE email = ?";
                    statement = connection.prepareStatement(sql);
                    statement.setString(1, "default@default.com");
                    result = statement.executeQuery();
                    result.next();
                    blob = result.getBlob("image");
                }

                InputStream inputStream = blob.getBinaryStream();
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                byte[] buffer = new byte[4096];
                int bytesRead = -1;

                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }

                byte[] imageBytes = outputStream.toByteArray();
                String base64Image = Base64.getEncoder().encodeToString(imageBytes);


                inputStream.close();
                outputStream.close();

                request.setAttribute("PFP", base64Image);

                connection.close();
            }
            else{//instructor
                sql = "SELECT * FROM instructors WHERE email = ?";
                statement = connection.prepareStatement(sql);
                statement.setString(1, email);
                result = statement.executeQuery();

                Blob blob = result.getBlob("image");

                if(blob == null || blob.equals("0x")){//set default
                    sql = "SELECT * FROM students WHERE email = ?";
                    statement = connection.prepareStatement(sql);
                    statement.setString(1, "default@default.com");
                    result = statement.executeQuery();
                    result.next();
                    blob = result.getBlob("image");
                }

                InputStream inputStream = blob.getBinaryStream();
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                byte[] buffer = new byte[4096];
                int bytesRead = -1;

                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }

                byte[] imageBytes = outputStream.toByteArray();
                String base64Image = Base64.getEncoder().encodeToString(imageBytes);


                inputStream.close();
                outputStream.close();

                request.setAttribute("PFP", base64Image);

                connection.close();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
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
