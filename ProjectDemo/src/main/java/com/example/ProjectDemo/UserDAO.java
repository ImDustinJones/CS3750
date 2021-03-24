package com.example.ProjectDemo;
import java.math.BigInteger;
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

        String sql = "SELECT * FROM students WHERE email = ? and password = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, email);

        String encryptedPassword = encrypt(password);
        statement.setString(2, encryptedPassword);

        ResultSet result = statement.executeQuery();

        Users user = null;

        if (result.next()) { // sets the ${} variables and can set them before we go to profile
            user = new Users();
            user.setUserId(result.getInt("studentID"));
            user.setFirstname(result.getString("firstName"));
            user.setLastname(result.getString("lastName"));

            user.setBio(result.getString("bio"));
            user.setAddress(result.getString("address"));
            user.setCity(result.getString("city"));
            user.setState(result.getString("state"));
            user.setZip(result.getString("zip"));
            user.setPhonenumber(result.getString("phoneNumber"));
            user.setLink1(result.getString("link1"));
            user.setLink2(result.getString("link2"));
            user.setLink3(result.getString("link3"));

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

        String sqlInsert = "INSERT INTO students(email, password, firstName, lastName, birthDate) VALUES('"+email+"','"+encryptedPassword+"','"+firstname+"','"+lastname+"','"+birthdate+"');";
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

    public Users updateUser(String em, String fn, String ln, String bio,
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
        sqlInsert = "UPDATE students SET firstName = '"+fn+"', lastName = '"+ln+"', bio = '"+bio+"', address = '"+ad+"', city = '"+city+"', [state] = '"+state+"', zip = '"+zip+"', phoneNumber = '"+phonenumber+"', link1 = '" + link1 + "', link2 = '" + link2 + "', link3 = '" + link3 + "' WHERE email = '"+em+"';";
        PreparedStatement statement = connection.prepareStatement(sqlInsert);
        int result = statement.executeUpdate();

        Users user = null;

        if(result > 0){
            user = new Users();
            user.setFirstname(fn);
            user.setLastname(ln);
            user.setBio(bio);

            user.setAddress(ad);
            user.setCity(city);
            user.setState(state);
            user.setZip(zip);
            user.setPhonenumber(phonenumber);
            user.setLink1(link1);
            user.setLink2(link2);
            user.setLink3(link3);
        }

        connection.close();

        return user;
    }

    public int getCredits(String studentEmail) throws SQLException, ClassNotFoundException, ParseException {
        Connection connection = connectDatabase();
        Statement stmt = connection.createStatement();


        String sqlQuery = "SELECT creditHours FROM courseList FULL OUTER JOIN registrations ON courseList.courseID = registrations.courseID WHERE studentEmail = '"+studentEmail+"';";
        ResultSet rs = stmt.executeQuery(sqlQuery);

        int totalCredits = 0;

        while(rs.next()){
            totalCredits += rs.getInt("creditHours");

        }
        rs.close();


        return totalCredits;

    }


    public int getRemainingTuition(int studentID) throws SQLException, ClassNotFoundException {
        Connection connection = connectDatabase();
        Statement stmt = connection.createStatement();

        String sqlQuery = "SELECT * FROM students WHERE studentID = '"+studentID+"';";
        ResultSet rs = stmt.executeQuery(sqlQuery);

        int remainingAmount = 0;

        while(rs.next()) {
            remainingAmount = rs.getInt("remainingTuition");
        }

        rs.close();

        return remainingAmount;
    }

    public void updatePayTuition(int amount, int studentid) throws SQLException, ClassNotFoundException {
        Connection connection = connectDatabase();
        Statement stmt = connection.createStatement();

        System.out.println("student id is: " + studentid);
        System.out.println("amount is : " + amount);

        String sqlQuery = "SELECT * FROM students WHERE studentID = '"+studentid+"';";
        ResultSet rs = stmt.executeQuery(sqlQuery);

        int updatedTuition = 0;

        while(rs.next()) {
            System.out.println(rs.getString("remainingTuition"));
            updatedTuition = rs.getInt("remainingTuition");
        }

        System.out.println(updatedTuition);

        rs.close();
        int newRemainingPayment = updatedTuition - amount;

        System.out.println(newRemainingPayment);

        String sqlInsert = "UPDATE students SET remainingTuition = '"+newRemainingPayment+"', payments = '"+amount+"' WHERE studentID = '"+studentid+"';";
        PreparedStatement statement = connection.prepareStatement(sqlInsert);
        statement.executeUpdate();


    }

    public String getLatestPayment(int studentID) throws SQLException, ClassNotFoundException {
        Connection connection = connectDatabase();
        Statement stmt = connection.createStatement();

        String sqlQuery = "SELECT * FROM students WHERE studentID = '"+studentID+"';";
        ResultSet rs = stmt.executeQuery(sqlQuery);

        String payment = "";

        while(rs.next()) {
            payment = rs.getString("payments");
        }

        rs.close();

        return payment;
    }


}
