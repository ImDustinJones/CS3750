<%--
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
%>
<jsp:include page="/display-assignment-page" />
<div class = mainContainer>
    <h1>${theAssignment.assignmentName}</h1>

    <p>Due Date: ${theAssignment.dueDate}</p>
    <p>${theAssignment.assignmentDescription}</p>
    <p>Points: ${theAssignment.points}</p>

    <p>Submission Type: ${theAssignment.submissionType}</p>
    <c:if test ="${theAssignment.submissionType == 'file'}">
        <form method="post">
            <label for ="fileSubmission">Add A File: </label>
            <input type = "file" name = "fileSubmission" id = "fileSubmission"><br>
            <input type="submit" value="Submit File"> <br>
        </form>
    </c:if>
    <c:if test ="${theAssignment.submissionType == 'text'}">
        <form method="post">
            <label for ="textSubmission">Enter Text: </label><br>
            <textarea id = "textSubmission" name = "textSubmission" rows = "30" cols="100"></textarea><br>
            <input type="submit" value="Submit File"> <br>
        </form>
    </c:if>



</div>


</body>
</html>
