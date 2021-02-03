import java.util.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

public class EncryptFunctions {
	public static void main(String[] args) {
		//Getting the String input for testing. You should be able to just call the function with any string
		//So the next three lines aren't needed. Mostly the functions are needed. I also provided some syntax on how to use them too.
		Scanner scanner = new Scanner(System.in);  // Create a Scanner object
	    System.out.println("Enter the password you would like to encrypt or decrypt? ");
	    String passwordInput = scanner.nextLine();
	    
	    //user from my database to run the passwordCheck Function
	    String user = "testtest";
	    
	    //Calling function to output it into console. You should be able to call this function as a string in any situation
	    System.out.println(encrypt(passwordInput));
	    
	    //Testing to see if the encrypted passwords match
	    try {
			System.out.println("Password Verification was: "+passwordCheck(passwordInput, user));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	//Function Encrypts the string passed in into the SHA-256 format
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
	
	public static Boolean passwordCheck(String password, String username) throws ClassNotFoundException {
		 try {
			 //Setup my personal database for testing
			 Class.forName("com.mysql.jdbc.Driver");
			 Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/usersdb", "root", "admin1");
			 Statement st=conn.createStatement();
			 //Runs select statement to retrieve the account the username applies to
			 ResultSet rs=st.executeQuery("SELECT * FROM users WHERE userName='"+username+"'");
			 rs.next();
			 //Checks if the encypted password entered matches the encrypted password within sql
			if(encrypt(password).equals(rs.getString("userPassword"))){
				return true;
			}
			else {
				return false;
			}
			//Returns null if there is any error during this process
		 	} catch (SQLException e) {
		 		e.printStackTrace();
		 		System.out.print(e);
		 		return null;
		 	}
		
	}

}
