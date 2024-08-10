<%@ page import="java.util.List" %>
<%@ page import="com.mycompany.mycart.entities.Product" %>
<%@ page import="com.mycompany.nexo.dao.ProductDao" %>
<%@ page import="com.mycompany.nexo.helper.FactoryProvider" %>
<%@ page import="com.mycompany.mycart.entities.Category" %>
<%@ page import="com.mycompany.nexo.dao.CategoryDao" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>All Products</title>
    <%@ include file="components/common_css_js.jsp" %>
    <script>
        function openUpdateModal(productId, productName, productDesc, productPrice, productDiscount, productQuantity, productCategoryId, productPhoto) {
            document.getElementById("updateProductId").value = productId;
            document.getElementById("updatePName").value = productName;
            document.getElementById("updatePDesc").value = productDesc;
            document.getElementById("updatePPrice").value = productPrice;
            document.getElementById("updatePDiscount").value = productDiscount;
            document.getElementById("updatePQuantity").value = productQuantity;
            document.getElementById("updatePPhotoImg").src = 'img/products/' + productPhoto;
            document.getElementById("updatePCategoryId").value = productCategoryId;
            var updateModal = new bootstrap.Modal(document.getElementById('productModal'));
            updateModal.show();
        }
    </script>
</head>
<body>
    <%@ include file="components/navbar.jsp" %>
    <br>
    <h2 style="text-align: center">All Products</h2>
    <br>
    <% 
    ProductDao pdao = new ProductDao(FactoryProvider.getFactory());
    List<Product> productsList;
    String cat = request.getParameter("cat");
    
    if (cat != null && !cat.trim().isEmpty()) {
        try {
            int cid = Integer.parseInt(cat.trim());
            productsList = pdao.getCategoryAllProducts(cid);
        } catch (NumberFormatException e) {
            productsList = pdao.getAllProducts(); // Fallback to all products in case of invalid category ID
        }
    } else {
        productsList = pdao.getAllProducts();
    }

    // Debugging output
    out.println("Products List Size: " + productsList.size() + "<br>");
    for (Product product : productsList) {
        out.println("Product: " + product.getPName() + "<br>");
    }
    %>
    <div class="container">
        <div class="row row-cols-2 row-cols-md-4 g-3">
            <%@ include file="components/message.jsp" %>
            <% if (productsList != null && !productsList.isEmpty()) { %>
                <% for (Product product : productsList) { %>
                    <div class="col">
                        <div class="card h-100" style="width: 250px;">
                            <img src="img/products/<%= product.getPPhoto() %>" class="card-img-top" alt="No Image">
                            <div class="card-body">
                                <h5 class="card-title" style="font-size: 14px;"><%= product.getPName() %></h5>
                                <p class="card-text" style="font-size: 12px;"><%= product.getPDesc() %></p>
                                <div style="display: flex; justify-content: space-between; align-items: center;">
                                    <span>Rs.<%= product.calculateDiscountedPrice() %>/- <span style="font-size: 10px; text-decoration: line-through; color: gray;"><%= product.getPPrice() %></span></span>
                                </div>
                                <br>
                                <div>
                                    <button onclick="openUpdateModal('<%= product.getPId() %>', '<%= product.getPName() %>', '<%= product.getPDesc() %>', '<%= product.getPPrice() %>', '<%= product.getPDiscount() %>', '<%= product.getPQuantity() %>', '<%= product.getCategory().getCategoryId() %>', '<%= product.getPPhoto() %>')" style="background-color:black;color:white;border-radius: 5px; padding: 10px; border: none;">Update</button>
                                    <form action="RemoveProductServlet" method="post" style="display: inline;">
                                        <input type="hidden" name="productId" value="<%= product.getPId() %>">
                                        <button type="submit" id="Removepro" style="background-color:#fe3f35;color:white;border-radius: 5px; padding: 10px; border: none;">Remove</button>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                <% } %>
            <% } else { %>
                <p>No products available</p>
            <% } %>
        </div>
    </div>

    <!-- Modal for updating product -->
    <div class="modal fade" id="productModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true" style="margin-top: 20px">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h1 class="modal-title fs-5" id="exampleModalLabel" style="font-size:15px;">Update Product</h1>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <form action="ProductOperationServlet" method="post" enctype="multipart/form-data">
                        <input type="hidden" name="operation" value="updateproduct"/>
                        <input type="hidden" id="updateProductId" name="productId"/>

                        <div class="mb-3">
                            <input type="text" class="form-control" id="updatePName" name="pName" style="border: solid 1px gray;font-size:15px" placeholder="Enter Product Name" required>
                        </div>
                        <div class="mb-3">
                            <textarea class="form-control" id="updatePDesc" name="pDesc" style="border: solid 1px gray;font-size:15px;resize: none;" placeholder="Enter Product Description"></textarea>
                        </div>
                        <div class="mb-3">
                            <label for="productPhoto" style="font-size:15px">Product Photo:</label>
                            <input type="file" class="form-control" id="updatePPhoto" name="pPhoto" style="border: solid 1px gray;font-size:15px" placeholder="Choose Product Photo">
                            <img id="updatePPhotoImg" src="" alt="Product Photo" style="width: 100px; height: auto; margin-top: 10px;">
                        </div>
                        <div class="mb-3">
                            <input type="text" class="form-control" id="updatePPrice" name="pPrice" style="border: solid 1px gray;font-size:15px" placeholder="Enter Product Price" required>
                        </div>
                        <div class="mb-3">
                            <input type="number" class="form-control" id="updatePDiscount" name="pDiscount" style="border: solid 1px gray;font-size:15px" placeholder="Enter Product Discount">
                        </div>
                        <div class="mb-3">
                            <select class="form-select" id="updatePCategoryId" name="categoryId" style="border: solid 1px gray;font-size:15px" required>
                                <% CategoryDao cdao = new CategoryDao(FactoryProvider.getFactory());
                                   List<Category> list = cdao.getCategories();
                                   for(Category c : list) { %>
                                    <option value="<%= c.getCategoryId() %>"><%= c.getCategoryTitle() %></option>
                                <% } %>
                            </select>
                        </div>
                        <div class="mb-3">
                            <input type="number" class="form-control" id="updatePQuantity" name="pQuantity" style="border: solid 1px gray;font-size:15px" placeholder="Enter Product Quantity">
                        </div>
                        <button type="submit" class="btn btn-primary" style="background-color: black; border: none">Update Product</button>
                    </form>
                </div>
            </div>
        </div>
    </div>

</body>
</html>
