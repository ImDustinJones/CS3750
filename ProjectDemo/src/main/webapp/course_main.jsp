<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.DriverManager" %>
<%@ page import="java.sql.Statement" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href='navigationbar.css' rel='stylesheet'/>
    <link href='course_main.css' rel='stylesheet'/>
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

    <ul class="navUl">
        <li class="navLi"><a href="home.jsp">Home</a></li>
        <li class="navLi"><a href="edit_profile.jsp">Profile</a></li>
        <%
            String userTypeVar = (String) session.getAttribute("userType");

            if(userTypeVar.equals("student")) {
        %>
        <li class="navLi"><a href="courseRegisterCheckServlet">Course Catalog</a></li>
        <li class="navLi"><a href="courses_register.jsp">My Courses</a></li>
        <%}
        else { %>
        <li class="navLi"><a href="courseRegisterCheckServlet"> My Courses</a></li>
        <%  }
        %>
    </ul>

        <div class="mainContainer">
            <h1>${courseTitleString}</h1>
            <h2>Assignments</h2>
        </div>
<!-- Some helpful information when pulling assigments
varible courseID = the courseID column of the assignments table in the database
session varible email is the user's email
-->
</body>
</html>
