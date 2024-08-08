package com.learn.mycart.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import com.mycompany.mycart.entities.User;
import com.mycompany.nexo.dao.UserDao;
import com.mycompany.nexo.helper.FactoryProvider;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private UserDao userDao;

    public LoginServlet() {
        this.userDao = new UserDao(FactoryProvider.getFactory());
    }

    // Setter for injecting mock UserDao in tests
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get the email and password from the request
        String email = request.getParameter("userEmail");
        String password = request.getParameter("userPassword");

        User user = userDao.getUserByEmailAndPassword(email, password);
        HttpSession session = request.getSession();

        if (user == null) {
            session.setAttribute("message", "Invalid Details! Try Again...");
            response.sendRedirect("login.jsp");
            return;
        } else {
            // If user is authenticated
            session.setAttribute("current_user", user);
            // Check user type and redirect accordingly
            if (user.getUserType().equals("admin")) {
                response.sendRedirect("admin.jsp");
            } else if (user.getUserType().equals("normal")) {
                response.sendRedirect("index.jsp");
            } else {
                // If user type is neither admin nor normal
                PrintWriter out = response.getWriter();
                out.println("We did not identify user type.");
            }
        }
    }



}
