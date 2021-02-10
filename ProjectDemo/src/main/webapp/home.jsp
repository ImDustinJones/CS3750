<%@ page import="com.example.ProjectDemo.Users" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang='en'>
<head>
    <meta charset='utf-8' />
    <link href='fullcalendar/main.css' rel='stylesheet' />
    <link href='home.css' rel='stylesheet' />
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
                        color : 'yellow'
                    },
                    {
                        title : 'Place Holder Assignment 5',
                        start : '2021-02-04',
                        end : '2021-02-04',
                        color : 'yellow'
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
  <h1>Welcome ${firstName} ${lastName}!</h1>

  <h2>Your Courses</h2>
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
  <h2>Learning Calendar</h2>
  <div id='calendar' style="width: 40%;"></div> <%--Comment this out to hide the calendar for testing if it's annoying--%>




  <div id='logoText'>
      <p>RunTime Error's LMS</p>
  </div>
</body>
</html>
