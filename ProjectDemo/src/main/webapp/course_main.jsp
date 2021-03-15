<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.DriverManager" %>
<%@ page import="java.sql.Statement" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <link href='navigationbar.css' rel='stylesheet'/>
    <link href='course_main.css' rel='stylesheet'/>
    <%
        String courseIDCourse_main = request.getParameter( "courseID" );
        session.setAttribute("courseID", request.getParameter( "courseID" ));
    %>
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
                    String userTypeVar = (String) session.getAttribute("userType");

                    if(userTypeVar.equals("student")) {
                        Statement statement = connection.createStatement();
                        String query = "SELECT registrations.courseID, courseList.courseNumber, courseList.courseName, courseList.departmentCode, courseList.instructorLastName " +
                                "FROM registrations INNER JOIN courseList ON registrations.courseID = courseList.courseID WHERE registrations.studentEmail = '" + email + "' AND registrations.courseID = '" + courseIDCourse_main + "'";
                        ResultSet resultSet = statement.executeQuery(query);
                        System.out.println(resultSet);
                        while (resultSet.next()) {
                            String courseTitleString = resultSet.getString("departmentCode") + " " + resultSet.getString("courseNumber") + " " + resultSet.getString("courseName") + " Instructed By " + resultSet.getString("instructorLastName");
                            session.setAttribute("courseTitleString", courseTitleString);
                        }
                    }
                    else {
                        Statement statement = connection.createStatement();
                        String query = "SELECT courseID, courseNumber, courseName, departmentCode, instructorLastName " +
                                "FROM courseList WHERE instructorEmail = '" + email + "' AND courseID = '" + courseIDCourse_main + "'";
                        ResultSet resultSet = statement.executeQuery(query);
                        System.out.println(resultSet);
                        while (resultSet.next()) {
                            String courseTitleString = resultSet.getString("departmentCode") + " " + resultSet.getString("courseNumber") + " " + resultSet.getString("courseName");
                            session.setAttribute("courseTitleString", courseTitleString);
                        }
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

<jsp:include page="/display-assignments" />
        <div class="mainContainer">
            <h1>${courseTitleString}</h1>
            <h2>Assignments</h2>

            <table id="myTable2">
                <tr class="header">
                    <th>Assignment Name</th>
                    <th style="width:10%;">Points</th>
                    <th style="width:10%;">Due Date</th>
                    <th style="width:10%;">Submission Type</th>
                    <th style="width:40%;">Description</th>
                </tr>

                <tr> <c:forEach items = "${assignmentList}" var = "assignment" >
                    <td><a href = "assignment_main.jsp?assignmentID=${assignment.assignmentID}&courseID=${courseID}">${assignment.assignmentName}</a></td>
                    <td>${assignment.points}</td>
                    <td>${assignment.dueDate}</td>
                    <td>${assignment.submissionType}</td>
                    <td>${assignment.assignmentDescription}</td>
                </tr>
                </c:forEach>
            </table>

            <p>The course ID is <%=request.getParameter( "courseID" )%></p>


        </div>
<!-- Some helpful information when pulling assigments
pull courseID using request.getParameter( "courseID" )!
session varible email is the user's email
-->




</body>
</html>
