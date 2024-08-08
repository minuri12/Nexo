//package com.learn.mycart.servlets;
//
//import com.mycompany.mycart.entities.User;
//import com.mycompany.nexo.helper.FactoryProvider;
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.hibernate.Transaction;
//import org.junit.Before;
//import org.junit.After;
//import org.junit.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.mockito.MockedStatic;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.io.StringWriter;
//
//import static org.junit.Assert.assertTrue;
//import static org.mockito.Mockito.*;
//
//public class RegisterServletTest {
//
//    @InjectMocks
//    private RegisterServlet registerServlet;
//
//    @Mock
//    private HttpServletRequest request;
//
//    @Mock
//    private HttpServletResponse response;
//
//    @Mock
//    private HttpSession httpSession;
//
//    @Mock
//    private Session hibernateSession;
//
//    @Mock
//    private Transaction transaction;
//
//    @Mock
//    private SessionFactory sessionFactory;
//
//    private MockedStatic<FactoryProvider> mockedFactoryProvider;
//
//    @Before
//    public void setUp() throws Exception {
//        MockitoAnnotations.openMocks(this);
//
//        // Mock the FactoryProvider class to return the sessionFactory
//        mockedFactoryProvider = mockStatic(FactoryProvider.class);
//        when(FactoryProvider.getFactory()).thenReturn(sessionFactory);
//        when(sessionFactory.openSession()).thenReturn(hibernateSession);
//        when(hibernateSession.beginTransaction()).thenReturn(transaction);
//    }
//
//    @Test
//    public void testDoPost_SuccessfulRegistration() throws IOException, ServletException {
//        // Mock request parameters
//        when(request.getParameter("userName")).thenReturn("TestUser");
//        when(request.getParameter("userEmail")).thenReturn("testuser@example.com");
//        when(request.getParameter("userPassword")).thenReturn("password");
//        when(request.getParameter("userPhone")).thenReturn("1234567890");
//        when(request.getParameter("userAddress")).thenReturn("123 Test Street");
//        when(request.getSession()).thenReturn(httpSession);
//
//        // Prepare response writer
//        StringWriter stringWriter = new StringWriter();
//        PrintWriter writer = new PrintWriter(stringWriter);
//        when(response.getWriter()).thenReturn(writer);
//
//        // Simulate successful save and transaction commit
//        when(hibernateSession.save(any(User.class))).thenReturn(1); // Mock an ID (e.g., 1) as the return value
//        doNothing().when(transaction).commit();
//
//        // Perform servlet doPost method
//        registerServlet.doPost(request, response);
//
//        // Verify Hibernate session interactions
//        verify(hibernateSession).beginTransaction();
//        verify(hibernateSession).save(any(User.class));
//        verify(transaction).commit();
//        verify(hibernateSession).close();
//
//        // Verify session attributes and redirection
//        verify(httpSession).setAttribute(eq("message"), eq("Registration Successfully!!"));
//        verify(response).sendRedirect("user_register.jsp");
//
//        // Check response content
//        String result = stringWriter.toString();
//        assertTrue(result.contains("Registration Successfully!!"));
//    }
//
//    @Test
//    public void testDoPost_InvalidEmail() throws IOException, ServletException {
//        System.out.println("Starting testDoPost_InvalidEmail");
//
//        // Mock request parameters with invalid userEmail
//        when(request.getParameter("userName")).thenReturn("TestUser");
//        when(request.getParameter("userEmail")).thenReturn("invalidemail");
//        when(request.getParameter("userPassword")).thenReturn("password");
//        when(request.getParameter("userPhone")).thenReturn("1234567890");
//        when(request.getParameter("userAddress")).thenReturn("123 Test Street");
//        when(request.getSession()).thenReturn(httpSession);
//
//        // Prepare response writer
//        StringWriter stringWriter = new StringWriter();
//        PrintWriter writer = new PrintWriter(stringWriter);
//        when(response.getWriter()).thenReturn(writer);
//
//        try {
//            // Perform servlet doPost method
//            registerServlet.doPost(request, response);
//        } catch (Exception e) {
//            e.printStackTrace(); // Print the stack trace for debugging
//            throw e; // Re-throw the exception to let the test framework handle it
//        }
//
//        System.out.println("Servlet processing completed");
//
//        // Verify that no interaction with Hibernate session occurs
//        verify(hibernateSession, never()).beginTransaction();
//        verify(hibernateSession, never()).save(any(User.class));
//        verify(transaction, never()).commit();
//        verify(hibernateSession, never()).close();
//
//        // Verify session attributes and redirection do occur for invalid email
//        verify(httpSession).setAttribute(eq("message"), eq("Invalid email address."));
//        verify(response).sendRedirect("user_register.jsp");
//
//        // Check response content
//        String result = stringWriter.toString();
//        assertTrue(result.contains("Invalid email address."));
//    }
//
// @Test
//    public void testDoPost_EmailRequired() throws IOException, ServletException {
//        System.out.println("Starting testDoPost_EmailRequired");
//
//        // Mock request parameters without userEmail
//        when(request.getParameter("userName")).thenReturn("TestUser");
//        when(request.getParameter("userEmail")).thenReturn(""); 
//        when(request.getParameter("userPassword")).thenReturn("password");
//        when(request.getParameter("userPhone")).thenReturn("1234567890");
//        when(request.getParameter("userAddress")).thenReturn("123 Test Street");
//        when(request.getSession()).thenReturn(httpSession);
//
//        // Prepare response writer
//        StringWriter stringWriter = new StringWriter();
//        PrintWriter writer = new PrintWriter(stringWriter);
//        when(response.getWriter()).thenReturn(writer);
//
//        try {
//            // Perform servlet doPost method
//            registerServlet.doPost(request, response);
//        } catch (Exception e) {
//            e.printStackTrace(); // Print the stack trace for debugging
//            throw e; // Re-throw the exception to let the test framework handle it
//        }
//
//        System.out.println("Servlet processing completed");
//
//        // Verify that no interaction with Hibernate session occurs
//        verify(hibernateSession, never()).beginTransaction();
//        verify(hibernateSession, never()).save(any(User.class));
//        verify(transaction, never()).commit();
//        verify(hibernateSession, never()).close();
//
//        // Verify session attributes and redirection do occur for missing email
//
//        // Verify session attributes and redirection do occur for invalid email
//        verify(httpSession).setAttribute(eq("message"), eq("Invalid email address."));
//        verify(response).sendRedirect("user_register.jsp");
//
//        // Check response content
//        String result = stringWriter.toString();
//        assertTrue(result.contains("Invalid email address."));
//    }
//@Test
//public void testDoPost_InvalidPhoneNumber() throws IOException, ServletException {
//    // Mock request parameters with an invalid phone number (more than 10 digits)
//    when(request.getParameter("userName")).thenReturn("TestUser");
//    when(request.getParameter("userEmail")).thenReturn("testuser@example.com");
//    when(request.getParameter("userPassword")).thenReturn("password");
//    when(request.getParameter("userPhone")).thenReturn("12345678912367"); // Invalid phone number
//    when(request.getParameter("userAddress")).thenReturn("123 Test Street");
//    when(request.getSession()).thenReturn(httpSession);
//
//    // Prepare response writer
//    StringWriter stringWriter = new StringWriter();
//    PrintWriter writer = new PrintWriter(stringWriter);
//    when(response.getWriter()).thenReturn(writer);
//
//    try {
//        // Perform servlet doPost method
//        registerServlet.doPost(request, response);
//    } catch (Exception e) {
//        e.printStackTrace(); // Print the stack trace for debugging
//        throw e; // Re-throw the exception to let the test framework handle it
//    }
//
//    // Verify that no interaction with Hibernate session occurs
//    verify(hibernateSession, never()).beginTransaction();
//    verify(hibernateSession, never()).save(any(User.class));
//    verify(transaction, never()).commit();
//    verify(hibernateSession, never()).close();
//
//    // Verify session attributes and redirection
//    verify(httpSession).setAttribute(eq("message"), eq("Invalid phone number. It should be 10 digits."));
//    verify(response).sendRedirect("user_register.jsp");
//
//    // Check response content
//    String result = stringWriter.toString();
//    assertTrue(result.contains("Invalid phone number. It should be 10 digits."));
//}
//
//    @Test
//    public void testDoPost_NameRequired() throws IOException, ServletException {
//        // Mock request parameters without userName
//        when(request.getParameter("userName")).thenReturn(""); // Empty name
//        when(request.getParameter("userEmail")).thenReturn("testuser@example.com");
//        when(request.getParameter("userPassword")).thenReturn("password");
//        when(request.getParameter("userPhone")).thenReturn("1234567890");
//        when(request.getParameter("userAddress")).thenReturn("123 Test Street");
//        when(request.getSession()).thenReturn(httpSession);
//
//        // Prepare response writer
//        StringWriter stringWriter = new StringWriter();
//        PrintWriter writer = new PrintWriter(stringWriter);
//        when(response.getWriter()).thenReturn(writer);
//
//        try {
//            // Perform servlet doPost method
//            registerServlet.doPost(request, response);
//        } catch (Exception e) {
//            e.printStackTrace(); // Print the stack trace for debugging
//            throw e; // Re-throw the exception to let the test framework handle it
//        }
//
//        // Verify that no interaction with Hibernate session occurs
//        verify(hibernateSession, never()).beginTransaction();
//        verify(hibernateSession, never()).save(any(User.class));
//        verify(transaction, never()).commit();
//        verify(hibernateSession, never()).close();
//
//        // Verify session attributes and redirection
//        verify(httpSession).setAttribute(eq("message"), eq("Name field is required."));
//        verify(response).sendRedirect("user_register.jsp");
//
//        // Check response content
//        String result = stringWriter.toString();
//        assertTrue(result.contains("Name field is required."));
//    }
//
//    @Test
//    public void testDoPost_AddressRequired() throws IOException, ServletException {
//        // Mock request parameters without userAddress
//        when(request.getParameter("userName")).thenReturn("TestUser");
//        when(request.getParameter("userEmail")).thenReturn("testuser@example.com");
//        when(request.getParameter("userPassword")).thenReturn("password");
//        when(request.getParameter("userPhone")).thenReturn("1234567890");
//        when(request.getParameter("userAddress")).thenReturn(""); // Empty address
//        when(request.getSession()).thenReturn(httpSession);
//
//        // Prepare response writer
//        StringWriter stringWriter = new StringWriter();
//        PrintWriter writer = new PrintWriter(stringWriter);
//        when(response.getWriter()).thenReturn(writer);
//
//        try {
//            // Perform servlet doPost method
//            registerServlet.doPost(request, response);
//        } catch (Exception e) {
//            e.printStackTrace(); // Print the stack trace for debugging
//            throw e; // Re-throw the exception to let the test framework handle it
//        }
//
//        // Verify that no interaction with Hibernate session occurs
//        verify(hibernateSession, never()).beginTransaction();
//        verify(hibernateSession, never()).save(any(User.class));
//        verify(transaction, never()).commit();
//        verify(hibernateSession, never()).close();
//
//        // Verify session attributes and redirection
//        verify(httpSession).setAttribute(eq("message"), eq("Address field is required."));
//        verify(response).sendRedirect("user_register.jsp");
//
//        // Check response content
//        String result = stringWriter.toString();
//        assertTrue(result.contains("Address field is required."));
//    }
//
//    @Test
//    public void testDoPost_PasswordLength() throws IOException, ServletException {
//        // Mock request parameters with a short password
//        when(request.getParameter("userName")).thenReturn("TestUser");
//        when(request.getParameter("userEmail")).thenReturn("testuser@example.com");
//        when(request.getParameter("userPassword")).thenReturn("12345"); 
//        when(request.getParameter("userPhone")).thenReturn("1234567890");
//        when(request.getParameter("userAddress")).thenReturn("123 Test Street");
//        when(request.getSession()).thenReturn(httpSession);
//
//        // Prepare response writer
//        StringWriter stringWriter = new StringWriter();
//        PrintWriter writer = new PrintWriter(stringWriter);
//        when(response.getWriter()).thenReturn(writer);
//
//        try {
//            // Perform servlet doPost method
//            registerServlet.doPost(request, response);
//        } catch (Exception e) {
//            e.printStackTrace(); // Print the stack trace for debugging
//            throw e; // Re-throw the exception to let the test framework handle it
//        }
//
//        // Verify that no interaction with Hibernate session occurs
//        verify(hibernateSession, never()).beginTransaction();
//        verify(hibernateSession, never()).save(any(User.class));
//        verify(transaction, never()).commit();
//        verify(hibernateSession, never()).close();
//
//        // Verify session attributes and redirection
//        verify(httpSession).setAttribute(eq("message"), eq("Password must be at least 6 characters long."));
//        verify(response).sendRedirect("user_register.jsp");
//
//        // Check response content
//        String result = stringWriter.toString();
//        assertTrue(result.contains("Password must be at least 6 characters long."));
//    }
//    @After
//    public void tearDown() {
//        mockedFactoryProvider.close();
//    }
//
//
//}
