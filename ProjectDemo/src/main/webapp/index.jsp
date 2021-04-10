<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <link rel='stylesheet' href='signinout.css' type="text/css"/>
    <title>LMS Project - CS3750</title>
</head>
<body>
<h1>Sign In</h1>
<div class="Wrapper">
<div class="Inputs">
    <form method="post" action="LoginServlet">
        <label for="email">Email: </label><br>
        <input type="text" id="email" name="email"><br>
        <label for="password">Password: </label><br>
        <input type="text" id="password" name="password"><br>
        <div class ="signupbtn"><input type="submit" value="Submit"></div>
    </form>
    <% //Getting the setAttribute from servlet and creating an error message
        String errorMessage = "";
        if(request.getAttribute("loginResult") == "true")
        {
            errorMessage = "Invalid Username or Password";
        }
    %>
    <p style ="color:#ff0000"><%= errorMessage%> </p>
    <div class="signupwrap"><a href="signup.jsp">Sign Up</a></div>
</div>
</div>
</body>
</html>