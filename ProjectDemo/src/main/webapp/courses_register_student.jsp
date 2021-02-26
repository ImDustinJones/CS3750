<%@ page import="java.awt.*" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.example.ProjectDemo.Courses" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.DriverManager" %>
<%@ page import="java.sql.Statement" %>
<%@ page import="java.sql.ResultSet" %><%--
  Created by IntelliJ IDEA.
  User: Dustin
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href='navigationbar.css' rel='stylesheet'/>
    <link href='home.css' rel='stylesheet'/>
    <title>Courses</title>
    <style>
        * {
            box-sizing: border-box;
        }

        #myInput {
            background-position: 10px 10px;
            background-repeat: no-repeat;
            width: 100%;
            font-size: 16px;
            padding: 12px 20px 12px 40px;
            border: 1px solid #ddd;
            margin-bottom: 12px;
        }

        #myTable {
            border-collapse: collapse;
            width: 100%;
            border: 1px solid #ddd;
            font-size: 18px;
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
        /* Dropdown Button */
        .dropbtn {
            background-color: #f6ae2d;
            color: white;
            padding: 16px;
            font-size: 16px;
            border: none;
        }

        /* The container <div> - needed to position the dropdown content */
        .dropdown {
            position: fixed;
            display: inline-block;
            right: 10%;
            top: 12%;
        }

        /* Dropdown Content (Hidden by Default) */
        .dropdown-content {
            display: none;
            position: absolute;
            right: 0;
            background-color: #f1f1f1;
            min-width: 160px;
            box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
            z-index: 1;
        }

        /* Links inside the dropdown */
        .dropdown-content a {
            color: black;
            padding: 12px 16px;
            text-decoration: none;
            display: block;
        }

        /* Change color of dropdown links on hover */
        .dropdown-content a:hover {background-color: #ddd;}

        /* Show the dropdown menu on hover */
        .dropdown:hover .dropdown-content {display: block;}
        
    </style>
</head>
<body>
<ul class="navUl">
    <li class="navLi"><a href="home.jsp">Home</a></li>
    <li class="navLi"><a class="active" href="edit_profile.jsp">Profile</a></li>
    <li class="navLi"><a href="courses_register.jsp">Courses</a></li>
    <li class="navLi"><a href="#DummyN3">Dummy</a></li>
</ul>
<div class="mainContainer">
    <div class = "profileContainer">
        <h1>Courses</h1>

        <input type="text" id="myInput" onkeyup="myFunction(0)" placeholder="Search for courses.." title="Type in a course name">

        <table id="myTable">
            <tr class="header">
                <th style="width:60%;">Course Name</th>
                <th style="width:40%;">Department</th>
                <th style="width:40%;">Course Number</th>
                <th style="width:40%;">Instructor</th>
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
            String query = "SELECT * FROM courseList";
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()){
            String courseName =  resultSet.getString("courseName");
            String department =  resultSet.getString("departmentCode");
            String courseNumber =  resultSet.getString("courseNumber");
            String instructor =  resultSet.getString("instructorLastName");
            session.setAttribute("courseTable1", courseName);
            session.setAttribute("courseTable2", department);
            session.setAttribute("courseTable3", courseNumber);
            session.setAttribute("courseTable4", instructor);
%>
            <tr>
                <td>${courseTable1}</td>
                <td>${courseTable2}</td>
                <td>${courseTable3}</td>
                <td>${courseTable4}</td>
            </tr>
            <%}
                connection.close();
            }catch(Exception e){
                e.printStackTrace();
            }
            %>
        </table>

        <div class="dropdown">
            <button class="dropbtn">Filter</button>
            <div class="dropdown-content">
                <%
                try{
                String jdbcURL = "jdbc:sqlserver://titan.cs.weber.edu:10433;database=LMS_RunTime";
                String dbUser = "LMS_RunTime";
                String dbPassword = "password1!";
                String email = (String) session.getAttribute("email");
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                Connection connection = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
                Statement statement = connection.createStatement();
                String query = "SELECT DISTINCT departmentCode FROM courseList";
                ResultSet resultSet = statement.executeQuery(query);
                while(resultSet.next()){
                String department =  resultSet.getString("departmentCode");
                session.setAttribute("filterDept", department);
                %>
                <a id="myLink1" href="#" onclick="dropDownFunction('${filterDept}')">${filterDept}</a>
                <%}
                    connection.close();
                }catch(Exception e){
                    e.printStackTrace();
                }
                %>
            </div>
        </div>

        <script>
            function myFunction(int) {
                var input, filter, table, tr, td, i, txtValue;
                input = document.getElementById("myInput");
                filter = input.value.toUpperCase();
                table = document.getElementById("myTable");
                tr = table.getElementsByTagName("tr");
                for (i = 0; i < tr.length; i++) {
                    td = tr[i].getElementsByTagName("td")[int];
                    if (td) {
                        txtValue = td.textContent || td.innerText;
                        if (txtValue.toUpperCase().indexOf(filter) > -1) {
                            tr[i].style.display = "";
                        } else {
                            tr[i].style.display = "none";
                        }
                    }
                }
            }
            function dropDownFunction(String){
                var filter, table, tr, td, i, txtValue;
                filter = String;
                table = document.getElementById("myTable");
                tr = table.getElementsByTagName("tr");
                for (i = 0; i < tr.length; i++) {
                    td = tr[i].getElementsByTagName("td")[1];
                    if (td) {
                        txtValue = td.textContent || td.innerText;
                        if (txtValue.toUpperCase().indexOf(filter) > -1) {
                            tr[i].style.display = "";
                        } else {
                            tr[i].style.display = "none";
                        }
                    }
                }
            }
        </script>
    </div>
</div>
</body>
</html>
