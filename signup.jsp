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
    <form method="post" action="/home.jsp">
        <label for="firstName">First Name: </label>
        <input type="text" name="firstName" id="firstName"> <br>
        <label for="lastName">Last Name: </label>
        <input type="text" name="lastName" id="lastName"> <br>
        <label for="birthDate">Birth Date: </label>
        <input type="text" name="birthDate" id="birthDate"> <br>
        <label for="email">Email: </label>
        <input type="text" name="email" id="email"> <br>
        <label for="confirmPassword">Password: </label>
        <input type="text" name="confirmPassword" id="confirmPassword"> <br>

        <input type="radio" name="userType" id="studentRadioBtn">
        <label for="studentRadioBtn">Student</label> <br>
        <input type="radio" name="userType" id="instructorRadioBtn">
        <label for="instructorRadioBtn">Instructor </label> <br>
        <input type="submit" value="Sign Up">
    </form>
</body>
</html>
