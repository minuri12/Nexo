<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.mycompany.nexo.dao.CategoryDao" %>
<%@ page import="com.mycompany.nexo.helper.FactoryProvider" %>
<%@ page import="com.mycompany.mycart.entities.Category" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Update Categories</title>
    <%@include file="components/common_css_js.jsp" %>

    <script>
        function openUpdateModal(categoryId, categoryTitle, categoryDescription) {
            console.log("Opening modal for category:", categoryId, categoryTitle, categoryDescription);
            document.getElementById('categoryId').value = categoryId;
            document.getElementById('catTitle').value = categoryTitle;
            document.getElementById('catDesc').value = categoryDescription;
            var updateModal = new bootstrap.Modal(document.getElementById('categoryModal'));
            updateModal.show();
        }
    </script>
</head>
<body>
<%@include file="components/navbar.jsp" %>

<div class="container">
    <br><h2 style="text-align: center">Categories</h2>
    <br>
    <table class="table table-striped">
        <thead>
            <tr>
                <th>Name</th>
                <th>Description</th>
                <th>Action</th>
            </tr>
        </thead>
        <tbody>
            <% CategoryDao cdao = new CategoryDao(FactoryProvider.getFactory());
               List<Category> categoryList = null;
               try {
                   categoryList = cdao.getCategories();
                   if (categoryList != null) {
                       for (Category category : categoryList) { %>
                           <tr>
                               <td><%= category.getCategoryTitle() %></td>
                               <td><%= category.getCategoryDescription() %></td>
                               <td>
                                   <button onclick="openUpdateModal('<%= category.getCategoryId() %>', '<%= category.getCategoryTitle() %>','<%= category.getCategoryDescription() %>')" class="btn btn-info btn-sm">Update</button>
                                   <form action="RemoveCategoryServlet" method="post" style="display: inline;">
                                       <input type="hidden" name="categoryId" value="<%= category.getCategoryId() %>">
                                       <button type="submit" class="btn btn-danger btn-sm">Remove</button>
                                   </form>
                               </td>
                           </tr>
                       <% }
                   } else { %>
                       <tr>
                           <td colspan="3">Error retrieving categories.</td>
                       </tr>
                   <% }
               } catch (Exception e) {
                   e.printStackTrace();
               }
            %>
        </tbody>
    </table>
</div>

<!-- Modal -->
<div class="modal fade" id="categoryModal" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="categoryModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h1 class="modal-title fs-5" id="categoryModalLabel" >Update Category Details</h1>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form action="ProductOparetionServlet" method="post">
                    <input type="hidden" name="operation" value="updatecategory"/>
                    <input type="hidden" name="categoryId" id="categoryId"/>
                    <div class="mb-3">
                        <input type="text" class="form-control" name="catTitle" id="catTitle" style="border: solid 1px gray" placeholder="Enter Category title" required>
                    </div>
                    <div class="mb-3">
                        <textarea class="form-control" name="catDesc" id="catDesc" style="border: solid 1px gray; resize: none; height: 150px" placeholder="Enter Category Description"></textarea>
                    </div>
                    <button type="submit" id="Updateca" class="btn btn-primary" style="background-color: black; border: none">Update Category</button>
                </form>
            </div>
        </div>
    </div>
</div>

</body>
</html>
