<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.DriverManager" %>
<%@ page import="java.sql.Statement" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%String courseID = request.getParameter( "courseID" );%>
    <title>Your Course</title>
</head>
<body> <%
                try {
                    String jdbcURL = "jdbc:sqlserver://titan.cs.weber.edu:10433;database=LMS_RunTime";
                    String dbUser = "LMS_RunTime";
                    String dbPassword = "password1!";
                    String email = (String) session.getAttribute("email");
                    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                    Connection connection = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);

                    Statement statement = connection.createStatement();
                    String query = "SELECT registrations.courseID, courseList.courseNumber, courseList.courseName, courseList.departmentCode, courseList.instructorLastName "+
                            "FROM registrations INNER JOIN courseList ON registrations.courseID = courseList.courseID WHERE registrations.studentEmail = '"+email+"' AND registrations.courseID = '" + courseID + "'";
                    ResultSet resultSet = statement.executeQuery(query);
                    System.out.println(resultSet);
                    while(resultSet.next()){
                        String courseTitleString = resultSet.getString("departmentCode")+" "+resultSet.getString("courseNumber")+" "+resultSet.getString("courseName")+ " Instructed By "+ resultSet.getString("instructorLastName");
                        session.setAttribute("courseTitleString", courseTitleString);
                    }
                    connection.close();
                    }catch(Exception e){
                        e.printStackTrace();
                        System.out.println("Something wrong with SQL statement");
                    }

                %>
        <h2>${courseTitleString}</h2>

        <h2>Assignments</h2>
<!-- Some helpful information when pulling assigments
varible courseID = the courseID column of the assignments table in the database
session varible email is the user's email
-->
</body>
</html>
