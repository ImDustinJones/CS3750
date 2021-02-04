<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>LMS Project - CS3750</title>
</head>
<body>
<h1>Sign In</h1>

<h2>LOGIN</h2>

<form method="post" action="LoginServlet">
    <label for="email">Email: </label><br>
    <input type="text" id="email" name="email"><br>
    <label for="password">Password: </label><br>
    <input type="text" id="password" name="password"><br>
    <input type="submit" value="Submit">
</form>
<%
    String errorMessage = "";
    if(request.getAttribute("loginResult") == "true")
    {
        errorMessage = "Invalid Username or Password";
    }
%>
<p style ="color:#ff0000"><%= errorMessage%> </p>
<a href="hello-servlet">Hello Servlet</a>
</body>
</html>