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
        <title>Login Page</title>
        <link rel="stylesheet" href="css/style.css">
        <%@include file="components/common_css_js.jsp" %>
    </head>
    <body>
        <%@include file="components/navbar.jsp" %>
        
    
        <div class="form_container">
               
            <div class="left-side">
               
            <img src="img/login.png" alt="alt"/>   
          

                <!-- Add any additional text or content here -->
            </div>
            <div class="right-side">
                   
                <form action="LoginServlet" method="post">
                <%@include file="components/message.jsp" %>
                 <h2>Hi there! Log in here...</h2><br>
                    <div class="mb-3">
                        <label for="userEmail" class="form-label">Email address</label>
                        <input type="email" class="form-control" id="userEmail" name="userEmail" aria-describedby="emailHelp" style="border: solid 1px black" required>
                 
                    </div>
                    <div class="mb-3">
                        <label for="userPassword" class="form-label">Password</label>
                        <input type="password" class="form-control" id="userPassword" name="userPassword" style="border: solid 1px black" required>
                    </div>
                 
                 <div><a href="user_register.jsp" style="color: black;font-weight: 600">Register Now</a></div>  
                    
                 <br>
                
                    <button class="btn-primary1" id="loginButton">Login</button>
                </form>
            </div>
        </div>
                 
                 
                 
    </body>
</html>
