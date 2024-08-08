<%-- 
    Document   : index
    Created on : Jun 4, 2024, 5:00:51 PM
    Author     : HP
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="css/style.css">
        <%@include file="components/common_css_js.jsp" %>
    </head>
    <body>
        <%@include file="components/navbar.jsp" %>
    
        <div class="form_container">
               
            <div class="left-side">
               
                <img src="img/Shopping_1.png" alt="alt"/>
                <h2>NE<span>X</span>O</h2>
                <p>Welcome to Nexo, your one-stop online shop for all things amazing!</p>
                <!-- Add any additional text or content here -->
            </div>
            <div class="right-side">
                  
                <form action="RegisterServlet" method="post">
                 <%@include file="components/message.jsp" %>
                    <div class="mb-3">
                        <label for="userName" class="form-label">Name</label>
                        <input type="text" class="form-control" id="userName" name="userName" style="border: solid 1px black" required>
                    </div>
                    <div class="mb-3">
                        <label for="userEmail" class="form-label">Email address</label>
                        <input type="email" class="form-control" id="userEmail" name="userEmail" aria-describedby="emailHelp" style="border: solid 1px black" required>
                 
                    </div>
                    <div class="mb-3">
                        <label for="userPassword" class="form-label">Password</label>
                        <input type="password" class="form-control" id="userPassword" name="userPassword" style="border: solid 1px black" required>
                    </div>
                    <div class="mb-3">
                        <label for="userPhone" class="form-label">Phone</label>
                        <input type="text" class="form-control" id="userPhone" name="userPhone" style="border: solid 1px black">
                    </div>
                    <div class="mb-3">
                        <label for="userAddress" class="form-label">Address</label>
                        <textarea class="form-control" id="userAddress" name="userAddress" style="border: solid 1px black; resize: none;"></textarea>
                    </div>
                    <button class="btn-primary2">Reset</button>
                    <button type="submit" class="btn-primary1" id="registerButton">Submit</button>
                </form>
            </div>
        </div>
    </body>
</html>
