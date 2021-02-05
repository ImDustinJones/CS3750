<%--
  Created by IntelliJ IDEA.
  User: johnn
  Date: 2/5/2021
  Time: 12:11 PM
  To change this template use File | Settings | File Templates.
--%>
<%--
  Created by IntelliJ IDEA.
  User: John
  Date: 2/1/2021
  Time: 9:36 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Sign Up</title>
</head>
<body>

<h1>Please Register an Account</h1>

<form action="${pageContext.request.contextPath}/addUser" method="post" >
    <label for="firstName">First Name: </label>
    <input type="text" name="firstName" id="firstName"> <br>
    <label for="lastName">Last Name: </label>
    <input type="text" name="lastName" id="lastName"> <br>
    <label for="birthDate">Birth Date: </label>
    <input type="date" name="birthDate" id="birthDate"><br>
    <label for="email">Email: </label>
    <input type="text" name="email" id="email"> <br>
    <label for="password">Password: </label>
    <input type="text" name="password" id="password"> <br>
    <input type="radio" id="student" name="userType" value="student">
    <label for="student">Student</label><br>
    <input type="radio" id="instructor" name="userType" value="instructor">
    <label for="instructor">Instructor</label><br>
    <input type="submit" value="Sign Up">

    <%
        String errorMessage = "";
        if(request.getAttribute("emailAgeFail") == "true")
        {
            errorMessage = "Invalid Email or Age (Must be 18+)";
        }
    %>
    <p style ="color:#ff0000"><%= errorMessage%> </p>
</form>
</body>
</html>