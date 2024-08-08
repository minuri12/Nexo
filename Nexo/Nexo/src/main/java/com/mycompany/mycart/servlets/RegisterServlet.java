package com.learn.mycart.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.mycompany.mycart.entities.User;
import com.mycompany.nexo.helper.FactoryProvider;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Retrieve form parameters
        String userName = request.getParameter("userName");
        String userEmail = request.getParameter("userEmail");
        String userPassword = request.getParameter("userPassword");
        String userPhone = request.getParameter("userPhone");
        String userAddress = request.getParameter("userAddress");

        // Validate inputs
        if (userName == null || userName.trim().isEmpty()) {
            handleError("Name field is required.", out, request, response);
            return;
        }

        if (userEmail == null || !isValidEmail(userEmail)) {
            handleError("Invalid email address.", out, request, response);
            return;
        }

   

        if (userPassword == null || userPassword.trim().isEmpty() || !isValidPassword(userPassword)) {
            handleError("Password must be at least 6 characters long.", out, request, response);
            return;
        }

        if (userPhone == null || !isValidPhoneNumber(userPhone)) {
            handleError("Invalid phone number. It should be 10 digits.", out, request, response);
            return;
        }

        if (userAddress == null || userAddress.trim().isEmpty()) {
            handleError("Address field is required.", out, request, response);
            return;
        }

        // Create User object
        User user = new User(userName, userEmail, userPassword, userPhone, "default.jpg", userAddress, "normal");

        // Save user to database
        Session hibernateSession = FactoryProvider.getFactory().openSession();
        Transaction tx = hibernateSession.beginTransaction();
        int userId = 0; // Initialize userId outside the try block

        try {
            userId = (int) hibernateSession.save(user);
            tx.commit();
            out.println("Registration Successfully!!");
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
            out.println("Failed to save user. Please try again.");
        } finally {
            hibernateSession.close();
        }

        // Set message attribute in session
        HttpSession session = request.getSession();
        session.setAttribute("message", "Registration Successfully!!");

        // Redirect to user_register.jsp
        response.sendRedirect("user_register.jsp");
    }

    // Helper method to handle errors
    private void handleError(String message, PrintWriter out, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        out.println("<p style='color: red;'>" + message + "</p>");
        HttpSession session = request.getSession();
        session.setAttribute("message", message);
        response.sendRedirect("user_register.jsp");
    }

    // Helper method to validate email format
    private boolean isValidEmail(String email) {
        // Simple email validation using regex
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }

    // Helper method to validate phone number format
    private boolean isValidPhoneNumber(String phone) {
        // Simple phone number validation (10 digits)
        return phone.matches("\\d{10}");
    }

    // Helper method to validate password strength
    private boolean isValidPassword(String password) {
        // Example: Password should be at least 6 characters long
        return password.length() >= 6;
    }
}
