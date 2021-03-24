<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.DriverManager" %>
<%@ page import="java.sql.Statement" %>
<%@ page import="java.sql.ResultSet" %><%--
  Created by IntelliJ IDEA.
  User: johnn
  Date: 3/3/2021
  Time: 12:02 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>AssignmentName</title>
    <link href='navigationbar.css' rel='stylesheet'/>
    <link href='assignment_main.css' rel='stylesheet'/>
</head>
<body>
<ul class="navUl">
    <li class="navLi"><a href="home.jsp">Home</a></li>
    <li class="navLi"><a href="edit_profile.jsp">Profile</a></li>
    <%
        String userTypeVar = (String) session.getAttribute("userType");

        if(userTypeVar.equals("student")) {
    %>
    <li class="navLi"><a href="courseRegisterCheckServlet">Course Catalog</a></li>
    <li class="navLi"><a class="active" href="courses_register.jsp">My Courses</a></li>
    <li class="navLi"><a href="account_balance.jsp">Account Balance</a></li>
    <%}
    else { %>
    <li class="navLi"><a class="active" href="courseRegisterCheckServlet"> My Courses</a></li>
    <%  }
    %>
</ul>
<%
    String assignmentID = request.getParameter( "assignmentID" );
    session.setAttribute("courseID", request.getParameter( "courseID" ));
    session.setAttribute("assignmentID", request.getParameter("assignmentID"));
    session.setAttribute("usersID", session.getAttribute("studentID"));
