package com.example.ProjectDemo;
import java.sql.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.regex.Pattern;

public class UserDAO {
    public Users checkLogin(String email, String password) throws SQLException, ClassNotFoundException {
        Connection connection = connectDatabase();

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

    public Users addUserDB(String email, String firstname, String lastname, String password, String birthdate) throws SQLException, ClassNotFoundException, ParseException {
        //DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String encryptedPassword = encrypt(password);
//        Date birthDate = null;
//        try {
//            birthDate = dateFormat.parse(birthdate);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
        Connection connection = connectDatabase();

        String sqlInsert = "INSERT INTO users(email, password, firstName, lastName, birthDate) VALUES('"+email+"','"+encryptedPassword+"','"+firstname+"','"+lastname+"', '"+birthdate+"');";
        PreparedStatement statement = connection.prepareStatement(sqlInsert);
        int result = statement.executeUpdate();

        Users user = null;

        if(result > 0){
            user = new Users();
            user.setFirstname(firstname);
            user.setLastname(lastname);
            user.setEmail(email);
            user.setBirthdate(birthdate);
        }

        connection.close();

        return user;
    }

    public static String encrypt(String encryptMe) {
        try {

            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(encryptMe.getBytes());

            BigInteger no = new BigInteger(1, messageDigest);

            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public Connection connectDatabase() throws SQLException, ClassNotFoundException {
        String jdbcURL = "jdbc:mysql://localhost:3306/project";
        String dbUser = "root";
        String dbPassword = "z41ub8143";

        Class.forName("com.mysql.jdbc.Driver");

        return DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
    }

    public boolean checkAge(String birthDate) {//check if user is 18+ years old

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");//format for input
        Date dateCheck = null;//get date using above format
        try {
            dateCheck = dateFormat.parse(birthDate);//store string as a date
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (dateCheck != null) {//if the above was successful
            LocalDate birth = dateCheck.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();//convert to LocalDate
            LocalDate today = LocalDate.now();//get current date
            Period difference = Period.between(birth, today);//get difference between dates
            int years = difference.getYears();//get years between dates
            return years >= 18;//return true if user is older than 18
        }

        return false;//return false if age couldn't be verified

    }

    public boolean checkEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z" + "A-Z]{2,7}$";

        Pattern pattern = Pattern.compile(emailRegex);

        if(email == null){
            return false;
        }

        return pattern.matcher(email).matches();
    }
}
