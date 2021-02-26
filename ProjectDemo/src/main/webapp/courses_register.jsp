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
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<html>
<head>
    <link href='navigationbar.css' rel='stylesheet'/>
    <link href='home.css' rel='stylesheet'/>
    <title>Courses</title>
    <style>
        * {
            box-sizing: border-box;
        }

        #myTable {
            border-collapse: collapse;
            width: 100%;
            border: 1px solid #ddd;
            font-size: 18px;
            background-color: #F6AE2D;
        }

        #myTable th, #myTable td {
            text-align: left;
            padding: 12px;
        }

        #myTable tr {
            border-bottom: 1px solid #ddd;
        }

        #myTable tr.header, #myTable tr:hover {
            background-color: #f1f1f1;
        }

        #coursesContainer{
            margin: auto;
            padding-left: 5%;
            width: 80%;
            box-sizing: border-box;
        }
    </style>
</head>
<body>
<jsp:include page="/display-instructors-course" />
<ul class="navUl">
    <li class="navLi"><a href="home.jsp">Home</a></li>
    <li class="navLi"><a class="active" href="edit_profile.jsp">Profile</a></li>
    <li class="navLi"><a href="courses_register.jsp">Courses</a></li>
    <li class="navLi"><a href="#DummyN3">Dummy</a></li>
</ul>

<h1>Your Courses</h1>

