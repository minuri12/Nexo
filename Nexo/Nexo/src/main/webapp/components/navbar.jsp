<%@page import="com.mycompany.mycart.entities.User" %>
<%@ page import="com.mycompany.nexo.dao.ProductDao" %>
<%@ page import="com.mycompany.nexo.dao.CategoryDao" %>

<%
User user = (User) session.getAttribute("current_user");
%>

<nav class="navbar navbar-expand-lg bg-body-tertiary custom-navbar" style="border-bottom: 1px black solid">
  <div class="container-fluid">
    <a class="navbar-brand" href="#">Nexo</a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarSupportedContent">
      <ul class="navbar-nav me-auto mb-2 mb-lg-0">
        
        <li class="nav-item dropdown">
          <!-- Additional navigation items can go here -->
        </li>
      </ul>
      <ul class="navbar-nav">
        <li class="nav-item">
          <a class="nav-link">
              <img src="img/shopping-cart_1.png" style="width: 30px; height: auto;" data-bs-toggle="modal" data-bs-target="#cartmodel" id="cartadd">
            <span id="cartItemCount" class="badge rounded-pill" style="color: #1fb622; background-color: black">0</span> <!-- Cart item count -->
          </a>
        </li>
        <% if (user == null) { %>
          <li class="nav-item">
            <a class="nav-link" href="login.jsp">Login</a>
          </li>
        <% } else { %>
          <li class="nav-item">
              <a class="nav-link" href="LogOutServlet" id="logoutButton">Logout</a> <!-- Assuming logout.jsp handles session invalidation -->
          </li>
        <% } %>
      </ul>
    </div>
  </div>
</nav>

<div class="modal fade" id="cartmodel" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true" style="justify-content: center;">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h1 class="modal-title fs-5" id="staticBackdropLabel">Your Shopping Cart</h1>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body">
        <table class="table table-bordered">
          <thead>
            <tr>
              <th>Product Name</th>
              <th>Price</th>
              <th>Quantity</th>
              <th>Total</th>
              <th>Action</th>
            </tr>
          </thead>
          <tbody id="cartItemsContainer">
            <!-- Cart items will be dynamically inserted here -->
          </tbody>
        </table>
        <div class="d-flex justify-content-between">
          <h5>Total Price:</h5>
          <h5 id="totalPrice">Rs. 0</h5>
        </div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
        <button type="button" class="btn btn-primary" onclick="proceedToCheckout()" id="checkoutbtn">Checkout</button>
      </div>
    </div>
  </div>
</div>

<script>
// Function to add item to cart
function addToCart(pId, pName, pPrice, pQuantity) {
  let cart = JSON.parse(localStorage.getItem("cart")) || [];
  let existingProduct = cart.find(item => item.productId === pId);

  // Check if the product quantity is available
  if (pQuantity <= 0) {
    alert("No product is available.");
    return;
  }

  if (existingProduct) {
    // If product already exists in cart, increment quantity if available
    if (pQuantity > existingProduct.productQuantity) {
      existingProduct.productQuantity++;
    } else {
      alert("No more product is available.");
      return;
    }
  } else {
    // If product does not exist in cart, add it if available
    if (pQuantity > 0) {
      let newProduct = { productId: pId, productName: pName, productQuantity: 1, productPrice: pPrice };
      cart.push(newProduct);
    }
  }

  localStorage.setItem("cart", JSON.stringify(cart));

  // Show a success message
  alert(pName + " added to cart successfully!");

  // Update cart item count and modal
  updateCartItemCount();
  updateCartModal();
}

// Function to remove item from cart
function removeFromCart(index) {
  let cart = JSON.parse(localStorage.getItem("cart"));
  if (cart && cart.length > 0) {
    cart.splice(index, 1);
    localStorage.setItem("cart", JSON.stringify(cart));
  }

  // Update cart item count and modal
  updateCartItemCount();
  updateCartModal();
}

// Initial call to update cart item count and modal when page loads
document.addEventListener("DOMContentLoaded", function() {
  updateCartItemCount();
  updateCartModal();
});

// Function to update cart item count
function updateCartItemCount() {
  let cart = JSON.parse(localStorage.getItem("cart"));
  let itemCount = cart ? cart.length : 0;
  document.getElementById("cartItemCount").innerText = itemCount;
}

// Function to update cart modal
function updateCartModal() {
  let cart = JSON.parse(localStorage.getItem("cart"));
  let cartItemsContainer = document.getElementById("cartItemsContainer");
  let totalPrice = 0;

  // Clear existing content
  while (cartItemsContainer.firstChild) {
    cartItemsContainer.removeChild(cartItemsContainer.firstChild);
  }

  if (cart && cart.length > 0) {
    cart.forEach((item, index) => {
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

      let removeButtonCell = document.createElement("td");
      let removeButton = document.createElement("button");
      removeButton.className = "btn btn-danger btn-sm";
      removeButton.textContent = "Remove";
      removeButton.onclick = function() {
        removeFromCart(index);
      };
      removeButtonCell.appendChild(removeButton);
      row.appendChild(removeButtonCell);

      // Append row to the container
      cartItemsContainer.appendChild(row);
    });
  } else {
    let emptyRow = document.createElement("tr");
    let emptyCell = document.createElement("td");
    emptyCell.colSpan = 5;
    emptyCell.textContent = "No items in cart.";
    emptyRow.appendChild(emptyCell);
    cartItemsContainer.appendChild(emptyRow);
  }

  document.getElementById("totalPrice").innerText = "Rs. " + totalPrice.toFixed(2);
}

// Function to proceed to checkout
function proceedToCheckout() {
  let isLoggedIn = <%= (user != null) %>;
  if (!isLoggedIn) {
    // If user is not logged in, redirect to login page
    window.location.href = 'login.jsp';
  } else {
    // If user is logged in, redirect to checkout page
    window.location.href = 'checkout.jsp';
  }
}
</script>
