package com.example.ProjectDemo;


import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;


public class CoursesDAO {

    public static void addCoursesDB(String courseNumber, String courseName, String departmentCode, String instructorEmail,
                                    String instructorLastName, String courseDescription, int creditHours, String startTime,
                                    String endTime, int studentCapacity, int monday, int tuesday, int wednesday,
                                    int thursday, int friday) throws SQLException, ClassNotFoundException, ParseException {

        Connection connection = connectDatabase();


        String sqlInsert = "INSERT INTO courseList(courseNumber, courseName, departmentCode, instructorEmail, instructorLastName, courseDescription, creditHours, startTime, endTime, studentCapacity, monday, tuesday, wednesday, thursday, friday) VALUES('"+courseNumber+"','"+courseName+"','"+departmentCode+"','"+instructorEmail+"','"+instructorLastName+"','"+courseDescription+"','"+creditHours+"','"+startTime+"','"+endTime+"','"+studentCapacity+"','"+monday+"','"+tuesday+"','"+wednesday+"','"+thursday+"','"+friday+"');";
        PreparedStatement statement = connection.prepareStatement(sqlInsert);
        int result = statement.executeUpdate();

        Courses course;

        if(result > 0){
            course = new Courses();
            course.setCourseName(courseName);
            course.setDepartmentCode(departmentCode);
            course.setInstructorEmail(instructorEmail);
            course.setInstructorLastName(instructorLastName);
            course.setCourseDescription(courseDescription);
            course.setCreditHours(creditHours);
            course.setStartTime(startTime);
            course.setEndTime(endTime);
        }

        connection.close();
    }
    public static Connection connectDatabase() throws SQLException, ClassNotFoundException {
        String jdbcURL = "jdbc:sqlserver://titan.cs.weber.edu:10433;database=LMS_RunTime";
        String dbUser = "LMS_RunTime";
        String dbPassword = "password1!";

        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

        return DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
    }

    public List<Courses> getCourseList(String email) throws SQLException, ClassNotFoundException {
        Connection connection = connectDatabase();
        Statement stmt = connection.createStatement();


        String sqlQuery = "SELECT * FROM courseList WHERE instructorEmail LIKE '"+email+"';";
        ResultSet rs = stmt.executeQuery(sqlQuery);

        //Courses[] courseList = null;
        //int count = 0;

        List<Courses>courseList = new ArrayList<Courses>();

        while(rs.next()){
            //Retrieve by column name
            Courses course;
            course = new Courses();
            course.setCourseID(rs.getInt("courseID"));
            course.setCourseNumber(rs.getInt("courseNumber"));
            course.setCourseName(rs.getString("courseName"));
            course.setDepartmentCode(rs.getString("departmentCode"));
            course.setInstructorEmail(rs.getString("instructorEmail"));
            course.setInstructorLastName(rs.getString("instructorLastName"));
            course.setCourseDescription(rs.getString("courseDescription"));
            course.setCreditHours(rs.getInt("creditHours"));
            course.setStartTime(rs.getString("startTime"));
            course.setEndTime(rs.getString("endTime"));
            course.setMonday(rs.getInt("monday"));
            course.setTuesday(rs.getInt("tuesday"));
            course.setWednesday(rs.getInt("wednesday"));
            course.setThursday(rs.getInt("thursday"));
            course.setFriday(rs.getInt("friday"));

            course.setStudentCapacity(rs.getInt("studentCapacity"));

            assert false;
            courseList.add(course);
            //courseList[count] = course;
            //count++;

        }
        rs.close();

        return courseList;
    }




    public List<Courses> getStudentCourseList(String email) throws SQLException, ClassNotFoundException {
        Connection connection = connectDatabase();
        Statement stmt = connection.createStatement();

        String sqlQuery = "SELECT courseList.courseID, courseList.courseName, courseList.departmentCode, courseList.courseNumber, courseList.courseDescription, courseList.creditHours, courseList.monday, courseList.tuesday, courseList.wednesday, courseList.thursday, courseList.friday, courseList.startTime, courseList.endTime, courseList.studentCapacity FROM registrations INNER JOIN courseList ON registrations.courseID = courseList.courseID WHERE registrations.studentEmail = '"+email+"';";
        ResultSet rs = stmt.executeQuery(sqlQuery);

        List<Courses>courseList = new ArrayList<Courses>();

        while(rs.next()){
            //Retrieve by column name
            Courses course;
            course = new Courses();
            course.setCourseID(rs.getInt("courseID"));
            course.setCourseNumber(rs.getInt("courseNumber"));
            course.setCourseName(rs.getString("courseName"));
            course.setDepartmentCode(rs.getString("departmentCode"));
            //course.setInstructorEmail(rs.getString("instructorEmail"));
            //course.setInstructorLastName(rs.getString("instructorLastName"));
            course.setCourseDescription(rs.getString("courseDescription"));
            course.setCreditHours(rs.getInt("creditHours"));
            course.setStartTime(rs.getString("startTime"));
            course.setEndTime(rs.getString("endTime"));
            course.setMonday(rs.getInt("monday"));
            course.setTuesday(rs.getInt("tuesday"));
            course.setWednesday(rs.getInt("wednesday"));
            course.setThursday(rs.getInt("thursday"));
            course.setFriday(rs.getInt("friday"));

            course.setStudentCapacity(rs.getInt("studentCapacity"));

            assert false;
            courseList.add(course);
            //courseList[count] = course;
            //count++;

        }

        rs.close();

        return courseList;
    }





    public void updateCoursesDB(String courseNumber, String courseName, String departmentCode, String instructorEmail, String courseDescription,
                                int creditHours, String startTime, String endTime, int studentCapacity, int monday,
                                int tuesday, int wednesday, int thursday, int friday, int courseID)
            throws SQLException, ClassNotFoundException, ParseException {

        Connection connection = connectDatabase();


        String sqlInsert  = "UPDATE courseList SET courseNumber = '"+courseNumber+"', courseName = '"+courseName+"', departmentCode = '"+departmentCode+"', courseDescription = '"+courseDescription+"', creditHours = '"+creditHours+"', startTime = '"+startTime+"', endTime = '"+endTime+"', monday = '"+monday+"', tuesday = '"+tuesday+"', wednesday = '"+wednesday+"', thursday = '"+thursday+"', friday = '"+friday+"', studentCapacity = '"+studentCapacity+"' WHERE courseID = '"+courseID+"' AND instructorEmail = '"+instructorEmail+"';";
        PreparedStatement statement = connection.prepareStatement(sqlInsert);
        statement.executeUpdate();


        connection.close();
    }
}
