package com.mycompany.mycart.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.mycompany.nexo.dao.CategoryDao;
import com.mycompany.nexo.helper.FactoryProvider;

@WebServlet("/RemoveCategoryServlet")
public class RemoveCategoryServlet extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

            // Get the categoryId parameter from the request
            int categoryId = Integer.parseInt(request.getParameter("categoryId"));
            out.println("Received categoryId: " + categoryId);  // Debugging statement

            // Create an instance of CategoryDao
            CategoryDao categoryDao = new CategoryDao(FactoryProvider.getFactory());

            // Call the deleteCategory method and store the result
            boolean result = categoryDao.deleteCategory(categoryId);
            out.println("Deletion result: " + result);  // Debugging statement

            // Provide feedback to the user
            HttpSession httpsession = request.getSession();
            if (result) {
                httpsession.setAttribute("message", "Category removed successfully!");
            } else {
                httpsession.setAttribute("message", "Category removal failed!");
            }
            response.sendRedirect("admin.jsp");
            return;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Servlet for removing categories";
    }
}
