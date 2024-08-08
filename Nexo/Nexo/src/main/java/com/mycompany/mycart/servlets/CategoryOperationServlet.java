package com.mycompany.nexo.servlets;

import com.mycompany.mycart.entities.Category;
import com.mycompany.nexo.dao.CategoryDao;
import com.mycompany.nexo.helper.FactoryProvider;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "CategoryOperationServlet", urlPatterns = {"/CategoryOperationServlet"})
public class CategoryOperationServlet extends HttpServlet {

    private CategoryDao categoryDao;

    @Override
    public void init() throws ServletException {
        super.init();
        categoryDao = new CategoryDao(FactoryProvider.getFactory()); // Initialize DAO in Servlet's init method
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String operation = request.getParameter("operation");

        if (operation != null && operation.equals("updatecategory")) {
            updateCategory(request, response);
        }
        
    }

    private void updateCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int categoryId = Integer.parseInt(request.getParameter("categoryId"));
        String catTitle = request.getParameter("catTitle");
        String catDesc = request.getParameter("catDesc");

        Category categoryToUpdate = categoryDao.getCategoryById(categoryId);
        if (categoryToUpdate != null) {
            categoryToUpdate.setCategoryTitle(catTitle);
            categoryToUpdate.setCategoryDescription(catDesc);

            categoryDao.updateCategory(categoryToUpdate); // Update category in the database

            // Redirect to the page after update
            response.sendRedirect(request.getContextPath() + "/updateProduct"); // Replace with your desired redirection URL
        } else {
            // Handle case where category is not found
            response.getWriter().println("Category not found for ID: " + categoryId);
        }
    }
}
