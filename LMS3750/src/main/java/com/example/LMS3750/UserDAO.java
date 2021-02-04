package com.example.ProjectDemo;
import java.sql.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UserDAO {
    public Users checkLogin(String email, String password) throws SQLException, ClassNotFoundException {
        String jdbcURL = "jdbc:mysql://localhost:3306/cs3750assignment1";
        String dbUser = "root";
        String dbPassword = "z41ub8143";

        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
        String sql = "SELECT * FROM users WHERE email = ? and password = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, email);

        String encryptedPassword = encrypt(password);
        statement.setString(2, encryptedPassword);

        ResultSet result = statement.executeQuery();

        Users user = null;

        if (result.next()) {
            user = new Users();
            user.setFirstname(result.getString("firstName"));
            user.setEmail(email);
        }

        connection.close();

        return user;
    }

    public static String encrypt(String encryptMe) {
        try {
            MessageDigest messageDigest;
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(encryptMe.getBytes());
            String stringHash = new String(messageDigest.digest());
            return stringHash;
            //Returns null if it is unable to encrypt
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
