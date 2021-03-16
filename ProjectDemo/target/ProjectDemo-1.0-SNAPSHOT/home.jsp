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
                events: [<%
                try{
                String jdbcURL = "jdbc:sqlserver://titan.cs.weber.edu:10433;database=LMS_RunTime";
                String dbUser = "LMS_RunTime";
                String dbPassword = "password1!";
                String email = (String) session.getAttribute("email");
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                Connection connection = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);

                Statement statement = connection.createStatement();
                String userTypeVar = (String) session.getAttribute("userType");
                if(userTypeVar.equals("student")) {
                String query = "SELECT registrations.courseID, assignments.assignmentName, assignments.dueDate " +
                        "FROM registrations INNER JOIN assignments on registrations.courseID = assignments.courseID WHERE registrations.studentEmail = '"+email+"'";
                session.setAttribute("calendarQuery", query);
                }
                if(userTypeVar.equals("instructor")) {
                String query = "SELECT courseID, assignmentName, dueDate " +
                        "FROM assignments WHERE instructorEmail = '"+email+"'";
                session.setAttribute("calendarQuery", query);
                }
                ResultSet resultSet = statement.executeQuery((String) session.getAttribute("calendarQuery"));
                while(resultSet.next()){
                    String assignmentName = resultSet.getString("assignmentName");
                    String date = resultSet.getString("dueDate").substring(0,resultSet.getString("dueDate").length()-11);
                    session.setAttribute("calendarDueDate", date);
                    session.setAttribute("calendarAssignmentName", assignmentName);
                    System.out.println(date);

                %>
                    {
                        title : '${calendarAssignmentName}',
                        start : '${calendarDueDate}',
                        end : '${calendarDueDate}',
                        color : 'blue'
                    },<%}
                    connection.close();
            }catch(Exception e){
                    e.printStackTrace();
            }%>
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
    <li class="navLi"><a class="active" href="home.jsp">Home</a></li>
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

    <% if(session.getAttribute("userType").equals("student")){ %>
    <ul class="todoUL">
        <h2>To Do:</h2>
        <div class="toDoItemContainer">
    <%
            try{
            String jdbcURL = "jdbc:sqlserver://titan.cs.weber.edu:10433;database=LMS_RunTime";
            String dbUser = "LMS_RunTime";
            String dbPassword = "password1!";
            String email = (String) session.getAttribute("email");
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection connection = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);

            Statement statement = connection.createStatement();
            String query = "SELECT * FROM registrations INNER JOIN courseList ON courseList.courseID = " +
                            "registrations.courseID INNER JOIN assignments ON assignments.courseID = " +
                            "registrations.courseID  WHERE studentEmail LIKE '" + session.getAttribute("email") +
                            "' AND dueDate >= getDate() ORDER BY dueDate ASC";
            ResultSet resultSet = statement.executeQuery(query);

            while(resultSet.next()){
                    session.setAttribute("courseID", resultSet.getInt("courseID"));
                    session.setAttribute("courseName", resultSet.getString("courseName"));
                    session.setAttribute("assignmentID", resultSet.getString("assignmentID"));
                    session.setAttribute("assignmentName", resultSet.getString("assignmentName"));
                    session.setAttribute("dueDate", resultSet.getDate("dueDate").toString());
    %>

    <li><a href="assignment_main.jsp?assignmentID=${assignmentID}&courseID=${courseID}">${courseName}<br>${assignmentName}<br>Due: ${dueDate}</a><span class="itemX" onclick="deleteItem(this)"> X </span></li>

    <%
            }
            session.removeAttribute("courseID");
            session.removeAttribute("courseName");
            session.removeAttribute("assignmentID");
            session.removeAttribute("assignmentName");
            session.removeAttribute("dueDate");
            connection.close();
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    %>
        </div>

</ul>

<div class="mainContainer">
      <h1>Welcome ${firstName} ${lastName}!</h1>

    <div class="courseContainer">
        <% String userType = (String) session.getAttribute("userType");
            if(userType.equals("student")) {
        %>
          <h2>Courses List</h2><%}%>

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
                String query = "SELECT registrations.courseID, courseList.courseNumber, courseList.courseName, courseList.departmentCode, courseList.monday, courseList.tuesday, courseList.wednesday, courseList.thursday, courseList.friday, courseList.instructorLastName, courseList.startTime, courseList.endTime " +
                        "FROM registrations INNER JOIN courseList ON registrations.courseID = courseList.courseID WHERE registrations.studentEmail = '"+email+"'";
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
                    String courseString3 = days+": "+resultSet.getString("startTime")+ " - "+resultSet.getString("endTime");
                    session.setAttribute("courseString1", courseString1);
                    session.setAttribute("courseString2", courseString2);
                    session.setAttribute("courseString3", courseString3);
                    session.setAttribute("courseID", resultSet.getString("courseID"));%>
            <a href="course_main.jsp?courseID=${courseID}"> <div class="card">
                <div class="container">
                    <h4><b>${courseString1}</b></h4>
                    <p>${courseString2}</p>
                    <p>${courseString3}</p>
                </div>
            </div></a>
            <%}
            connection.close();
            }catch(Exception e){
                    e.printStackTrace();
            }
            %>
        </div>
        <% if(userType.equals("instructor")) {
        %>
        <h2>Courses List</h2><%}%>

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
                String query = "SELECT courseID, courseNumber, courseName, departmentCode, monday, tuesday, wednesday, thursday, friday,  startTime, endTime " +
                "FROM courseList  WHERE instructorEmail = '"+email+"'";
                ResultSet resultSet = statement.executeQuery(query);

                while(resultSet.next()){
                String courseString1 = resultSet.getString("departmentCode")+" "+resultSet.getString("courseNumber")+" "+resultSet.getString("courseName");
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
                String courseString3 = days+": "+resultSet.getString("startTime")+ " - "+resultSet.getString("endTime");
                session.setAttribute("courseString1", courseString1);
                session.setAttribute("courseString3", courseString3);
                session.setAttribute("courseID", resultSet.getString("courseID"));%>
            <a href="course_main.jsp?courseID=${courseID}"> <div class="card">
                <div class="container">
                    <h4><b>${courseString1}</b></h4>
                    <p>${courseString2}</p>
                    <p>${courseString3}</p>
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
