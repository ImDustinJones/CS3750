<%@ page import="java.awt.*" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.example.ProjectDemo.Courses" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.DriverManager" %>
<%@ page import="java.sql.Statement" %>
<%@ page import="java.sql.ResultSet" %><%--
  Created by IntelliJ IDEA.
  User: johnn
  Date: 2/23/2021
  Time: 12:06 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href='navigationbar.css' rel='stylesheet'/>
    <link href='home.css' rel='stylesheet'/>
    <title>Courses</title>
</head>
<body>
<jsp:include page="/display-instructors-course" />
<ul class="navUl">
    <li class="navLi"><a href="home.jsp">Home</a></li>
    <li class="navLi"><a class="active" href="edit_profile.jsp">Profile</a></li>
    <li class="navLi"><a href="courses_register.jsp">Courses</a></li>
    <li class="navLi"><a href="#DummyN3">Dummy</a></li>
</ul>
<div class="mainContainer">
    <div class = "profileContainer">
    <h1>Your Courses</h1>

    <!--This is the course list -->
        <%
            try{
                String jdbcURL = "jdbc:sqlserver://titan.cs.weber.edu:10433;database=LMS_RunTime";
                String dbUser = "LMS_RunTime";
                String dbPassword = "password1!";
                String email = (String) session.getAttribute("email");
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                Connection connection = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);

                Statement statement = connection.createStatement();
                String query = "SELECT * FROM courseList WHERE instructorEmail LIKE '"+email+"';";
                ResultSet resultSet = statement.executeQuery(query);

                while(resultSet.next()){
                    String courseString1 = resultSet.getString("departmentCode")+" "+resultSet.getString("courseNumber")+" "+resultSet.getString("courseName");
                    String courseString2 = resultSet.getString("instructorLastName");
                    String days = "";
                    if(resultSet.getString("monday").equals("1")){
                        days = days.concat("Mon");
                    }
                    if(resultSet.getString("tuesday").equals("1")){
                        days = days.concat(" Tue");
                    }
                    if(resultSet.getString("wednesday").equals("1")){
                        days = days.concat(" Wed");
                    }
                    if(resultSet.getString("thursday").equals("1")){
                        days = days.concat(" Thur");
                    }
                    if(resultSet.getString("friday").equals("1")){
                        days = days.concat(" Fri");
                    }
                    String courseString3 = days+": "+resultSet.getString("startTime").substring(0,resultSet.getString("startTime").length() - 11)+ " - "+resultSet.getString("endTime").substring(0,resultSet.getString("endTime").length() - 11);
                    session.setAttribute("courseString1", courseString1);
                    session.setAttribute("instructorLastName", courseString2);
                    session.setAttribute("courseString3", courseString3);
                    session.setAttribute("courseNumber", resultSet.getString("courseNumber"));
                    session.setAttribute("courseName", resultSet.getString("courseName"));
                    session.setAttribute("departmentCode", resultSet.getString("departmentCode"));
                    session.setAttribute("instructorEmail", resultSet.getString("instructorEmail"));
                    session.setAttribute("courseDescription", resultSet.getString("courseDescription"));
                    session.setAttribute("creditHours", resultSet.getInt("creditHours"));
                    session.setAttribute("studentCapacity", resultSet.getInt("studentCapacity"));
                    session.setAttribute("startTime", resultSet.getString("startTime"));
                    session.setAttribute("endTime", resultSet.getString("endTime"));
                    session.setAttribute("courseID", resultSet.getInt("courseID"));

        %>
       <div class="card">
            <div class="container">
                <h4><b>${courseString1}</b></h4>
                <p>${instructorLastName}</p>
                <p>${courseString3}</p>

                <button onclick="myFunction()">Edit Course</button>
                <div id="myDiv">
                    <h2 >Edit Course</h2>
                    <form method="post" action="${pageContext.request.contextPath}/edit-course">
                    <label for="CourseNumber1">Course Number: </label>
                    <input type="text" name="CourseNumber1" id="CourseNumber1" placeholder="${courseNumber}"><br>

                    <label for="CourseName1">Course Name: </label>
                    <input type="text" name="CourseName1" id="CourseName1" placeholder="${courseName}"><br>

                    <label for="Department1">Department: </label>
                    <input type="text" name="Department1" id="Department1" placeholder="${departmentCode}"><br>

                    <label for="CreditHours1">Credit Hours: </label>
                    <input type="number" name="CreditHours1" id="CreditHours1" placeholder="${creditHours}"><br>

                    <label for="Capacity1">Capacity: </label>
                    <input type="Number" name="Capacity1" id="Capacity1" placeholder="${studentCapacity}"><br>

                    <label for="Monday1">Monday</label>
                    <input type="checkbox" id="Monday1" name="Monday1" value="1"><br>

                    <label for="Tuesday1">Tuesday</label>
                    <input type="checkbox" id="Tuesday1" name="Tuesday1" value="1"><br>

                    <label for="Wednesday1">Wednesday</label>
                    <input type="checkbox" id="Wednesday1" name="Wednesday1" value="1"><br>

                    <label for="Thursday1">Thursday</label>
                    <input type="checkbox" id="Thursday1" name="Thursday1" value="1"><br>

                    <label for="Friday1">Friday</label>
                    <input type="checkbox" id="Friday1" name="Friday1" value="1"><br>

                    <label for="StartTime1">Start Time:</label>
                    <input type="time" name="StartTime1" id="StartTime1" placeholder="${startTime}"> <br>

                    <label for="EndTime1">End Time:</label>
                    <input type="time" name="EndTime1" id="EndTime1" placeholder="${endTime}"> <br>

                    <label for ="CourseDescription1">Course Description: </label>
                    <textarea id = "CourseDescription1" name = "CourseDescription1" rows = "10" cols="50" placeholder="${courseDescription}"></textarea><br>

                    <input type="submit" value="Update">
                </form>
                </div>
<script>
                function myFunction(){
                    const x = document.getElementById("myDiv");
                    if(x.style.display==="none"){
                        x.style.display = "block";
                    }
                    else{
                        x.style.display = "none";
                    }
                }
</script>
            </div>
       </div>
        <%}
            connection.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        %>



    <div class="popup">
        <div class="popup__container">
            <button type="button" class="close-button"></button>
            <h2 class="popup__title">Add Course</h2>
        <form method="post" action="${pageContext.request.contextPath}/add-course">
            <label for="CourseNumber">Course Number: </label>
            <input type="text" name="CourseNumber" id="CourseNumber"><br>

            <label for="CourseName">Course Name: </label>
            <input type="text" name="CourseName" id="CourseName"><br>

            <label for="Department">Department: </label>
            <input type="text" name="Department" id="Department"><br>

            <label for="CreditHours">Credit Hours: </label>
            <input type="number" name="CreditHours" id="CreditHours"><br>

            <label for="Capacity">Capacity: </label>
            <input type="Number" name="Capacity" id="Capacity"><br>

            <label for="Monday">Monday</label>
            <input type="checkbox" id="Monday" name="Monday" value="1"><br>

            <label for="Tuesday">Tuesday</label>
            <input type="checkbox" id="Tuesday" name="Tuesday" value="1"><br>

            <label for="Wednesday">Wednesday</label>
            <input type="checkbox" id="Wednesday" name="Wednesday" value="1"><br>

            <label for="Thursday">Thursday</label>
            <input type="checkbox" id="Thursday" name="Thursday" value="1"><br>

            <label for="Friday">Friday</label>
            <input type="checkbox" id="Friday" name="Friday" value="1"><br>

            <label for="StartTime">Start Time:</label>
            <input type="time" name="StartTime" id="StartTime"> <br>

            <label for="EndTime">End Time:</label>
            <input type="time" name="EndTime" id="EndTime"> <br>

            <label for ="CourseDescription">Course Description: </label>
            <textarea id = "CourseDescription" name = "CourseDescription" rows = "10" cols="50"></textarea><br>

            <input type="submit" value="Add">
        </form>
        </div>
    </div>
    <div style = "text-align: center">
        <button class = "popup-button">Add Course</button>
    </div>
</div>
</div>
<script src="./editprofile.js"></script>
</body>
</html>