package com.example.ProjectDemo;

import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class AssignmentDAO {

    public void addAssignmentDB(String assignmentName, int courseID, String instructorEmail, String instructorLastName,
                                String assignmentDescription, int points, String dueDate, String submissionType)
            throws SQLException, ClassNotFoundException, ParseException {

        Connection connection = connectDatabase();

        String sqlInsert = "INSERT INTO assignments(assignmentName, courseID, instructorEmail, instructorLastName, assignmentDescription, points, dueDate, submissionType) VALUES('"+assignmentName+"','"+courseID+"','"+instructorEmail+"','"+instructorLastName+"','"+assignmentDescription+"','"+points+"','"+dueDate+"','"+submissionType+"');";
        PreparedStatement statement = connection.prepareStatement(sqlInsert);
        int result = statement.executeUpdate();

        Assignments assignment;

        if(result > 0){
            assignment = new Assignments();
            assignment.setAssignmentName(assignmentName);
            assignment.setInstructorEmail(instructorEmail);
            assignment.setInstructorLastName(instructorLastName);
            assignment.setAssignmentDescription(assignmentDescription);
            assignment.setPoints(points);
            assignment.setDueDate(dueDate);
            assignment.setSubmissionType(submissionType);
            assignment.setCourseID(courseID);
        }

        connection.close();
    }
    public Connection connectDatabase() throws SQLException, ClassNotFoundException {
        String jdbcURL = "jdbc:sqlserver://titan.cs.weber.edu:10433;database=LMS_RunTime";
        String dbUser = "LMS_RunTime";
        String dbPassword = "password1!";

        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

        return DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
    }

    public List<Assignments> getAssignmentsList(int courseID) throws SQLException, ClassNotFoundException {
        Connection connection = connectDatabase();
        Statement stmt = connection.createStatement();


        String sqlQuery = "SELECT * FROM assignments WHERE courseID LIKE '"+courseID+"';";
        ResultSet rs = stmt.executeQuery(sqlQuery);


        List<Assignments>assignmentList = new ArrayList<Assignments>();

        while(rs.next()){
            //Retrieve by column name
            Assignments assignment = new Assignments();
            assignment.setAssignmentID(rs.getInt("assignmentID"));
            assignment.setAssignmentName(rs.getString("assignmentName"));
            assignment.setCourseID(rs.getInt("courseID"));
            assignment.setInstructorEmail(rs.getString("instructorEmail"));
            assignment.setInstructorLastName(rs.getString("instructorLastName"));
            assignment.setAssignmentDescription(rs.getString("assignmentDescription"));
            assignment.setPoints(rs.getInt("points"));
            assignment.setDueDate(rs.getString("dueDate"));
            assignment.setSubmissionType(rs.getString("submissionType"));



            assert false;
            assignmentList.add(assignment);

        }
        rs.close();

        return assignmentList;
    }
    //This is just here if we need it
    public List<Assignments> getStudentAssignmentsList(int courseID) throws SQLException, ClassNotFoundException {
        List<Assignments> assignmentList = new ArrayList<Assignments>();

        return assignmentList;
    }



    public void updateAssignmentDB(int assignmentID, String assignmentName, int courseID, String instructorEmail,
                                   String assignmentDescription, int points, String dueDate, String submissionType)
            throws SQLException, ClassNotFoundException, ParseException {

        Connection connection = connectDatabase();

        String sqlInsert  = "UPDATE assignments SET assignmentName = '"+assignmentName+"', assignmentDescription = '"+assignmentDescription+"', points = '"+points+"', dueDate = '"+dueDate+"', submissionType = '"+submissionType+"' WHERE courseID = '"+courseID+"' AND instructorEmail = '"+instructorEmail+"' AND assignmentID = '"+assignmentID+"';";
        PreparedStatement statement = connection.prepareStatement(sqlInsert);
        statement.executeUpdate();

        connection.close();
    }

    public Assignments getAssignment(int courseID, int assignmentID) throws SQLException, ClassNotFoundException {
        Connection connection = connectDatabase();
        Statement stmt = connection.createStatement();


        String sqlQuery = "SELECT * FROM assignments WHERE courseID LIKE '"+courseID+"' AND assignmentID LIKE '"+assignmentID+"';";
        ResultSet rs = stmt.executeQuery(sqlQuery);


        Assignments assignment = new Assignments();

        while(rs.next()){
            //Retrieve by column name
            assignment.setAssignmentID(rs.getInt("assignmentID"));
            assignment.setAssignmentName(rs.getString("assignmentName"));
            assignment.setCourseID(rs.getInt("courseID"));
            assignment.setInstructorEmail(rs.getString("instructorEmail"));
            assignment.setInstructorLastName(rs.getString("instructorLastName"));
            assignment.setAssignmentDescription(rs.getString("assignmentDescription"));
            assignment.setPoints(rs.getInt("points"));
            assignment.setDueDate(rs.getString("dueDate"));
            assignment.setSubmissionType(rs.getString("submissionType"));


        }
        rs.close();

        return assignment;
    }
}
