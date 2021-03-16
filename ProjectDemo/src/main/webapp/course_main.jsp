<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.DriverManager" %>
<%@ page import="java.sql.Statement" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <link href='navigationbar.css' rel='stylesheet'/>
    <link href='course_main.css' rel='stylesheet'/>
    <%
        String courseIDCourse_main = request.getParameter( "courseID" );
        session.setAttribute("courseID", request.getParameter( "courseID" ));
    %>
    <title>Your Course</title>
</head>
<body> <%
                try {
                    String jdbcURL = "jdbc:sqlserver://titan.cs.weber.edu:10433;database=LMS_RunTime";
                    String dbUser = "LMS_RunTime";
                    String dbPassword = "password1!";
                    String email = (String) session.getAttribute("email");
                    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                    Connection connection = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
                    String userTypeVar = (String) session.getAttribute("userType");


                    String courseID = (String) session.getAttribute("courseID");
                    String lastName = (String) session.getAttribute("lastName");

                    if(userTypeVar.equals("student")) {
                        Statement statement = connection.createStatement();
                        String query = "SELECT registrations.courseID, courseList.courseNumber, courseList.courseName, courseList.departmentCode, courseList.instructorLastName " +
                                "FROM registrations INNER JOIN courseList ON registrations.courseID = courseList.courseID WHERE registrations.studentEmail = '" + email + "' AND registrations.courseID = '" + courseIDCourse_main + "'";
                        ResultSet resultSet = statement.executeQuery(query);
                        System.out.println(resultSet);
                        while (resultSet.next()) {
                            String courseTitleString = resultSet.getString("departmentCode") + " " + resultSet.getString("courseNumber") + " " + resultSet.getString("courseName") + " Instructed By " + resultSet.getString("instructorLastName");
                            session.setAttribute("courseTitleString", courseTitleString);
                        }
                    }
                    else {
                        Statement statement = connection.createStatement();
                        String query = "SELECT courseID, courseNumber, courseName, departmentCode, instructorLastName " +
                                "FROM courseList WHERE instructorEmail = '" + email + "' AND courseID = '" + courseIDCourse_main + "'";
                        ResultSet resultSet = statement.executeQuery(query);
                        System.out.println(resultSet);
                        while (resultSet.next()) {
                            String courseTitleString = resultSet.getString("departmentCode") + " " + resultSet.getString("courseNumber") + " " + resultSet.getString("courseName");
                            session.setAttribute("courseTitleString", courseTitleString);
                        }
                    }
                    connection.close();
                    }catch(Exception e){
                        e.printStackTrace();
                        System.out.println("Something wrong with SQL statement");
                    }

                %>

    <ul class="navUl">
        <li class="navLi"><a href="home.jsp">Home</a></li>
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

