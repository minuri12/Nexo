<%@page import="com.mycompany.mycart.entities.User" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Checkout</title>
     <%@include file="components/common_css_js.jsp" %>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/5.1.3/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div style="background-color: black;color: white;text-align: center;height: 60px;justify-content: center;align-items: center;display: flex;font-size: 20px">
        NEXO - Checkout Process
    </div>
    <div class="container mt-5">
        <div class="row">
            <div class="col-md-6" style="background-color: #d1ffbd;border-radius: 10px;padding: 20px;">
                <h3>Shopping Cart</h3>
                
                <table class="table table-bordered table-hover">
                    <thead class="table-dark">
                        <tr>
                            <th>Product Name</th>
                            <th>Price</th>
                            <th>Quantity</th>
                            <th>Total</th>
                        </tr>
                    </thead>
                    <tbody id="cartItemsContainer">
                        <!-- Cart items will be dynamically inserted here -->
                    </tbody>
                </table>
                <div class="d-flex justify-content-between" style="background-color: black;color:white;padding: 20px;border-radius: 10px">
                    <h5>Total Price:</h5>
                    <h5 id="totalPrice">Rs. 0</h5>
                </div>
            </div>
            <div class="col-md-6">
                <h3>User Details</h3>
                <form id="userDetailsForm" action="OrderServlet" method="post">
                    <div class="mb-3">
                        <label for="fullName" class="form-label">Full Name</label>
                        <input type="text" class="form-control" id="fullName" name="fullName" required>
                    </div>
                    <div class="mb-3">
                        <label for="email" class="form-label">Email</label>
                        <input type="email" class="form-control" id="email" name="email" required>
                    </div>
                    <div class="mb-3">
                        <label for="address" class="form-label">Address</label>
                        <input type="text" class="form-control" id="address" name="address" required>
                    </div>
                    <div class="mb-3">
                        <label for="phone" class="form-label">Phone</label>
                        <input type="text" class="form-control" id="phone" name="phone" required>
                    </div>
                    <button type="submit" class="btn btn-primary w-100" style="background-color: black;color: white;border: none">Place Order</button>
                </form>
            </div>
        </div>
    </div>

    <script>
        document.addEventListener("DOMContentLoaded", function() {
            updateCartModal();
        });

        function updateCartModal() {
            let cart = JSON.parse(localStorage.getItem("cart"));
            let cartItemsContainer = document.getElementById("cartItemsContainer");
            let totalPrice = 0;

            // Clear existing content
            while (cartItemsContainer.firstChild) {
                cartItemsContainer.removeChild(cartItemsContainer.firstChild);
            }

            if (cart && cart.length > 0) {
                cart.forEach(item => {
                    let itemTotalPrice = item.productPrice * item.productQuantity;
                    totalPrice += itemTotalPrice;

                    // Create table row and cells
                    let row = document.createElement("tr");

                    let productNameCell = document.createElement("td");
                    productNameCell.textContent = item.productName;
                    row.appendChild(productNameCell);

                    let productPriceCell = document.createElement("td");
                    productPriceCell.textContent = item.productPrice.toFixed(2);
                    row.appendChild(productPriceCell);

                    let productQuantityCell = document.createElement("td");
                    productQuantityCell.textContent = item.productQuantity;
                    row.appendChild(productQuantityCell);

                    let itemTotalPriceCell = document.createElement("td");
                    itemTotalPriceCell.textContent = itemTotalPrice.toFixed(2);
                    row.appendChild(itemTotalPriceCell);

                    // Append row to the container
                    cartItemsContainer.appendChild(row);
                });
            } else {
                let emptyRow = document.createElement("tr");
                let emptyCell = document.createElement("td");
                emptyCell.colSpan = 4;
                emptyCell.textContent = "No items in cart.";
                emptyRow.appendChild(emptyCell);
                cartItemsContainer.appendChild(emptyRow);
            }

            document.getElementById("totalPrice").innerText = "Rs. " + totalPrice.toFixed(2);
        }
    </script>

    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/5.1.3/js/bootstrap.bundle.min.js"></script>
</body>
</html>
