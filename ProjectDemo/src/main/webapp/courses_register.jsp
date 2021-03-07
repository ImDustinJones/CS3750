<%--
  Created by IntelliJ IDEA.
  User: johnn
  Date: 2/23/2021
  Time: 12:06 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<html>
<head>
    <link href='navigationbar.css' rel='stylesheet'/>
    <link href='courses_register.css' rel='stylesheet'/>
    <title>Courses</title>

</head>
<body>
<jsp:include page="/display-instructors-course" />
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

<h1>Your Courses</h1>

<div id="coursesContainer">
        <table id="myTable1">
            <tr class="header">
                <th style="width:20%;">Course Name</th>
                <th style="width:10%;">Department</th>
                <th style="width:10%;">Course Number</th>
                <th style="width:40%;">Description</th>
                <th style="width:10%;">Credit Hours</th>
                <th style="width:10%;">Mon</th>
                <th style="width:10%;">Tue</th>
                <th style="width:10%;">Wed</th>
                <th style="width:10%;">Thu</th>
                <th style="width:10%;">Fri</th>
                <th style="width:10%;">Start Time</th>
                <th style="width:10%;">End Time</th>
                <th style="width:10%;">Capacity</th>
                <th style="width:10%;"></th>
            </tr>

            <tr> <c:forEach items = "${courses}" var = "course" >
                    <td>${course.courseName}</td>
                    <td>${course.departmentCode}</td>
                    <td>${course.courseNumber}</td>
                    <td>${course.courseDescription}</td>
                    <td>${course.creditHours}</td>
                    <c:if test ="${course.monday == 1}">
                        <td>X</td>
                    </c:if>
                    <c:if test ="${course.monday == 0}">
                        <td>-</td>
                    </c:if>
                    <c:if test ="${course.tuesday == 1}">
                        <td>X</td>
                    </c:if>
                    <c:if test ="${course.tuesday == 0}">
                        <td>-</td>
                    </c:if>
                    <c:if test ="${course.wednesday == 1}">
                        <td>X</td>
                    </c:if>
                    <c:if test ="${course.wednesday == 0}">
                        <td>-</td>
                    </c:if>
                    <c:if test ="${course.thursday == 1}">
                        <td>X</td>
                    </c:if>
                    <c:if test ="${course.thursday == 0}">
                        <td>-</td>
                    </c:if>
                    <c:if test ="${course.friday == 1}">
                        <td>X</td>
                    </c:if>
                    <c:if test ="${course.friday == 0}">
                        <td>-</td>
                    </c:if>
                <td>${course.startTime}</td>
                <td>${course.endTime}</td>
                <td>${course.studentCapacity}</td>
                <%
                    if(userTypeVar.equals("instructor"))
                    {%>

                <td><button class = "my_button" value = "${course.courseID}" onclick= "showDiv(this.value)">Edit Course</button><br></td>
            </tr>
                <tr id = "${course.courseID}" style="display: none">
                    <td colspan = "14">
                        <form method="post" action="${pageContext.request.contextPath}/edit-course">
                    <label for ="CourseID1">CourseID (READ ONLY)</label>
                    <input type ="number" name = "CourseID1" id = "CourseID1" value = "${course.courseID}" placeholder = "${course.courseID}" readonly><br>
                    <label for="CourseNumber1">Course Number: </label>
                    <input type="text" name="CourseNumber1" id="CourseNumber1" placeholder = "${course.courseNumber}"><br>

                    <label for="CourseName1">Course Name: </label>
                    <input type="text" name="CourseName1" id="CourseName1" placeholder = "${course.courseName}"><br>

                    <label for="Department1">Department: </label>
                            <select name = "Department1" id = "Department1">
                                <option value = "CS">CS</option>
                                <option value = "ENGL">ENGL</option>
                                <option value = "MATH">MATH</option>
                                <option value = "BIO">BIO</option>
                                <option value = "HIST">HIST</option>
                            </select><br>

                    <label for="CreditHours1">Credit Hours: </label>
                    <input type="number" name="CreditHours1" id="CreditHours1" placeholder = "${course.creditHours}"><br>

                    <label for="Capacity1">Capacity: </label>
                    <input type="Number" name="Capacity1" id="Capacity1" placeholder = "${course.studentCapacity}"><br>

                    <label for="Monday1">Monday</label>
                    <input type="checkbox" id="Monday1" name="Monday1" value="1"><br>

                    <label for="Tuesday1">Tuesday</label>
                    <input type="checkbox" id="Tuesday1" name="Tuesday1" value="1" ><br>

                    <label for="Wednesday1">Wednesday</label>
                    <input type="checkbox" id="Wednesday1" name="Wednesday1" value="1"><br>

                    <label for="Thursday1">Thursday</label>
                    <input type="checkbox" id="Thursday1" name="Thursday1" value="1"><br>

                    <label for="Friday1">Friday</label>
                    <input type="checkbox" id="Friday1" name="Friday1" value="1"><br>

                    <label for="StartTime1">Start Time:</label>
                    <input type="time" name="StartTime1" id="StartTime1" placeholder = "${course.startTime}"> <br>

                    <label for="EndTime1">End Time:</label>
                    <input type="time" name="EndTime1" id="EndTime1" placeholder = "${course.endTime}"> <br>

                    <label for ="CourseDescription1">Course Description: </label>
                    <textarea id = "CourseDescription1" name = "CourseDescription1" rows = "10" cols="50" placeholder = "${course.courseDescription}"></textarea><br>

                    <input type="submit" value="Update">
                </form>
                    </td>
                </tr>
            <%}
                else { %>
                </tr>
            <% }
            %>
            </c:forEach>
        </table