<jsp:include page="/display-assignments" />
        <div class="mainContainer">
            <h1>${courseTitleString}</h1>
            <h2>Assignments</h2>

            <table id="myTable2">
                <tr class="header">
                    <th onclick="sortTable(0)">Assignment Name</th>
                    <th style="width:10%;" onclick="sortTableNumbers(1)">Points</th>
                    <th style="width:20%;" onclick="sortTable(2)">Due Date</th>
                    <th style="width:10%;" onclick="sortTable(3)">Submission Type</th>
                    <th style="width:40%;" onclick="sortTable(4)">Description</th>
                    <th style="width:40%;"></th>
                </tr>

                <tr> <c:forEach items = "${assignmentList}" var = "assignment" >
                    <a href = "assignment_main.jsp?assignmentID=${assignment.assignmentID}&courseID=${courseID}"><td>${assignment.assignmentName} </td> </a>
                    <td>${assignment.points}</td>
                    <td>${assignment.dueDate}</td>
                    <td>${assignment.submissionType}</td>
                    <td>${assignment.assignmentDescription}</td>
                    <td> <button class = "my_button" value = "${assignment.assignmentID}" onclick= "showDiv(this.value)">Edit Assignment</button> </td>
                </tr>


                <div style="display: none" id = "${assignment.assignmentID}" class = "editAssignForm">
                    <div class="editAssignFormContainer">
                        <form method="post" action="${pageContext.request.contextPath}/EditAssignmentServlet">
                            <h1>Edit Assignment ${assignment.assignmentName}</h1>
                            <div class="editFormWrap" style="text-align: left">
                                <label for="editAssignName"><b>Assignment Name</b></label>
                                <input type="text" placeholder="${assignment.assignmentName}" name="editAssignName" id="editAssignName" required><br>

                                <label for="editAssignScore"><b>Possible Points</b></label>
                                <input type="text" placeholder="${assignment.points}" name="editAssignScore" id="editAssignScore" required><br>

                                <label for="editAssignDate"><b>Due Date</b></label>
                                <input type="datetime-local" placeholder="${assignment.dueDate}" name="editAssignDate" id="editAssignDate" value="${assignment.dueDate}" required><br>

                                <label><b>Assignment Submission Type</b></label>
                                <div class="editRadioAssignGroup">
                                    <input type="radio" id="editTextRadio" name="editGroup" value="text" required>
                                    <label for="editTextRadio">Text</label>
                                    <input type="radio" id="editFileRadio" name="editGroup" value="file">
                                    <label for="editFileRadio">File</label><br>
                                </div>

                                <label for="editAssignDescription"><b>Description</b></label><br>
                                <textarea placeholder="${assignment.assignmentDescription}" name="editAssignDescription" id="editAssignDescription" required rows="8" cols="50"></textarea>
                                <input type="text" style="display: none" id="assignID" name="assignID" value="${assignment.assignmentID}">
                                <input type="text" style="display: none" id="courseID" name="courseID" value=<%=request.getParameter( "courseID" )%>>
                                <input type="text" style="display: none" id="courseIDMain" name="courseIDMain" value=<%=request.getParameter( "courseID" )%>>


                                <button type="submit" class="btn">Update</button>
                                <button type="button" class="btn cancel" onclick= "showDiv(${assignment.assignmentID})">Close</button>
                            </div>
                        </form>
                    </div>
                </div>




            </c:forEach>
            </table>

            <p style="display: none">The course ID is <%=request.getParameter( "courseID" )%></p>
        </div>


<!-- Some helpful information when pulling assigments
pull courseID using request.getParameter( "courseID" )!
session varible email is the user's email
-->
        <%if((userTypeVar.equals("instructor"))){%>

        <button class="addAssignBtn" onclick = "openAddForm()" > Add New Assignment</button >
           <% }%>
        <div class="addAssignmentForm" id="addAssignmentForm">
            <form method="post" action="${pageContext.request.contextPath}/AssignmentServlet" class="addAssignFormContainer">
                <h1>Create A New Assignment</h1>

                <label for="assignName"><b>Assignment Name</b></label>
                <input type="text" placeholder="Enter assignment name" name="assignName" id="assignName" required>

                <label for="assignScore"><b>Possible Points</b></label>
                <input type="text" placeholder="Enter the max possible points" name="assignScore" id="assignScore" required>

                <label for="assignDate"><b>Due Date</b></label>
                <input type="datetime-local" placeholder="Enter the assignment due date" name="assignDate" id="assignDate" required>

                <label><b>Assignment Submission Type</b></label>
                <div class="radioAssignGroup">
                    <input type="radio" id="textRadio" name="assignGroup" value="text" required>
                    <label for="textRadio">Text</label>
                    <input type="radio" id="fileRadio" name="assignGroup" value="file">
                    <label for="fileRadio">File</label>
                </div>


                <label for="assignDescription"><b>Description</b></label>
                <textarea placeholder="Enter the assignment description" name="assignDescription" id="assignDescription" required rows="8" cols="50"></textarea>
                <input type="text" style="display: none" id="courseIDMain2" name="courseIDMain2" value=<%=request.getParameter( "courseID" )%>>


                <button type="submit" class="createAssignBtn">Create Assignment</button>
                <button type="button" class="createAssignBtn close" onclick="closeAddForm()">Close Form</button>
            </form>
        </div>

<script>
    function openAddForm() {
        document.getElementById("addAssignmentForm").style.display = "block";
    }

    function closeAddForm() {
        document.getElementById("addAssignmentForm").style.display = "none";
    }

    function openEditForm() {
        document.getElementById("editAssignmentForm").style.display = "block";
    }

    function closeEditForm(value) {
        document.getElementById(value).style.display = "none";
    }

    function showDiv(value) {
        if (document.getElementById(value).style.display === "none") {
            document.getElementById(value).style.display = "table-row";
        } else {
            document.getElementById(value).style.display = "none";
        }
    }


</script>

<script src="course_main.js"></script>

</body>
</html>
