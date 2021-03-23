<%--
  Created by IntelliJ IDEA.
  User: johnn
  Date: 3/17/2021
  Time: 11:26 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href='navigationbar.css' rel='stylesheet'/>
    <link href='account_balance.css' rel='stylesheet'/>
    <title>Student Account Balance</title>
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
    <li class="navLi"><a href="courses_register.jsp">My Courses</a></li>
    <li class="navLi"><a class="active" href="account_balance.jsp">Account Balance</a></li>
    <%}
    else { %>
    <li class="navLi"><a href="courseRegisterCheckServlet"> My Courses</a></li>
    <%  }
    %>
</ul>
<jsp:include page="/account-balance" />
    <div class = mainContainer>
        <h1>Account Balance</h1>
        <p>Total Credits: ${studentsCredits}</p>
        <p>Total Payable Account Balance: $${studentTotal}.00</p>
        <form action="${pageContext.request.contextPath}/PaymentProcessServlet" method="post">
            <label for="CardholderName">Name: </label>
            <input type="text" name="CardholderName" id="CardholderName"><br>

            <label for="CardNumber">Card Number: </label>
            <input type="text" name="CardNumber" id="CardNumber"><br>

            <label for="cardMonth">Month: </label>
            <select name = "cardMonth" id = "cardMonth">
                <option value = "1">1 - January</option>
                <option value = "2">2 - February</option>
                <option value = "3">3 - March</option>
                <option value = "4">4 - April</option>
                <option value = "5">5 - May</option>
                <option value = "6">6 - June</option>
                <option value = "7">7 - July</option>
                <option value = "8">8 - August</option>
                <option value = "9">9 - September</option>
                <option value = "10">10 - October</option>
                <option value = "11">11 - November</option>
                <option value = "12">12 - December</option>

            </select>

            <label for="cardYear">Year: </label>
            <select name = "cardYear" id = "cardYear">
                <option value = "2021">2021</option>
                <option value = "2022">2022</option>
                <option value = "2023">2023</option>
                <option value = "2024">2024</option>
                <option value = "2025">2025</option>
                <option value = "2026">2026</option>
                <option value = "2027">2027</option>
            </select><br>

            <label for="CVC">CVC: </label>
            <input type="text" name="CVC" id="CVC"><br>

            <label for="AmountToPay">Amount You Would Like To Pay: </label>
            <input type="text" name="AmountToPay" id="AmountToPay"><br>

            <button type="submit" class="btn">Pay</button>
        </form>
    </div>
</body>
</html>
