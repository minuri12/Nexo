package com.mycompany.mycart.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.mycompany.nexo.dao.ProductDao;
import com.mycompany.nexo.helper.FactoryProvider;

@WebServlet("/RemoveProductServlet")
public class RemoveProductServlet extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

            // Fetch the product ID from the request
            int productId = Integer.parseInt(request.getParameter("productId"));

            // Create a ProductDao object
            ProductDao productDao = new ProductDao(FactoryProvider.getFactory());

            // Delete the product by its ID
            boolean result = productDao.deleteProduct(productId);

            // Provide feedback to the user
            HttpSession httpsession = request.getSession();
            if (result) {
                httpsession.setAttribute("message", "Product removed successfully!");
            } else {
                httpsession.setAttribute("message", "Product removal failed!");
            }
            response.sendRedirect("admin.jsp");
            return;
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
        return "Short description";
    }
}
