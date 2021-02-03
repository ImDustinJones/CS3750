<%--
  Created by IntelliJ IDEA.
  User: John
  Date: 2/1/2021
  Time: 9:30 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Home Page</title>
</head>
<body>
<%
    String firstName = (String)session.getAttribute("firstName");
    String lastName = (String)session.getAttribute("lastName");
%>
<h1>Welcome <%=firstName%> <%=lastName%>!</h1>

</body>
</html>
