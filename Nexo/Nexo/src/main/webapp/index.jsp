<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="com.mycompany.nexo.helper.FactoryProvider"%>
<%@ page import="org.hibernate.SessionFactory"%>
<%@ page import="com.mycompany.nexo.dao.ProductDao" %>
<%@ page import="com.mycompany.nexo.dao.CategoryDao" %>
<%@ page import="com.mycompany.mycart.entities.Product" %>
<%@ page import="com.mycompany.mycart.entities.Category" %>
<%@ page import="com.mycompany.nexo.helper.Helper" %>
<%@ page import="java.util.List" %>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Home Page</title>
    <link rel="stylesheet" href="css/style.css">
    <%@include file="components/common_css_js.jsp" %>
    <style>
        .card {
            margin: 5px; /* Reduce the margin around the cards */
        }
        
        .card:hover{
            background-color: #e9eaec;
        }
        
        .card-body {
            padding: 10px; /* Adjust the padding inside the cards if needed */
        }
        body {
            overflow-x: hidden; /* Prevent horizontal scrolling */
        }
    </style>
    <script>
        // Save scroll position before reload
        window.addEventListener('beforeunload', function () {
            localStorage.setItem('scrollPosition', window.scrollY);
        });

        // Restore scroll position after reload
        window.addEventListener('load', function () {
            var scrollPosition = localStorage.getItem('scrollPosition');
            if (scrollPosition) {
                window.scrollTo(0, scrollPosition);
                localStorage.removeItem('scrollPosition');
            }
        });
    </script>
</head>
<body>
    <%@include file="components/navbar.jsp" %>

    <div class="Hero_container">
        <div class="main">NE<span>X</span>O</div>
        <p class="body_text">
            Welcome to Nexo, your one-stop online shop for all things amazing! 
            Discover an extensive range of high-quality products at unbeatable prices. 
            Whether you're looking for the latest tech gadgets, fashionable apparel, or home 
            essentials, Nexo has it all. Shop with confidence, enjoy fast shipping, and experience 
            exceptional customer service. Start your shopping journey with Nexo today – where convenience 
            meets satisfaction!
        </p>
        <a href="user_register.jsp"><button class="main_button">Register Now</button></a> 
    </div>

    <div class="row">
        <% 
        ProductDao pdao = new ProductDao(FactoryProvider.getFactory());
        List<Product> productsList;
        String cat = request.getParameter("cat");
        
        if (cat != null && !cat.trim().isEmpty()) {
            try {
                int cid = Integer.parseInt(cat.trim());
                productsList = pdao.getCategoryAllProducts(cid);
            } catch (NumberFormatException e) {
                productsList = pdao.getAllProducts(); // fallback to all products in case of invalid category ID
            }
        } else {
            productsList = pdao.getAllProducts();
        }
        
        CategoryDao cdao = new CategoryDao(FactoryProvider.getFactory());
        List<Category> categoryList = cdao.getCategories();
        %>

   

        <div class="col-md-2 list-group" style="margin: 30px;margin-top: 40px;width: 350px">
            <a href="index.jsp" class="list-group-item list-group-item-action active" id="allproducts" aria-current="true" style="background-color: #1fb622;border: solid 1px #1fb622;">
                All Products
            </a>
            <% for (Category c : categoryList) { %>
            <a href="?cat=<%= c.getCategoryId() %>" class="list-group-item list-group-item-action" id="categoryName"><%= c.getCategoryTitle() %></a>  
            <% } %>
        </div>
        
        <div class="row row-cols-1 row-cols-md-3 g-4 col-md-8 ml-0">
            <% if (productsList != null && !productsList.isEmpty()) { %>
                <% for (Product product : productsList) { %>
                    <div class="col">
                        <div class="card h-100" style="width: 250px;">
                            <img src="img/products/<%= product.getPPhoto() %>" class="card-img-top" alt="No Image">
                            <div class="card-body">
                                <h5 class="card-title" style="font-size: 14px;"><%= product.getPName() %></h5>
                                <p class="card-text" style="font-size: 12px;"><%= Helper.get10Words(product.getPDesc()) %></p>
                                <div style="display: flex; justify-content: space-between; align-items: center;">
                                    <span>Rs.<%= product.calculateDiscountedPrice() %>/- <span style="font-size: 10px; text-decoration: line-through; color: gray;"><%= product.getPPrice() %></span></span>
                                   
                                   
                                  <button class="btn btn-primary" style="background-color: black; border: none; font-size: 12px" onclick="addToCart(<%= product.getPId() %>, '<%= product.getPName() %>', <%= product.getPPrice() %>, <%= product.getPQuantity() %>)" id="addItemButton">Add to Cart</button>

                                </div>
                         
                            </div>
                        </div>
                    </div>
                <% } %>
            <% } else { %>
                <p>No products available.</p>
            <% } %>
        </div>
    </div>
</body>
</html>