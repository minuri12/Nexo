package com.mycompany.mycart.servlets;

import javax.servlet.annotation.MultipartConfig;
import java.io.IOException;
import java.io.InputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.mycompany.mycart.entities.Category;
import com.mycompany.mycart.entities.Product;
import com.mycompany.nexo.dao.CategoryDao;
import com.mycompany.nexo.dao.ProductDao;
import com.mycompany.nexo.helper.FactoryProvider;
import javax.servlet.http.Part;

@MultipartConfig
@WebServlet("/ProductOparetionServlet")
public class ProductOparetionServlet extends HttpServlet {

    private CategoryDao categoryDao;
    private ProductDao productDao;

    // Setter methods for testing
    public void setCategoryDao(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    public void setProductDao(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Override
    public void init() throws ServletException {
        super.init();
        // Initialize DAOs
        if (this.categoryDao == null) {
            this.categoryDao = new CategoryDao(FactoryProvider.getFactory());
        }
        if (this.productDao == null) {
            this.productDao = new ProductDao(FactoryProvider.getFactory());
        }
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

            String op = request.getParameter("operation");

            if (op != null && op.trim().equals("addcategory")) {
                String catTitle = request.getParameter("catTitle");
                String catDesc = request.getParameter("catDesc");

   if (catTitle == null || catTitle.trim().isEmpty()) {
                    // Handle empty category title
                    HttpSession session = request.getSession();
                    session.setAttribute("message", "Category title cannot be empty");
                    response.sendRedirect("admin.jsp");
                    return;
                }

                Category existingCategory = categoryDao.getCategoryByTitle(catTitle);
                if (catDesc == null || catDesc.trim().isEmpty()) {
                    // Handle empty category description
                    HttpSession session = request.getSession();
                    session.setAttribute("message", "Category description cannot be empty");
                    response.sendRedirect("admin.jsp");
                    return;
                }


                if (existingCategory != null) {
                    HttpSession session = request.getSession();
                    session.setAttribute("message", "Category with this title already exists");
                    response.sendRedirect("admin.jsp");
                    return;
                }

                Category category = new Category();
                category.setCategoryTitle(catTitle);
                category.setCategoryDescription(catDesc);

                int catId = categoryDao.saveCategory(category);

                out.println("Category Saved!");
                HttpSession session = request.getSession();
                session.setAttribute("message", "Category added successfully");
                response.sendRedirect("admin.jsp");
                return;

            } else if (op != null && op.trim().equals("addproduct")){
                String pName = request.getParameter("pName");
                String pDesc = request.getParameter("pDesc");
                Part part = request.getPart("pPhoto");
                String pPriceStr = request.getParameter("pPrice");
                String pDiscountStr = request.getParameter("pDiscount");
                String categoryIdStr = request.getParameter("categoryId");
                String pQuantityStr = request.getParameter("pQuantity");

                if (pName == null || pName.trim().isEmpty() || 
                    pDesc == null || pDesc.trim().isEmpty() || 
                    
                    pPriceStr == null || pPriceStr.trim().isEmpty() || 
                    pDiscountStr == null || pDiscountStr.trim().isEmpty() || 
                    categoryIdStr == null || categoryIdStr.trim().isEmpty() || 
                    pQuantityStr == null || pQuantityStr.trim().isEmpty()) {

                    HttpSession session = request.getSession();
                    session.setAttribute("message", "All fields are required");
                    response.sendRedirect("admin.jsp");
                    return;
                }

                double pPrice = Double.parseDouble(pPriceStr);
                int pDiscount = Integer.parseInt(pDiscountStr);
                int categoryId = Integer.parseInt(categoryIdStr);
                int pQuantity = Integer.parseInt(pQuantityStr);

                Product p = new Product();
                p.setPName(pName);
                p.setPDesc(pDesc);
                p.setPPrice(pPrice);
                p.setPDiscount(pDiscount);
                p.setPQuantity(pQuantity);

                Category category = categoryDao.getCategoryById(categoryId);
                p.setCategory(category);

                // Save the file to the server
                String path = request.getServletContext().getRealPath("") + File.separator + "img" + File.separator + "products";
                String fileName = part.getSubmittedFileName();

                // Ensure the directory exists
                File directory = new File(path);
                if (!directory.exists()) {
                    directory.mkdirs();
                }

                // Save the file
                try (InputStream is = part.getInputStream();
                     FileOutputStream fos = new FileOutputStream(path + File.separator + fileName)) {
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = is.read(buffer)) != -1) {
                        fos.write(buffer, 0, bytesRead);
                    }
                    p.setPPhoto(fileName); // Set the file name in the Product entity
                } catch (IOException e) {
                    e.printStackTrace();
                }

                productDao.saveProduct(p);

                HttpSession session = request.getSession();
                session.setAttribute("message", "Product added successfully");
                response.sendRedirect("admin.jsp");
                return;

          
                

            } else if (op != null && op.trim().equals("updatecategory")) {

                int catId = Integer.parseInt(request.getParameter("categoryId"));
                String catTitle = request.getParameter("catTitle");
                String catDesc = request.getParameter("catDesc");
                Category category = categoryDao.getCategoryById(catId);

                if (category != null) {
                    category.setCategoryTitle(catTitle);
                    category.setCategoryDescription(catDesc);

                    categoryDao.updateCategory(category);

                    HttpSession session = request.getSession();
                    session.setAttribute("message", "Category updated successfully");
                    response.sendRedirect("admin.jsp");
                } else {
                    HttpSession session = request.getSession();
                    session.setAttribute("message", "Category not found!");
                    response.sendRedirect("admin.jsp");
                }
                return;
            } else {
                out.println("Invalid operation");
            }
        } catch (Exception e) {
            e.printStackTrace();
            HttpSession session = request.getSession();
            session.setAttribute("message", "An error occurred: " + e.getMessage());
            response.sendRedirect("admin.jsp");
        }
    }

    // Process POST requests
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    // Process GET requests
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}
