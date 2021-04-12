package com.example.ProjectDemo;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.regex.Pattern;

public class InstructorDAO {
    public static Instructors checkLoginInstructor(String email, String password) throws SQLException, ClassNotFoundException {
        Connection connection = connectDatabase();

        String sql = "SELECT * FROM instructors WHERE email = ? and password = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, email);

        String encryptedPassword = encrypt(password);
        statement.setString(2, encryptedPassword);

        ResultSet result = statement.executeQuery();

        Instructors instructor = null;

        if (result.next()) {
            instructor = new Instructors();
            instructor.setFirstname(result.getString("firstName"));
            instructor.setLastname(result.getString("lastName"));
            instructor.setUserId(result.getInt("instructorID"));
            instructor.setBio(result.getString("bio"));
            instructor.setAddress(result.getString("address"));
            instructor.setCity(result.getString("city"));
            instructor.setState(result.getString("state"));
            instructor.setZip(result.getString("zip"));
            instructor.setPhonenumber(result.getString("phoneNumber"));
            instructor.setLink1(result.getString("link1"));
            instructor.setLink2(result.getString("link2"));
            instructor.setLink3(result.getString("link3"));

            instructor.setEmail(email);
        }

        connection.close();

        return instructor;
    }

    public Instructors addInstructorDB(String email, String firstname, String lastname, String password, String birthdate) throws SQLException, ClassNotFoundException, ParseException {
  //      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String encryptedPassword = encrypt(password);
        Connection connection = connectDatabase();

        String sqlInsert = "INSERT INTO instructors(email, password, firstName, lastName, birthDate) VALUES('"+email+"','"+encryptedPassword+"','"+firstname+"','"+lastname+"','"+birthdate+"');";
        PreparedStatement statement = connection.prepareStatement(sqlInsert);
        int result = statement.executeUpdate();

        connection.close();

        Connection connection2 = connectDatabase();

        String sql = "SELECT * FROM instructors WHERE email = '"+email+"' and firstName = '"+firstname+"'";
        PreparedStatement statement2 = connection2.prepareStatement(sql);

        ResultSet result2 = statement2.executeQuery();

        Instructors instructor = null;

        if (result2.next()) { // sets the ${} variables and can set them before we go to profile
            instructor = new Instructors();
            instructor.setUserId(result2.getInt("instructorID"));
            instructor.setFirstname(result2.getString("firstName"));
            instructor.setLastname(result2.getString("lastName"));
            instructor.setEmail(email);
        }

        connection2.close();

        return instructor;
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

    public static Connection connectDatabase() throws SQLException, ClassNotFoundException {
        String jdbcURL = "jdbc:sqlserver://titan.cs.weber.edu:10433;database=LMS_RunTime";
        String dbUser = "LMS_RunTime";
        String dbPassword = "password1!";

        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

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


    public Instructors updateUser(String em, String fn, String ln, String bio,
                            String ad, String city, String state, String zip,
                            String phonenumber, String link1, String link2, String link3) throws SQLException, ClassNotFoundException, ParseException {
        Connection connection = connectDatabase();

        if(!link1.contains("//")){
            link1 = "https://" + link1;
        }
        if(!link2.contains("//")){
            link2 = "https://" + link2;
        }
        if(!link3.contains("//")){
            link3 = "https://" + link3;
        }

        String sqlInsert;
        sqlInsert = "UPDATE instructors SET firstName = '" + fn + "', lastName = '" + ln + "', bio = '" + bio + "', address = '" + ad + "', city = '" + city + "', [state] = '" + state + "', zip = '" + zip + "', phoneNumber = '" + phonenumber + "', link1 = '"+link1+"', link2 = '"+link2+"', link3 = '"+link3+"' WHERE email = '" + em + "';";
        PreparedStatement statement = connection.prepareStatement(sqlInsert);
        int result = statement.executeUpdate();

        Instructors instructor = null;

        if (result > 0) {
            instructor = new Instructors();
            instructor.setFirstname(fn);
            instructor.setLastname(ln);
            instructor.setBio(bio);

            instructor.setAddress(ad);
            instructor.setCity(city);
            instructor.setState(state);
            instructor.setZip(zip);
            instructor.setPhonenumber(phonenumber);
            instructor.setLink1(link1);
            instructor.setLink2(link2);
            instructor.setLink3(link3);
        }

        connection.close();

        return instructor;
    }
}
