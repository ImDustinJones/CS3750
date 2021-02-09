<%@ page import="com.example.ProjectDemo.Users" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang='en'>
<head>
    <meta charset='utf-8' />
    <link href='fullcalendar/main.css' rel='stylesheet' />
    <script src="https://cdn.jsdelivr.net/npm/fullcalendar-scheduler@5.3.0/main.min.js"></script>
    <script>
        document.addEventListener('DOMContentLoaded', function() {
            var calendarEl = document.getElementById('calendar');
            var calendar = new FullCalendar.Calendar(calendarEl, {
                initialView: 'dayGridMonth'
            });
            calendar.render();
        });
    </script>
    <title>Home Page</title>
</head>
<body>
  <h1>Welcome ${firstName} ${lastName}!</h1>

    <div id='calendar'></div> <%--Comment this out to hide the calendar for testing if it's annoying--%>
</body>
</html>