<div id="coursesContainer">
    <div class = "profileContainer">
        <p>${courses}</p>
        <c:forEach items = "${courses}" var = "course" >
            <c:out value = "${course.departmentCode}${course.courseNumber} ${course.courseName}"/><p>
            <c:if test ="${course.monday == 1}">
                <p>Monday</p>
            </c:if>
            <c:if test ="${course.tuesday == 1}">
                <p>Tuesday</p>
            </c:if>
            <c:if test ="${course.wednesday == 1}">
                <p>Wednesday</p>
            </c:if>
            <c:if test ="${course.thursday == 1}">
                <p>Thursday</p>
            </c:if>
            <c:if test ="${course.friday == 1}">
                <p>Friday</p>
            </c:if>
            <p>${courseDescription}</p>

            <button>Edit Course</button><br>
            <form method="post" action="${pageContext.request.contextPath}/edit-course">
                <label for ="CourseID1">CourseID (unchangable)</label>
                <input type ="number" name = "CourseID1" id = "CourseID1" value = "${course.courseID}" placeholder = "${course.courseID}" readonly><br>
                <label for="CourseNumber1">Course Number: </label>
                <input type="text" name="CourseNumber1" id="CourseNumber1" placeholder = "${course.courseNumber}"><br>

                <label for="CourseName1">Course Name: </label>
                <input type="text" name="CourseName1" id="CourseName1" placeholder = "${course.courseName}"><br>

                <label for="Department1">Department: </label>
                <input type="text" name="Department1" id="Department1" placeholder = "${course.departmentCode}"><br>

                <label for="CreditHours1">Credit Hours: </label>
                <input type="number" name="CreditHours1" id="CreditHours1" placeholder = "${course.creditHours}"><br>

                <label for="Capacity"1>Capacity: </label>
                <input type="Number" name="Capacity1" id="Capacity1" placeholder = "${course.studentCapacity}"><br>

                <label for="Monday1">Monday</label>
                <input type="checkbox" id="Monday1" name="Monday1" value="1"><br>

                <label for="Tuesday1">Tuesday</label>
                <input type="checkbox" id="Tuesday1" name="Tuesday1" value="1" ><br>

                <label for="Wednesday1">Wednesday</label>
                <input type="checkbox" id="Wednesday1" name="Wednesday1" value="1"><br>

                <label for="Thursday1">Thursday</label>
                <input type="checkbox" id="Thursday1" name="Thursday1" value="1"><br>

                <label for="Friday1">Friday</label>
                <input type="checkbox" id="Friday1" name="Friday1" value="1"><br>

                <label for="StartTime1">Start Time:</label>
                <input type="time" name="StartTime1" id="StartTime1" placeholder = "${course.startTime}"> <br>

                <label for="EndTime1">End Time:</label>
                <input type="time" name="EndTime1" id="EndTime1" placeholder = "${course.endTime}"> <br>

                <label for ="CourseDescription1">Course Description: </label>
                <textarea id = "CourseDescription1" name = "CourseDescription1" rows = "10" cols="50" placeholder = "${course.courseDescription}"></textarea><br>

                <input type="submit" value="Add">
            </form>

        </c:forEach>
    </div>

        <table id="myTable">
            <tr class="header">
                <th style="width:20%;">Course Name</th>
                <th style="width:10%;">Department</th>
                <th style="width:10%;">Course Number</th>
                <th style="width:40%;">Description</th>
                <th style="width:10%;">Credit Hours</th>
                <th style="width:10%;">Mon</th>
                <th style="width:10%;">Tue</th>
                <th style="width:10%;">Wed</th>
                <th style="width:10%;">Thu</th>
                <th style="width:10%;">Fri</th>
                <th style="width:10%;">Start Time</th>
                <th style="width:10%;">End Time</th>
                <th style="width:10%;">Capacity</th>
                <th></th>

            </tr>
            <%
                try{
                    String jdbcURL = "jdbc:sqlserver://titan.cs.weber.edu:10433;database=LMS_RunTime";
                    String dbUser = "LMS_RunTime";
                    String dbPassword = "password1!";
                    String email = (String) session.getAttribute("email");
                    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                    Connection connection = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
                    Statement statement = connection.createStatement();
                    String query = "SELECT * FROM courseList WHERE instructorEmail LIKE '" + email + "'";
                    ResultSet resultSet = statement.executeQuery(query);
                    while(resultSet.next()){
                        String courseName =  resultSet.getString("courseName");
                        String department =  resultSet.getString("departmentCode");
                        String courseNumber =  resultSet.getString("courseNumber");
                        String description =  resultSet.getString("courseDescription");
                        String credits = resultSet.getString("creditHours");
                        String start = resultSet.getTime("startTime").toLocalTime().toString();
                        String end = resultSet.getTime("endTime").toLocalTime().toString();
                        String capacity = String.valueOf(resultSet.getInt("studentCapacity"));
                        session.setAttribute("courseTable1", courseName);
                        session.setAttribute("courseTable2", department);
                        session.setAttribute("courseTable3", courseNumber);
                        session.setAttribute("courseTable4", description);
                        session.setAttribute("courseTable5", credits);
                        if(resultSet.getBoolean("monday")){
                            session.setAttribute("courseTable6", "X");
                        }
                        else{
                            session.setAttribute("courseTable6", "");
                        }
                        if(resultSet.getBoolean("tuesday")){
                            session.setAttribute("courseTable7", "X");
                        }
                        else{
                            session.setAttribute("courseTable7", "");
                        }
                        if(resultSet.getBoolean("wednesday")){
                            session.setAttribute("courseTable8", "X");
                        }
                        else{
                            session.setAttribute("courseTable8", "");
                        }
                        if(resultSet.getBoolean("thursday")){
                            session.setAttribute("courseTable9", "X");
                        }
                        else{
                            session.setAttribute("courseTable9", "");
                        }
                        if(resultSet.getBoolean("friday")){
                            session.setAttribute("courseTable10", "X");
                        }
                        else{
                            session.setAttribute("courseTable10", "");
                        }
                        session.setAttribute("courseTable11", start);
                        session.setAttribute("courseTable12", end);
                        session.setAttribute("courseTable13", capacity);
            %>
            <tr>
                <td>${courseTable1}</td>
                <td>${courseTable2}</td>
                <td>${courseTable3}</td>
                <td>${courseTable4}</td>
                <td>${courseTable5}</td>
                <td>${courseTable6}</td>
                <td>${courseTable7}</td>
                <td>${courseTable8}</td>
                <td>${courseTable9}</td>
                <td>${courseTable10}</td>
                <td>${courseTable11}</td>
                <td>${courseTable12}</td>
                <td>${courseTable13}</td>
                <td><button>Edit Course</button></td>
            </tr>
            <%}
                connection.close();
            }catch(Exception e){
                e.printStackTrace();
            }
            %>
        </table>

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
<script src="editprofile.js"></script>
</body>
</html>