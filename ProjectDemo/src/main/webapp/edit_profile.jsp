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
<jsp:include page="/RetrieveImageServlet" />
    <ul class="navUl">
        <li class="navLi"><a class="active" href="edit_profile.jsp">Profile</a></li>
        <li class="navLi"><a href="home.jsp">Home</a></li>
        <li class="navLi"><a href="#DummyN2">Dummy</a></li>
        <li class="navLi"><a href="#DummyN3">Dummy</a></li>
    </ul>
    <div class="mainContainer">
    <!-- grab these from database! -->
        <h1>Your Profile</h1>
        <div class = "profileContainer">
            <div class = "row">
                <div class = "column">
                    <!--<img src="img/DefaultPFP.png" alt="default profile picture" width="170" height="170"/>-->
                    <img src="data:image/jpg;base64,${PFP}" width="170" height="170"/>
                </div>
                <div class = "column">
                    <p>Your First Name: ${firstName}</p>
                    <p>Your Last Name: ${lastName}</p>
                    <p>Your Address: ${address}</p>
                    <p>Your zip: ${zip}</p>
                    <p>Bio: ${bio}</p>
                </div>
                <div class = "column">
                    <p>Your Email: ${email}</p>
                    <p>Your Phone Number: ${phoneNumber}</p>
                    <p>Your State: ${state}</p>
                    <p>Your City: ${city}</p>
                    <a href="https://www.linkedin.com/">LinkedIn</a>
                    <a href="https://www.facebook.com/">Facebook</a>
                    <a href="https://twitter.com/">Twitter</a>
                </div>
            </div>
            <div class="popup">
                <div class="popup__container">
                    <button type="button" class="close-button"></button>
                    <h2 class="popup__title">Edit Profile</h2>
                    <%--Had to Add second form for my servlet because only 1 servlet can be connected to a single form. Sorry it doesn't look good. I will try to fix after I get my cod working--%>
                    <form method="post" action="imageUploadServlet" enctype="multipart/form-data">
                        <label for ="profilePic">Profile Picture: </label>
                        <input type = "file" name = "profilePic" id = "profilePic"><br>
                        <input type="submit" value="Submit Image"> <br>
                    </form>
                    <form form method="post" action="EditProfileDAO">
                        <label for = "email" style="text-align: center;">Email: ${email}</label>
                        <input type="hidden" name="email" id="email" value="${email}"><br>
                        <label for="firstName">First Name: </label>
                        <input type="text" name="firstName" id="firstName" placeholder="${firstName}"> <br>
                        <label for="lastName">Last Name: </label>
                        <input type="text" name="lastName" id="lastName" placeholder="${lastName}"> <br>
                        <label for="phoneNumber">Phone: </label>
                        <input type="number" name="phoneNumber" id="phoneNumber" placeholder="${phoneNumber}"><br>

                        <label for="address">Address: </label>
                        <input type="text" name="address" id="address" placeholder="${address}"><br>
                        <label for="city">City: </label>
                        <input type="text" name="city" id="city" placeholder="${city}"><br>
                        <label for="state">State: </label>
                        <input type="text" name="state" id="state" placeholder="${state}"><br>
                        <label for="zip">Zip: </label>
                        <input type="text" name="zip" id="zip" placeholder="${zip}"><br>
                        <label for ="bioBox">Bio: </label>
                        <textarea id = "bioBox" name = "bioBox" rows = "5" cols="50"></textarea><br>

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
