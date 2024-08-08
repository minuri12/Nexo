//package com.mycompany.mycart.servlets;
//
//import com.mycompany.mycart.entities.Category;
//import com.mycompany.mycart.entities.Product;
//import com.mycompany.nexo.dao.CategoryDao;
//import com.mycompany.nexo.dao.ProductDao;
//import org.junit.Before;
//import org.junit.Test;
//import org.mockito.Mockito;
//import java.io.ByteArrayInputStream;
//import javax.servlet.ServletContext;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import javax.servlet.http.Part;
//import java.io.IOException;
//import java.io.PrintWriter;
//import static org.mockito.Mockito.*;
//import static org.junit.Assert.*;
//import com.mycompany.nexo.helper.FactoryProvider;
//
//public class ProductOparetionServletTest {
//
//    private ProductOparetionServlet productOparetionServlet;
//    private HttpServletRequest request;
//    private HttpServletResponse response;
//    private HttpSession session;
//    private PrintWriter writer;
//    private CategoryDao categoryDao;
//    private ProductDao productDao;
//
//    @Before
//    public void setUp() throws Exception {
//        productOparetionServlet = new ProductOparetionServlet();
//        request = mock(HttpServletRequest.class);
//        response = mock(HttpServletResponse.class);
//        session = mock(HttpSession.class);
//        writer = mock(PrintWriter.class);
//        categoryDao = mock(CategoryDao.class);
//        productDao = mock(ProductDao.class);
//
//
//        // Mock responses
//        when(response.getWriter()).thenReturn(writer);
//        when(request.getSession()).thenReturn(session);
//
//        // Inject mocks into the servlet
//        productOparetionServlet.setCategoryDao(categoryDao);
//        productOparetionServlet.setProductDao(productDao);
//    }
//
//
//
//
//    @Test
//    public void testAddCategory() throws Exception {
//        when(request.getParameter("operation")).thenReturn("addcategory");
//        when(request.getParameter("catTitle")).thenReturn("Electronics");
//        when(request.getParameter("catDesc")).thenReturn("Devices and gadgets");
//        when(categoryDao.saveCategory(any(Category.class))).thenReturn(1);
//
//        productOparetionServlet.doPost(request, response);
//
//        verify(session).setAttribute("message", "Category added successfully");
//        verify(response).sendRedirect("admin.jsp");
//    }
//
//  @Test
//    public void testAddCategoryWithEmptyDec() throws Exception {
//        when(request.getParameter("operation")).thenReturn("addcategory");
//        when(request.getParameter("catTitle")).thenReturn("Electronics");
//        when(request.getParameter("catDesc")).thenReturn("");
//        when(categoryDao.saveCategory(any(Category.class))).thenReturn(1);
//
//        productOparetionServlet.doPost(request, response);
//
//        verify(session).setAttribute("message", "Category description cannot be empty");
//        verify(response).sendRedirect("admin.jsp");
//    }
//
// @Test
//    public void testAddCategoryWithEmptytitle() throws Exception {
//        when(request.getParameter("operation")).thenReturn("addcategory");
//        when(request.getParameter("catTitle")).thenReturn("");
//        when(request.getParameter("catDesc")).thenReturn("Description");
//        when(categoryDao.saveCategory(any(Category.class))).thenReturn(1);
//
//        productOparetionServlet.doPost(request, response);
//
//        verify(session).setAttribute("message", "Category title cannot be empty");
//        verify(response).sendRedirect("admin.jsp");
//    }
//
// @Test
//    public void testAddCategory_CategoryExists() throws ServletException, IOException {
//        // Arrange
//        String catTitle = "Existing Category";
//        String catDesc = "Category Description";
//        
//        // Mock request parameters
//        when(request.getParameter("operation")).thenReturn("addcategory");
//        when(request.getParameter("catTitle")).thenReturn(catTitle);
//        when(request.getParameter("catDesc")).thenReturn(catDesc);
//
//        // Mock DAO behavior
//        when(categoryDao.getCategoryByTitle(catTitle)).thenReturn(new Category());
//
//        // Act
//        productOparetionServlet.doPost(request, response);
//
//        // Assert
//        verify(session).setAttribute("message", "Category with this title already exists");
//        verify(response).sendRedirect("admin.jsp");
//    }
//
//
//@Test
//public void testAddProduct() throws Exception {
//    // Prepare test data
//    String pName = "Product Name";
//    String pDesc = "Product Description";
//    double pPrice = 100.0;
//    int pDiscount = 10;
//    int categoryId = 1;
//    int pQuantity = 5;
//    Part part = mock(Part.class);
//    when(part.getSubmittedFileName()).thenReturn("product.jpg");
//    when(part.getInputStream()).thenReturn(new ByteArrayInputStream(new byte[0]));
//
//    // Mock DAOs
//    Category category = new Category();
//    category.setCategoryId(categoryId);
//    when(categoryDao.getCategoryById(categoryId)).thenReturn(category);
//
//    // Mock ProductDao's saveProduct method
//    doNothing().when(productDao).saveProduct(any(Product.class));
//
//    // Mock HttpServletRequest
//    when(request.getParameter("operation")).thenReturn("addproduct");
//    when(request.getParameter("pName")).thenReturn(pName);
//    when(request.getParameter("pDesc")).thenReturn(pDesc);
//    when(request.getParameter("pPrice")).thenReturn(String.valueOf(pPrice));
//    when(request.getParameter("pDiscount")).thenReturn(String.valueOf(pDiscount));
//    when(request.getParameter("categoryId")).thenReturn(String.valueOf(categoryId));
//    when(request.getParameter("pQuantity")).thenReturn(String.valueOf(pQuantity));
//    when(request.getPart("pPhoto")).thenReturn(part);
//
//    // Mock HttpSession
//    HttpSession session = mock(HttpSession.class);
//    when(request.getSession()).thenReturn(session);
//
//    // Mock ServletContext
//    ServletContext servletContext = mock(ServletContext.class);
//    when(request.getServletContext()).thenReturn(servletContext);
//    when(servletContext.getRealPath(anyString())).thenReturn("/path/to/images");
//
//    // Inject mocks into servlet
//    productOparetionServlet.setCategoryDao(categoryDao);
//    productOparetionServlet.setProductDao(productDao);
//
//    // Call the servlet's processRequest method
//    productOparetionServlet.processRequest(request, response);
//
//    // Verify the correct message is set in the session
//    verify(session).setAttribute("message", "Product added successfully");
//
//    // Verify that saveProduct was called
//    verify(productDao).saveProduct(any(Product.class));
//
//    // Verify redirection
//    verify(response).sendRedirect("admin.jsp");
//}
//
//@Test
//public void testAddProductEmpty() throws Exception {
//    // Prepare test data
//    String pName = "";
//    String pDesc = "Product Description";
//    double pPrice = 100.0;
//    int pDiscount = 10;
//    int categoryId = 1;
//    int pQuantity = 5;
//    Part part = mock(Part.class);
//    when(part.getSubmittedFileName()).thenReturn("product.jpg");
//    when(part.getInputStream()).thenReturn(new ByteArrayInputStream(new byte[0]));
//
//    // Mock DAOs
//    Category category = new Category();
//    category.setCategoryId(categoryId);
//    when(categoryDao.getCategoryById(categoryId)).thenReturn(category);
//
//    // Mock HttpServletRequest
//    when(request.getParameter("operation")).thenReturn("addproduct");
//    when(request.getParameter("pName")).thenReturn(pName);
//    when(request.getParameter("pDesc")).thenReturn(pDesc);
//    when(request.getParameter("pPrice")).thenReturn(String.valueOf(pPrice));
//    when(request.getParameter("pDiscount")).thenReturn(String.valueOf(pDiscount));
//    when(request.getParameter("categoryId")).thenReturn(String.valueOf(categoryId));
//    when(request.getParameter("pQuantity")).thenReturn(String.valueOf(pQuantity));
//    when(request.getPart("pPhoto")).thenReturn(part);
//
//    // Mock HttpSession
//    HttpSession session = mock(HttpSession.class);
//    when(request.getSession()).thenReturn(session);
//
//    // Mock ServletContext
//    ServletContext servletContext = mock(ServletContext.class);
//    when(request.getServletContext()).thenReturn(servletContext);
//    when(servletContext.getRealPath(anyString())).thenReturn("/path/to/images");
//
//    // Inject mocks into servlet
//    productOparetionServlet.setCategoryDao(categoryDao);
//    productOparetionServlet.setProductDao(productDao);
//
//    // Call the servlet's processRequest method
//    productOparetionServlet.processRequest(request, response);
//
//    // Verify the correct message is set in the session
//    verify(session).setAttribute("message", "All fields are required");
//
//    // Verify that saveProduct was NOT called
//    verify(productDao, never()).saveProduct(any(Product.class));
//
//    // Verify redirection
//    verify(response).sendRedirect("admin.jsp");
//}
//
//@Test
//public void testAddProductEmptyDec() throws Exception {
//    // Prepare test data
//    String pName = "Name";
//    String pDesc = "";
//    double pPrice = 100.0;
//    int pDiscount = 10;
//    int categoryId = 1;
//    int pQuantity = 5;
//    Part part = mock(Part.class);
//    when(part.getSubmittedFileName()).thenReturn("product.jpg");
//    when(part.getInputStream()).thenReturn(new ByteArrayInputStream(new byte[0]));
//
//    // Mock DAOs
//    Category category = new Category();
//    category.setCategoryId(categoryId);
//    when(categoryDao.getCategoryById(categoryId)).thenReturn(category);
//
//    // Mock HttpServletRequest
//    when(request.getParameter("operation")).thenReturn("addproduct");
//    when(request.getParameter("pName")).thenReturn(pName);
//    when(request.getParameter("pDesc")).thenReturn(pDesc);
//    when(request.getParameter("pPrice")).thenReturn(String.valueOf(pPrice));
//    when(request.getParameter("pDiscount")).thenReturn(String.valueOf(pDiscount));
//    when(request.getParameter("categoryId")).thenReturn(String.valueOf(categoryId));
//    when(request.getParameter("pQuantity")).thenReturn(String.valueOf(pQuantity));
//    when(request.getPart("pPhoto")).thenReturn(part);
//
//    // Mock HttpSession
//    HttpSession session = mock(HttpSession.class);
//    when(request.getSession()).thenReturn(session);
//
//    // Mock ServletContext
//    ServletContext servletContext = mock(ServletContext.class);
//    when(request.getServletContext()).thenReturn(servletContext);
//    when(servletContext.getRealPath(anyString())).thenReturn("/path/to/images");
//
//    // Inject mocks into servlet
//    productOparetionServlet.setCategoryDao(categoryDao);
//    productOparetionServlet.setProductDao(productDao);
//
//    // Call the servlet's processRequest method
//    productOparetionServlet.processRequest(request, response);
//
//    // Verify the correct message is set in the session
//    verify(session).setAttribute("message", "All fields are required");
//
//    // Verify that saveProduct was NOT called
//    verify(productDao, never()).saveProduct(any(Product.class));
//
//    // Verify redirection
//    verify(response).sendRedirect("admin.jsp");
//}
//
//@Test
//public void testAddProductEmptyPrice() throws Exception {
//    // Prepare test data
//    String pName = "Product Name";
//    String pDesc = "Product Description";
//    String pPrice = ""; // Empty price
//    String pDiscount = "10";
//    int categoryId = 1;
//    String pQuantity = "5";
//    Part part = mock(Part.class);
//    when(part.getSubmittedFileName()).thenReturn("product.jpg");
//    when(part.getInputStream()).thenReturn(new ByteArrayInputStream(new byte[0]));
//
//    // Mock DAOs
//    Category category = new Category();
//    category.setCategoryId(categoryId);
//    when(categoryDao.getCategoryById(categoryId)).thenReturn(category);
//
//    // Mock HttpServletRequest
//    when(request.getParameter("operation")).thenReturn("addproduct");
//    when(request.getParameter("pName")).thenReturn(pName);
//    when(request.getParameter("pDesc")).thenReturn(pDesc);
//    when(request.getParameter("pPrice")).thenReturn(pPrice);
//    when(request.getParameter("pDiscount")).thenReturn(pDiscount);
//    when(request.getParameter("categoryId")).thenReturn(String.valueOf(categoryId));
//    when(request.getParameter("pQuantity")).thenReturn(pQuantity);
//    when(request.getPart("pPhoto")).thenReturn(part);
//
//    // Mock HttpSession
//    HttpSession session = mock(HttpSession.class);
//    when(request.getSession()).thenReturn(session);
//
//    // Mock ServletContext
//    ServletContext servletContext = mock(ServletContext.class);
//    when(request.getServletContext()).thenReturn(servletContext);
//    when(servletContext.getRealPath(anyString())).thenReturn("/path/to/images");
//
//    // Inject mocks into servlet
//    productOparetionServlet.setCategoryDao(categoryDao);
//    productOparetionServlet.setProductDao(productDao);
//
//    // Call the servlet's processRequest method
//    productOparetionServlet.processRequest(request, response);
//
//    // Verify the correct message is set in the session
//    verify(session).setAttribute("message", "All fields are required");
//
//    // Verify that saveProduct was NOT called
//    verify(productDao, never()).saveProduct(any(Product.class));
//
//    // Verify redirection
//    verify(response).sendRedirect("admin.jsp");
//}
//@Test
//public void testAddProductEmptyCategoryId() throws Exception {
//    // Prepare test data
//    String pName = "Product Name";
//    String pDesc = "Product Description";
//    String pPrice = "100.00";
//    String pDiscount = "10";
//    String categoryId = ""; // Empty categoryId
//    String pQuantity = "5";
//    Part part = mock(Part.class);
//    when(part.getSubmittedFileName()).thenReturn("product.jpg");
//    when(part.getInputStream()).thenReturn(new ByteArrayInputStream(new byte[0]));
//
//    // Mock HttpServletRequest
//    when(request.getParameter("operation")).thenReturn("addproduct");
//    when(request.getParameter("pName")).thenReturn(pName);
//    when(request.getParameter("pDesc")).thenReturn(pDesc);
//    when(request.getParameter("pPrice")).thenReturn(pPrice);
//    when(request.getParameter("pDiscount")).thenReturn(pDiscount);
//    when(request.getParameter("categoryId")).thenReturn(categoryId);
//    when(request.getParameter("pQuantity")).thenReturn(pQuantity);
//    when(request.getPart("pPhoto")).thenReturn(part);
//
//    // Mock HttpSession
//    HttpSession session = mock(HttpSession.class);
//    when(request.getSession()).thenReturn(session);
//
//    // Mock ServletContext
//    ServletContext servletContext = mock(ServletContext.class);
//    when(request.getServletContext()).thenReturn(servletContext);
//    when(servletContext.getRealPath(anyString())).thenReturn("/path/to/images");
//
//    // Inject mocks into servlet
//    productOparetionServlet.setCategoryDao(categoryDao);
//    productOparetionServlet.setProductDao(productDao);
//
//    // Call the servlet's processRequest method
//    productOparetionServlet.processRequest(request, response);
//
//    // Verify the correct message is set in the session
//    verify(session).setAttribute("message", "All fields are required");
//
//    // Verify that saveProduct was NOT called
//    verify(productDao, never()).saveProduct(any(Product.class));
//
//    // Verify redirection
//    verify(response).sendRedirect("admin.jsp");
//}
//@Test
//public void testAddProductEmptyDiscount() throws Exception {
//    // Prepare test data
//    String pName = "Product Name";
//    String pDesc = "Product Description";
//    String pPrice = "100.00";
//    String pDiscount = ""; // Empty discount
//    int categoryId = 1;
//    String pQuantity = "5";
//    Part part = mock(Part.class);
//    when(part.getSubmittedFileName()).thenReturn("product.jpg");
//    when(part.getInputStream()).thenReturn(new ByteArrayInputStream(new byte[0]));
//
//    // Mock DAOs
//    Category category = new Category();
//    category.setCategoryId(categoryId);
//    when(categoryDao.getCategoryById(categoryId)).thenReturn(category);
//
//    // Mock HttpServletRequest
//    when(request.getParameter("operation")).thenReturn("addproduct");
//    when(request.getParameter("pName")).thenReturn(pName);
//    when(request.getParameter("pDesc")).thenReturn(pDesc);
//    when(request.getParameter("pPrice")).thenReturn(pPrice);
//    when(request.getParameter("pDiscount")).thenReturn(pDiscount);
//    when(request.getParameter("categoryId")).thenReturn(String.valueOf(categoryId));
//    when(request.getParameter("pQuantity")).thenReturn(pQuantity);
//    when(request.getPart("pPhoto")).thenReturn(part);
//
//    // Mock HttpSession
//    HttpSession session = mock(HttpSession.class);
//    when(request.getSession()).thenReturn(session);
//
//    // Mock ServletContext
//    ServletContext servletContext = mock(ServletContext.class);
//    when(request.getServletContext()).thenReturn(servletContext);
//    when(servletContext.getRealPath(anyString())).thenReturn("/path/to/images");
//
//    // Inject mocks into servlet
//    productOparetionServlet.setCategoryDao(categoryDao);
//    productOparetionServlet.setProductDao(productDao);
//
//    // Call the servlet's processRequest method
//    productOparetionServlet.processRequest(request, response);
//
//    // Verify the correct message is set in the session
//    verify(session).setAttribute("message", "All fields are required");
//
//    // Verify that saveProduct was NOT called
//    verify(productDao, never()).saveProduct(any(Product.class));
//
//    // Verify redirection
//    verify(response).sendRedirect("admin.jsp");
//}
//@Test
//public void testAddProductEmptyQuantity() throws Exception {
//    // Prepare test data
//    String pName = "Product Name";
//    String pDesc = "Product Description";
//    String pPrice = "100.00";
//    String pDiscount = "10";
//    int categoryId = 1;
//    String pQuantity = ""; // Empty quantity
//    Part part = mock(Part.class);
//    when(part.getSubmittedFileName()).thenReturn("product.jpg");
//    when(part.getInputStream()).thenReturn(new ByteArrayInputStream(new byte[0]));
//
//    // Mock DAOs
//    Category category = new Category();
//    category.setCategoryId(categoryId);
//    when(categoryDao.getCategoryById(categoryId)).thenReturn(category);
//
//    // Mock HttpServletRequest
//    when(request.getParameter("operation")).thenReturn("addproduct");
//    when(request.getParameter("pName")).thenReturn(pName);
//    when(request.getParameter("pDesc")).thenReturn(pDesc);
//    when(request.getParameter("pPrice")).thenReturn(pPrice);
//    when(request.getParameter("pDiscount")).thenReturn(pDiscount);
//    when(request.getParameter("categoryId")).thenReturn(String.valueOf(categoryId));
//    when(request.getParameter("pQuantity")).thenReturn(pQuantity);
//    when(request.getPart("pPhoto")).thenReturn(part);
//
//    // Mock HttpSession
//    HttpSession session = mock(HttpSession.class);
//    when(request.getSession()).thenReturn(session);
//
//    // Mock ServletContext
//    ServletContext servletContext = mock(ServletContext.class);
//    when(request.getServletContext()).thenReturn(servletContext);
//    when(servletContext.getRealPath(anyString())).thenReturn("/path/to/images");
//
//    // Inject mocks into servlet
//    productOparetionServlet.setCategoryDao(categoryDao);
//    productOparetionServlet.setProductDao(productDao);
//
//    // Call the servlet's processRequest method
//    productOparetionServlet.processRequest(request, response);
//
//    // Verify the correct message is set in the session
//    verify(session).setAttribute("message", "All fields are required");
//
//    // Verify that saveProduct was NOT called
//    verify(productDao, never()).saveProduct(any(Product.class));
//
//    // Verify redirection
//    verify(response).sendRedirect("admin.jsp");
//}
//
// 
//
//
//    @Test
//    public void testInvalidOperation() throws Exception {
//        when(request.getParameter("operation")).thenReturn("invalidOperation");
//
//        productOparetionServlet.doPost(request, response);
//
//        verify(writer).println("Invalid operation");
//    }
//
//    @Test
//    public void testExceptionHandling() throws Exception {
//        when(request.getParameter("operation")).thenReturn("addproduct");
//        when(request.getParameter("pName")).thenReturn("Laptop");
//        when(request.getParameter("pDesc")).thenReturn("Gaming Laptop");
//        when(request.getParameter("pPrice")).thenReturn("1200.00");
//        when(request.getParameter("pDiscount")).thenReturn("10");
//        when(request.getParameter("categoryId")).thenReturn("1");
//        when(request.getParameter("pQuantity")).thenReturn("5");
//
//        when(request.getPart("pPhoto")).thenThrow(new IOException("File upload error"));
//
//        productOparetionServlet.doPost(request, response);
//
//        verify(session).setAttribute("message", "An error occurred: File upload error");
//        verify(response).sendRedirect("admin.jsp");
//    }
//}
