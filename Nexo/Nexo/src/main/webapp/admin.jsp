<%@ page import="com.mycompany.mycart.entities.Category" %>
<%@ page import="com.mycompany.nexo.dao.CategoryDao" %>
<%@ page import="com.mycompany.nexo.helper.FactoryProvider" %>
<%@ page import="java.util.List" %>

<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Admin Page</title>
    <link rel="stylesheet" href="css/style.css">
    <%@include file="components/common_css_js.jsp" %>
</head>
<body>

    
    
    <%@include file="components/navbar.jsp" %>
  
         <%@include file="components/message.jsp" %>
   <div style="display: flex; justify-content: space-around; margin: 5px;">

    <div class="card_section" style="margin-left: 15px; margin-right: 15px;width: 400px">
        <a href="#" class="card_box1" id="adminDashboardLink"  style="margin: 5px; text-decoration: none;color: black">
            <img src="img/team.png" alt="user">
            <div class="text">
                <h3>234</h3>
                <p>USERS</p>
            </div>
        </a>
    </div>

    <div class="card_section" style="margin-left: 15px; margin-right: 15px;width: 400px">
        <a href="categories.jsp" class="card_box1" id="categories" style="margin: 5px; text-decoration: none;color: black">
            <img src="img/shopping-cart.png" alt="user" class="card_icon_image">
            <div class="text">
                <h2>05</h2>
                <p>CATEGORIES</p>
            </div>
        </a>
    </div>

    <div class="card_section" style="margin-left: 15px; margin-right: 15px;width: 400px">
        <a href="products.jsp" id="Products" class="card_box1" style="margin: 5px; text-decoration: none;color: black">
            <img src="img/online-shopping.png" alt="user">
            <div class="text">
                <h2>150</h2>
                <p>PRODUCTS</p>
            </div>
        </a>
    </div>

</div>

        
       
       
    
    <div class="card_section" style="margin-top: 0px;margin-bottom: 0">
        <div class="card_box">
            <img src="img/add-cart.png" alt="user"><br><br>
            <h3>ADD NEW CATEGORY</h3>
            <p>Simply click here to seamlessly introduce new product categories to your store.</p>
            <br><button data-bs-toggle="modal" data-bs-target="#staticBackdrop" id="AddnewCategory">Click Here</button>
        </div>
        
        <div class="card_box" style="margin-top: 0px;margin-bottom: 0">
            <img src="img/add-package.png" alt="user"><br><br>
            <h3>ADD NEW PRODUCT</h3>
            <p>Easily expand your product range with a simple click. Seamlessly add new items to your inventory.</p><br>
            <button type="button" id="AddnewProduct"  data-bs-toggle="modal" data-bs-target="#productModal" data-bs-whatever="@mdo">Click Here</button>
        </div>
    </div>

  
    <div class="modal fade" id="staticBackdrop" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true" style="justify-content: center">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h1 class="modal-title fs-5" id="staticBackdropLabel">Fill Category Details</h1>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <form action="ProductOparetionServlet">
                        <input type="hidden" name="operation" value="addcategory"/>
                        <div class="mb-3">
                            <input type="hidden" name="operation" value="addcategory">
                            <input type="text" class="form-control" name="catTitle" style="border: solid 1px gray" placeholder="Enter Category title" required>
                        </div>
                        <div class="mb-3">
                            <textarea class="form-control" name="catDesc" style="border: solid 1px gray; resize: none; height: 350px" placeholder="Enter Category Description"></textarea>
                        </div>
                        <button type="submit" class="btn btn-primary" style="background-color: black; border: none">Add Category</button>
                    </form>
                </div>
      
            </div>
        </div>
    </div>

    <div class="modal fade" id="productModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true" style="margin-top: 20px">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h1 class="modal-title fs-5" id="exampleModalLabel" style="font-size:15px;">Add new Product</h1>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <form action="ProductOparetionServlet" method="post" enctype="multipart/form-data">
                         
                        <input type="hidden" name="operation" value="addproduct"/>
                  
                        <div class="mb-3">
                            <input type="hidden" name="operation" value="addproduct">
                            <input type="text" class="form-control" name="pName" style="border: solid 1px gray;font-size:15px" placeholder="Enter Product Name" required>
                        </div>
                        <div class="mb-3">
                            <textarea class="form-control" name="pDesc" style="border: solid 1px gray;font-size:15px;resize: none;" placeholder="Enter Product Description"></textarea>
                        </div>
                        <div class="mb-3">
                            <label for="productPhoto" style="font-size:15px">Product Photo:</label>
                            <input type="file" class="form-control" name="pPhoto" style="border: solid 1px gray;font-size:15px" placeholder="Choose Product Photo">
                        </div>
                        <div class="mb-3">

                            <input type="text" class="form-control" name="pPrice" style="border: solid 1px gray;font-size:15px" placeholder="Enter Product Price" required>
                        </div>
                        <div class="mb-3">
                            <input type="number" class="form-control" name="pDiscount" style="border: solid 1px gray;font-size:15px" placeholder="Enter Product Discount">
                        </div>
                        <% CategoryDao cdao=new CategoryDao(FactoryProvider.getFactory()); List<Category> list=cdao.getCategories(); %>
                        <div class="mb-3">
                            <select class="form-select" id="calist" name="categoryId" style="border: solid 1px gray;font-size:15px" required>
                                <% for(Category c : list) { %>
                                    <option value="<%= c.getCategoryId() %>"><%= c.getCategoryTitle() %></option>
                                <% } %>
                            </select>
                        </div>
                            
                        <div class="mb-3">
                            <input type="number" class="form-control" name="pQuantity" style="border: solid 1px gray;font-size:15px" placeholder="Enter Product Quantity">
                        </div>
            
                        
            
            
                        <button type="submit" class="btn btn-primary" style="background-color: black; border: none" id="addproductbtn">Add Product</button>
                    </form>

                </div>
      
            </div>
        </div>
    </div>


</html>
