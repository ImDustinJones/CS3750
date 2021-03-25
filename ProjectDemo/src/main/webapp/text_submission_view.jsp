<%--
  Created by IntelliJ IDEA.
  User: darkb
  Date: 3/25/2021
  Time: 1:29 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href='navigationbar.css' rel='stylesheet'/>
    <link href='text_submission_view.css' rel='stylesheet'/>
    <%

        session.setAttribute("firstName", request.getParameter("firstName"));
        session.setAttribute("lastName", request.getParameter("lastName"));
        session.setAttribute("assignmentName", request.getParameter("assignmentName"));
        session.setAttribute("submission", request.getParameter("submission"));

    %>
    <title>${lastName}'s ${assignmentName} submission</title>
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
    <li class="navLi"><a href="account_balance.jsp">Account Balance</a></li>
    <%}
    else { %>
    <li class="navLi"><a class="active" href="courseRegisterCheckServlet"> My Courses</a></li>
    <%  }
    %>
</ul>

<div class="mainContainer">

    <h1>${firstName} ${lastName} ${assignmentName} Submission</h1>

    <div class="submissionContainer">
        ${submission}
    </div>

</div>

</body>
</html>
