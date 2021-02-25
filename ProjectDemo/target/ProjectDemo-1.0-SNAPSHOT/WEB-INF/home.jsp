<%@ page import="com.example.ProjectDemo.Users" %>
<%@ page import="java.sql.DriverManager" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.Statement" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang='en'>
<head>
    <meta charset='utf-8' />
    <link href='navigationbar.css' rel='stylesheet'/>
    <link href='fullcalendar/main.css' rel='stylesheet'/>
    <link href='home.css' rel='stylesheet'/>
    <script src="https://cdn.jsdelivr.net/npm/fullcalendar@5.3.0/main.min.js"></script>
    <script>
        document.addEventListener('DOMContentLoaded', function() {
            var calendarEl = document.getElementById('calendar');
            var calendar = new FullCalendar.Calendar(calendarEl, {
                events: [
                    {
                        title : 'Place Holder Assignment 1',
                        start : '2021-02-09',
                        end : '2021-02-09',
                        color : 'blue'
                    },
                    {
                        title : 'Place Holder Assignment 2',
                        start : '2021-02-13',
                        end : '2021-02-13',
                        color : 'red'
                    },
                    {
                        title : 'Place Holder Assignment 3',
                        start : '2021-02-18',
                        end : '2021-02-18',
                        color : 'blue'
                    },
                    {
                        title : 'Place Holder Assignment 4',
                        start : '2021-02-27',
                        end : '2021-02-27',
                        color : 'purple'
                    },
                    {
                        title : 'Place Holder Assignment 5',
                        start : '2021-02-04',
                        end : '2021-02-04',
                        color : 'purple'
                    }
                ],
                initialView: 'dayGridMonth',
                height: 500,
            });
            calendar.render();
        });
    </script>
    <title>Home Page</title>
</head>
<body>

<div class="notificationBell" onclick="toggleBox();">
    <img src="img/Bell.png" alt="notification bell" width="80" height="80"/>
</div>
<div class="notificationBox" id="box">

    <div class="notificationItem">
        Dummy Text 1 <span class="itemX" onclick="deleteItem(this)"> X </span>
    </div>
    <div class="notificationItem">
        Dummy Text 2 <span class="itemX" onclick="deleteItem(this)"> X </span>
    </div>
    <div class="notificationItem">
        Dummy Text 3 <span class="itemX" onclick="deleteItem(this)"> X </span>
    </div>
    <div class="notificationItem">
        Dummy Text 4 <span class="itemX" onclick="deleteItem(this)"> X </span>
    </div>
    <div class="notificationItem">
        Dummy Text 5 <span class="itemX" onclick="deleteItem(this)"> X </span>
    </div>
</div>


<ul class="navUl">
    <li class="navLi"><a href="home.jsp">Home</a></li>
    <li class="navLi"><a class="active" href="edit_profile.jsp">Profile</a></li>
    <li class="navLi"><a href="courses_register.jsp">Courses</a></li>
    <li class="navLi"><a href="#DummyN2">Dummy</a></li>
</ul>

<ul class="todoUL">
    <h2>My To Do List</h2>
    <li><a href="#DummyT1">CS1234 Dummy<br>Dummy Assignment1<br> 2/10/21 11:59PM</a><span class="itemX" onclick="deleteItem(this)"> X </span></li>
    <li><a href="#DummyT2">CS1234 Dummy<br>Dummy Assignment2<br> 2/15/21 11:59PM</a><span class="itemX" onclick="deleteItem(this)"> X </span></li>
    <li><a href="#DummyT3">CS1234 Dummy<br>Dummy Assignment3<br> 2/17/21 11:59PM</a><span class="itemX" onclick="deleteItem(this)"> X </span></li>
    <li><a href="#DummyT4">CS1234 Dummy<br>Dummy Assignment4<br> 2/21/21 11:59PM</a><span class="itemX" onclick="deleteItem(this)"> X </span></li>
    <li><a href="#DummyT5">CS1234 Dummy<br>Dummy Assignment5<br> 3/1/21 11:59PM</a><span class="itemX" onclick="deleteItem(this)"> X </span></li>
</ul>

<div class="mainContainer">
    <h1>Welcome ${firstName} ${lastName}!</h1>

    <div class="courseContainer">
        <h2>Courses List</h2>

        <div class="cardContainer">
            <%
                try{
                    String jdbcURL = "jdbc:sqlserver://titan.cs.weber.edu:10433;database=LMS_RunTime";
                    String dbUser = "LMS_RunTime";
                    String dbPassword = "password1!";
                    String email = (String) session.getAttribute("email");
                    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                    Connection connection = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
                    Statement statement = connection.createStatement();
                    String query = "SELECT registrations.courseNumber, courseList.courseName, courseList.instructorLastName, courseList.startTime, courseList.endTime " +
                            "FROM registrations INNER JOIN courseList ON registrations.courseNumber = courseList.courseNumber WHERE registrations.studentEmail = '"+email+"'";
                    ResultSet resultSet = statement.executeQuery(query);
<<<<<<< HEAD
                    while(resultSet.next()){%>
            <%--   This is still WIP currently unable to pull values but my code will display the amount of registered courses the user is in
            <a href="#DummyA"> <div class="card">
                     <div class="container">
                         <h4><b><%=resultSet.getString("registrations.courseNumber")+" "+resultSet.getString("courseList.courseName")%></b></h4>
                         <p><%=resultSet.getString("courseList.instructorLastName")%></p>
                         <p><%="DAYS OF WEEK: "+resultSet.getString("courseList.startTime")+ " - "+resultSet.getString("courseList.endTime")%></p>
                     </div>
               </div></a> --%>
=======
                    while(resultSet.next()){
            String courseString1 = resultSet.getString("registrations.courseNumber")+" "+resultSet.getString("courseList.courseName");
            String courseString2 = resultSet.getString("courseList.instructorLastName");
            String courseString3 = "DAYS OF WEEK: "+resultSet.getString("courseList.startTime")+ " - "+resultSet.getString("courseList.endTime");
            session.setAttribute("courseString1", courseString1);
            session.setAttribute("courseString2", courseString2);
            session.setAttribute("courseString3", courseString3);%>
            <a href="#DummyA"> <div class="card">
                     <div class="container">
                         <h4><b>${courseString1}</b></h4>
                         <p>${courseString2}</p>
                         <p>${courseString3}</p>
                     </div>
               </div></a>
>>>>>>> origin/main
            <a href="#DummyB"> <div class="card">
                <div class="container">
                    <h4><b>CS1234 Dummy Computer Science</b></h4>
                    <p>Instructor 1</p>
                    <p>Mon/Wed 1:00PM - 2:00PM</p>
                </div>
            </div></a>
            <%}
                connection.close();
            }catch(Exception e){
                e.printStackTrace();
            }
            %>
        </div>
    </div>
    <h2>Learning Calendar</h2>
    <div class="calanderContainer">
        <div id='calendar' ></div> <%--Comment this out to hide the calendar for testing if it's annoying--%>
    </div>
</div>

<div id='logoText'>
    <p>RunTime Terror's LMS</p>
</div>

<script src="notifications.js"></script>

</body>
</html>