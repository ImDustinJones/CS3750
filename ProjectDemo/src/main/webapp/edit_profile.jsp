<%--
  Created by IntelliJ IDEA.
  User: johnn
  Date: 2/17/2021
  Time: 11:43 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href='navigationbar.css' rel='stylesheet'/>
    <link href='home.css' rel='stylesheet'/>
    <title>Edit Profile</title>
</head>
<body>
    <ul class="navUl">
        <li class="navLi"><a class="active" href="edit_profile.jsp">Profile</a></li>
        <li class="navLi"><a href="#DummyN1">Dummy</a></li>
        <li class="navLi"><a href="#DummyN2">Dummy</a></li>
        <li class="navLi"><a href="#DummyN3">Dummy</a></li>
    </ul>
    <div class="mainContainer">
    <!-- grab these from database! -->
        <h1>Your Profile</h1>
        <div class = "profileContainer">
            <div class = "row">
                <div class = "column">
                    <img src="img/Bell.png" alt="notification bell" width="170" height="170"/>
                </div>
                <div class = "column">
                    <p>Your First Name</p>
                    <p>Your Last Name</p>
                    <p>This is your bio</p>
                </div>
                <div class = "column">
                    <p>Your Email</p>
                    <p>Your Phone Number</p>
                    <a>LinkedIn</a>
                    <a>Facebook</a>
                    <a>Twitter</a>
                </div>
            </div>
            <div class="popup">
                <div class="popup__container">
                    <button type="button" class="close-button"></button>
                    <h2 class="popup__title">Edit Profile</h2>
                    <form>
                        <label for ="profilePic">Profile Picture: </label>
                        <input type = "file" name = "profilePic" id = "profilePic"><br>
                        <label for="firstName">First Name: </label>
                        <input type="text" name="firstName" id="firstName"> <br>
                        <label for="lastName">Last Name: </label>
                        <input type="text" name="lastName" id="lastName"> <br>
                        <label for="email">Email: </label>
                        <input type="text" name="email" id="email"> <br>
                        <label for="phoneNum">Phone: </label>
                        <input type="number" name="phoneNum" id="phoneNum"><br>
                        <label for ="bioBox">Bio: </label>
                        <textarea id = "bioBox" name = "bioBox" rows = "5" cols="50">Put your Bio Here</textarea><br>

                        <input type="submit" value="Apply">
                    </form>
                </div>
            </div>
            <div style = "text-align: center">
                <button class = "popup-button">Edit Profile</button>
            </div>
        </div>
        <script src="editprofile.js"></script>
    </div>
</body>
</html>