%>
<jsp:include page="/display-assignment-page" />
<div class = mainContainer>
    <h1>${theAssignment.assignmentName}</h1>

    <p>Due Date: ${theAssignment.dueDate}</p>
    <p>${theAssignment.assignmentDescription}</p>
    <p>Points: ${theAssignment.points}</p>

    <%
        if(session.getAttribute("userType").equals("student")){
            // gets the grade to display on the students assignment, if none then its just a "-"
            try{
                String jdbcURL = "jdbc:sqlserver://titan.cs.weber.edu:10433;database=LMS_RunTime";
                String dbUser = "LMS_RunTime";
                String dbPassword = "password1!";
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                Connection connection = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
                Statement statement = connection.createStatement();
                String query = "SELECT * FROM studentSubmission INNER JOIN students ON " +
                        "students.studentID = studentSubmission.studentID WHERE assignmentID = " +
                        Integer.parseInt(assignmentID) + " AND studentSubmission.studentID = " + Integer.parseInt(session.getAttribute("usersID").toString());
                ResultSet resultSet = statement.executeQuery(query);
                while(resultSet.next()) {
//                    need to clear this
                    if(resultSet.getString("grade") == null) {
                        session.setAttribute("studentGradeForDisplay", "-");
                    }
                    else {
                        session.setAttribute("studentGradeForDisplay", String.valueOf(resultSet.getInt("grade")));
                    }

                    if(resultSet.getString("submissionType").equals("F")){
                        String temp = resultSet.getString("fileSubmissionPointer");
                        //System.out.println("THis is the temp:" + temp);
                        //System.out.println("Temp substring:" + temp.substring(temp.lastIndexOf("\\") + 1));
                        session.setAttribute("Submit", temp.substring(temp.lastIndexOf("\\") + 1));
                        String[] spliceOnSubmission = session.getAttribute("Submit").toString().split("ZZ");
                        session.setAttribute("submissionDisplaySpliced", spliceOnSubmission[1]);
     %>

    <p>Submission: <a href="submissionDownload?submission=${Submit}&courseID=
                        ${courseID}&assignmentID=${assignmentID}">${submissionDisplaySpliced}</a></p>

    <%
                    }
                    else if(resultSet.getString("submissionType").equals("T")){
                        session.setAttribute("Submit", resultSet.getString("textSubmission"));
    %>

    <p>Submission: ${Submit}</p>

    <%
                    }
                    else{
                        System.out.println("No submissions");
    %>

    <p>Submission: -</p>

    <%
                    }
                }
                connection.close();
                //session.removeAttribute("studentGradeForDisplay");
            }catch(Exception e){
                e.printStackTrace();
            }

    %>

    <p>Submission Type: ${theAssignment.submissionType}</p>
    <p>Assignment Scored: ${studentGradeForDisplay}</p>
    <c:if test ="${theAssignment.submissionType == 'file'}">
        <form method="post" action="fileSubmissionUploadServlet" enctype="multipart/form-data">
            <label for ="fileSubmission">Add A File: </label>
            <input type = "file" name = "fileSubmission" id = "fileSubmission"><br>
            <input type="submit" value="Submit File"> <br>
        </form>
    </c:if>
    <c:if test ="${theAssignment.submissionType == 'text'}">
        <form method="post" action="text-assignment-submission">
            <label for ="textSubmission">Enter Text: </label><br>
            <textarea id = "textSubmission" name = "textSubmission" rows = "30" cols="100"></textarea><br>
            <input type="text" style="display: none" id="studentIDMain" name="studentIDMain" value="${studentID}">
            <input type="submit" value="Submit File"> <br>
        </form>
        <% //Getting the setAttribute from servlet and creating an error message
            String submissionMessage = "";
            if(request.getAttribute("submitResult") == "true")
            {
                submissionMessage = "Your assignment is Submitted!";
            }
        %>
        <p style ="color:#0073ff"><%= submissionMessage %> </p>
    </c:if>

    <%

        }
        else{
    %>

    <h2>Submissions</h2>
    <table id="myTable">
        <tr class="header">
            <th style="width:15%;">Student ID</th>
            <th style="width:15%;">First Name</th>
            <th style="width:15%;">Last Name</th>
            <th style="width:20%;">Turn in Time</th>
            <th style="width:60%">Submission</th>
            <th style="width:100%">Points/${theAssignment.points}</th>

        </tr>
        <%
            try{
                String jdbcURL = "jdbc:sqlserver://titan.cs.weber.edu:10433;database=LMS_RunTime";
                String dbUser = "LMS_RunTime";
                String dbPassword = "password1!";
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                Connection connection = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
                Statement statement = connection.createStatement();
                String query = "SELECT * FROM studentSubmission INNER JOIN students ON " +
                        "students.studentID = studentSubmission.studentID WHERE assignmentID = " +
                        Integer.parseInt(assignmentID);
                ResultSet resultSet = statement.executeQuery(query);
                while(resultSet.next()) {
                    session.setAttribute("studentID", String.valueOf(resultSet.getInt("studentID")));
                    session.setAttribute("turnInTime", resultSet.getTime("turnInTime").toString());
                    session.setAttribute("sFirstName", resultSet.getString("firstName"));
                    session.setAttribute("sLastName", resultSet.getString("lastName"));

                    if(resultSet.getString("grade") == null) {
                        session.setAttribute("studentGrade", "-");
                    }
                    else {
                        session.setAttribute("studentGrade", String.valueOf(resultSet.getInt("grade")));
                    }
                    //System.out.println("student grade: " + session.getAttribute("studentGrade"));
                    session.setAttribute("SubID", String.valueOf(resultSet.getInt("submissionID")));
                    %>

            <tr>
                <td>${studentID}</td>
                <td>${sFirstName}</td>
                <td>${sLastName}</td>
                <td>${turnInTime}</td>


                        <%
                    if(resultSet.getString("submissionType").equals("F")){
                        String temp = resultSet.getString("fileSubmissionPointer");
                        //System.out.println("THis is the temp:" + temp);
                        //System.out.println("Temp substring:" + temp.substring(temp.lastIndexOf("\\") + 1));
                        session.setAttribute("submission", temp.substring(temp.lastIndexOf("\\") + 1));
                        String[] spliceOnSubmission = session.getAttribute("submission").toString().split("ZZ");
                        session.setAttribute("submissionDisplaySplice", spliceOnSubmission[1]);
                        %>

                        <td><a href="submissionDownload?submission=${submission}&courseID=
                        ${courseID}&assignmentID=${assignmentID}">${submissionDisplaySplice}</a></td>

                        <%
                    }
                    else{
                        session.setAttribute("submission", resultSet.getString("textSubmission"));
                        %>

                        <td>${submission}</td>

                        <%
                    }

                    %>
                <td>
                    <form class="gradeForm" action="${pageContext.request.contextPath}/SubmitGradeServlet" method="post">
                        <input type="number" id="gradePointsBox" name="gradePointsBox" step="5" oninput="emptyGrade()" placeholder="${studentGrade}">
                        <input type="submit" id="gradeSubBtn" disabled="true" value="Submit Grade">
                    </form>
                </td>
            </tr>
            <%

        }
            connection.close();
                session.removeAttribute("studentID");
                session.removeAttribute("turnInTime");
                session.removeAttribute("sFirstName");
                session.removeAttribute("sLastName");
                session.removeAttribute("submission");
        }catch(Exception e){
            e.printStackTrace();
        }
                        %>

    </table>

    <%
        }
    %>



</div>
<script type="text/javascript">
    function emptyGrade() {
        if(document.getElementById("gradePointsBox").value==="") {
            document.getElementById('gradeSubBtn').disabled = true;
        } else {
            document.getElementById('gradeSubBtn').disabled = false;
        }
    }
</script>

</body>
</html>
