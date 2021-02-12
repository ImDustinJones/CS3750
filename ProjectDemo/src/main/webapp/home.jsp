<%@ page import="com.example.ProjectDemo.Users" %>
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


<ul>
    <li><a class="active" href="#profile">Profile</a></li>
    <li><a href="#Dummy">Dummy</a></li>
    <li><a href="#Dummy">Dummy</a></li>
    <li><a href="#Dummy">Dummy</a></li>
</ul>

<div class="mainContainer">
      <h1>Welcome ${firstName} ${lastName}!</h1>

    <div class="courseContainer">
          <h2>Courses List</h2>

        <div class="cardContainer">
            <div class="card">
                  <div class="container">
                      <h4><b>CS1234 Dummy Computer Science</b></h4>
                      <p>Instructor 1</p>
                      <p>Mon/Wed 1:00PM - 2:00PM</p>
                  </div>
              </div>
              <div class="card">
                  <div class="container">
                      <h4><b>CS1234 Dummy Computer Science</b></h4>
                      <p>Instructor 1</p>
                      <p>Mon/Wed 1:00PM - 2:00PM</p>
                  </div>
              </div>
              <div class="card">
                  <div class="container">
                      <h4><b>CS1234 Dummy Computer Science</b></h4>
                      <p>Instructor 1</p>
                      <p>Mon/Wed 1:00PM - 2:00PM</p>
                  </div>
              </div>
              <div class="card">
                  <div class="container">
                      <h4><b>CS1234 Dummy Computer Science</b></h4>
                      <p>Instructor 1</p>
                      <p>Mon/Wed 1:00PM - 2:00PM</p>
                  </div>
              </div>
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