</div>

<%if(userTypeVar.equals("instructor")) { // only instructors need this button

%>
    <div class = "popup">
        <div class="popup__container">
            <button type="button" class="close-button"></button>
            <h2 class="popup__title">Add Course</h2>
            <form method="post" action="${pageContext.request.contextPath}/add-course">
                <label for="CourseNumber">Course Number: </label>
                <input type="text" name="CourseNumber" id="CourseNumber"><br>

                <label for="CourseName">Course Name: </label>
                <input type="text" name="CourseName" id="CourseName"><br>

                <label for="Department">Department: </label>
                <select name = "Department" id = "Department">
                    <option value = "CS">CS</option>
                    <option value = "ENGL">ENGL</option>
                    <option value = "MATH">MATH</option>
                    <option value = "BIO">BIO</option>
                    <option value = "HIST">HIST</option>
                </select><br>

                <label for="CreditHours">Credit Hours: </label>
                <input type="number" name="CreditHours" id="CreditHours"><br>

                <label for="Capacity">Capacity: </label>
                <input type="Number" name="Capacity" id="Capacity"><br>

                <label for="Monday">Monday</label>
                <input type="checkbox" id="Monday" name="Monday" value="1"><br>

                <label for="Tuesday">Tuesday</label>
                <input type="checkbox" id="Tuesday" name="Tuesday" value="1"><br>

                <label for="Wednesday">Wednesday</label>
                <input type="checkbox" id="Wednesday" name="Wednesday" value="1"><br>

                <label for="Thursday">Thursday</label>
                <input type="checkbox" id="Thursday" name="Thursday" value="1"><br>

                <label for="Friday">Friday</label>
                <input type="checkbox" id="Friday" name="Friday" value="1"><br>

                <label for="StartTime">Start Time:</label>
                <input type="time" name="StartTime" id="StartTime"> <br>

                <label for="EndTime">End Time:</label>
                <input type="time" name="EndTime" id="EndTime"> <br>

                <label for ="CourseDescription">Course Description: </label>
                <textarea id = "CourseDescription" name = "CourseDescription" rows = "10" cols="50"></textarea><br>

                <input type="submit" value="Add">
            </form>
        </div>
    </div>
    <div style = "text-align: center">
        <button class = "popup-button">Add Course</button>
    </div>
<%}
%>

<script src="editprofile.js"></script>
<script src="courses_register.js"></script>
</body>
</html>