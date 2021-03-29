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
<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.9.4/Chart.min.js" integrity="sha512-d9xgZrVZpmmQlfonhQUvTR7lMPtO7NkZMkA0ABN3PHCbKA5nqylQ/yWlFAyY6hYgdF1Qh6nYiuADWwKB4C2WSw==" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.9.4/Chart.bundle.js" integrity="sha512-zO8oeHCxetPn1Hd9PdDleg5Tw1bAaP0YmNvPY8CwcRyUk7d7/+nyElmFrB6f7vg4f7Fv4sui1mcep8RIEShczg==" crossorigin="anonymous"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.9.4/Chart.css" integrity="sha512-C7hOmCgGzihKXzyPU/z4nv97W0d9bv4ALuuEbSf6hm93myico9qa0hv4dODThvCsqQUmKmLcJmlpRmCaApr83g==" crossorigin="anonymous" />
<script src='https://cdn.plot.ly/plotly-latest.min.js'></script>
<script src="assignment_main.js"> </script>
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
        if(session.getAttribute("userType").equals("instructor")){%>

    <canvas id="myChart" width="800" height="400"></canvas>
    <script>
        var grades = [];
        var occurences = [];
        for(i = 0; i<10; i++){
            occurences[i] = 0;
        }
        <%
                try{
                    String jdbcURL2 = "jdbc:sqlserver://titan.cs.weber.edu:10433;database=LMS_RunTime";
                    String dbUser2 = "LMS_RunTime";
                    String dbPassword2 = "password1!";
                    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                    Connection connection2 = DriverManager.getConnection(jdbcURL2, dbUser2, dbPassword2);
                    Statement statement2 = connection2.createStatement();
                    String query2 = "SELECT * FROM studentSubmission INNER JOIN assignments ON " +
                        "assignments.assignmentID = studentSubmission.assignmentID WHERE studentSubmission.assignmentID = " +
                        Integer.parseInt(assignmentID)+ " AND studentSubmission.grade IS NOT NULL";
                    ResultSet resultSet2 = statement2.executeQuery(query2);
                     while(resultSet2.next()) {

                     %>
        grades.push(<%=((Double.parseDouble(resultSet2.getString("grade"))/Double.parseDouble(resultSet2.getString("points")))*100)%>);

        <%
            }
        connection2.close();
    }catch(Exception e){
        e.printStackTrace();
    }
%>
        console.log(grades);
        console.log(occurences);


        for(i=0; i<grades.length; i++){
            console.log(grades[i]);
            if(grades[i]<10 ){
                occurences[0] = occurences[0] + 1;
            }
            if(grades[i]<20 && grades[i]>=10){
                occurences[1] = occurences[1] + 1;
            }
            if(grades[i]<30 && grades[i]>=20){
                occurences[2] = occurences[2] + 1;
            }
            if(grades[i]<40 && grades[i]>=30){
                occurences[3] = occurences[3] + 1;
            }
            if(grades[i]<50 && grades[i]>=40){
                occurences[4] = occurences[4] + 1;
            }
            if(grades[i]<60 && grades[i]>=50){
                occurences[5] = occurences[5] + 1;
            }
            if(grades[i]<70 && grades[i]>=60){
                occurences[6] = occurences[6] + 1;
            }
            if(grades[i]<80 && grades[i]>=70){
                occurences[7] = occurences[7] + 1;
            }
            if(grades[i]<90 && grades[i]>=80){
                occurences[8] = occurences[8] + 1;
            }
            if(grades[i]<=100 && grades[i]>=90){
                occurences[9] = occurences[9] + 1;
            }
        }
        console.log(occurences);

        var ctx = document.getElementById("myChart");
        var myChart = new Chart(ctx, {
            type: 'bar',
            data: {
                labels: ["0-10", "10-20", "20-30", "30-40", "40-50", "50-60", "60-70", "70-80", "80-90", "90-100"],
                datasets: [{
                    label: 'Number of Student Assignment Scores Within the Following Percent Score Interval',
                    data: occurences,
                    backgroundColor: [
                        '#ff0800',
                        '#ff140d',
                        '#ff2d26',
                        '#ff3c36',
                        '#ff5c57',
                        '#ff7975',
                        '#a3ff8f',
                        '#88ff6e',
                        '#73ff54',
                        '#48ed24',
                        '#1fa103',
                        '#1fa103'
                    ],
                    borderColor: [
                        '#ff0800',
                        '#ff140d',
                        '#ff2d26',
                        '#ff3c36',
                        '#ff5c57',
                        '#ff7975',
                        '#a3ff8f',
                        '#88ff6e',
                        '#73ff54',
                        '#48ed24',
                        '#1fa103',
                        '#1fa103'
                    ],
                    borderWidth: 1
                }]
            },
            options: {
                responsive: false,
                scales: {
                    xAxes: [{
                        ticks: {
                            maxRotation: 90,
                            minRotation: 80
                        },
                        gridLines: {
                            offsetGridLines: true
                        }
                    }
                    ],
                    yAxes: [{
                        scaleLabel: {
                            labelString: 'Number of Student Scores within each Score Interval',
                        },
                        ticks: {
                            beginAtZero: true
                        }
                    }]
                }
            }
        });

    </script>

    <%}
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
                        session.setAttribute("submission", resultSet.getString("textSubmission"));
    %>

    <div id='myDiv'><!-- Plotly chart will be drawn inside this DIV --></div>
    <p><a href="text_submission_view.jsp?firstName=${sFirstName}&lastName=${sLastName}&assignmentName=${theAssignment.assignmentName}&submission=${submission}">View Submission</a></p>

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

    <p>Submission Type : ${theAssignment.submissionType}</p>
    <p>Assignment Scored: ${studentGradeForDisplay}</p>


    <div id='displayBoxPlot'><!-- Plotly chart will be drawn inside this DIV --></div>
    <script>
        var grades = [];

        <%
            try{
                String jdbcURL2 = "jdbc:sqlserver://titan.cs.weber.edu:10433;database=LMS_RunTime";
                String dbUser2 = "LMS_RunTime";
                String dbPassword2 = "password1!";
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                Connection connection2 = DriverManager.getConnection(jdbcURL2, dbUser2, dbPassword2);
                Statement statement2 = connection2.createStatement();
                String query2 = "SELECT * FROM studentSubmission INNER JOIN students ON " +
                    "students.studentID = studentSubmission.studentID WHERE assignmentID = " +
                    Integer.parseInt(assignmentID)+ " AND studentSubmission.grade IS NOT NULL";
                ResultSet resultSet2 = statement2.executeQuery(query2);
                while(resultSet2.next()) {

                 %>
        grades.push(<%=resultSet2.getString("grade")%>);
        <%
                }
                connection2.close();
            }
            catch(Exception e){
                e.printStackTrace();
            }
%>
        var trace1 = {
            x: grades,
            type: 'box',
            name: '',
            marker:{
                color: '#F6AE2D'
            }
        };

        var data = [trace1];

        var layout = {
            title: 'Assignment Graded Summary',
            xaxis:{ gridcolor: '#606060'},
            plot_bgcolor: '#001011',
            paper_bgcolor: 'rgba(0, 0, 0, 0)'
        };

        Plotly.newPlot('displayBoxPlot', data, layout);
    </script>


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

                            <td><a href="text_submission_view.jsp?firstName=${sFirstName}&lastName=${sLastName}&assignmentName=${theAssignment.assignmentName}&submission=${submission}">View Submission</a></td>

                            <%
                        }
                            %>
                        <td>
                            <form class="gradeForm" action="${pageContext.request.contextPath}/SubmitGradeServlet" method="post">
                                <input type="number" id="gradePointsBox${SubID}" name="gradePointsBox${SubID}" oninput="emptyGrade(${SubID})" step="any" placeholder="${studentGrade}">
                                <input type="text" id = "studentSubID" name = "studentSubID" style="display: none" value = "${SubID}" >
                                <input type="submit" id="gradeSubBtn${SubID}" disabled="true" value="Submit Grade">
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
</body>
</html>
