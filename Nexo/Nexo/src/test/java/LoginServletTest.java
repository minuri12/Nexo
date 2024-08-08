package com.learn.mycart.servlets;

import com.mycompany.mycart.entities.User;
import com.mycompany.nexo.dao.UserDao;
import com.mycompany.nexo.helper.FactoryProvider;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.MockedStatic;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.mockito.Mockito.*;

public class LoginServletTest {

    @InjectMocks
    private LoginServlet loginServlet;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession httpSession;

    @Mock
    private Session hibernateSession;

    @Mock
    private Transaction transaction;

    @Mock
    private SessionFactory sessionFactory;

    @Mock
    private UserDao userDao;  // Add UserDao mock

    private MockedStatic<FactoryProvider> mockedFactoryProvider;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        // Mock the FactoryProvider class to return the sessionFactory
        mockedFactoryProvider = mockStatic(FactoryProvider.class);
        when(FactoryProvider.getFactory()).thenReturn(sessionFactory);
        when(sessionFactory.openSession()).thenReturn(hibernateSession);
        when(hibernateSession.beginTransaction()).thenReturn(transaction);

        // Inject the mock UserDao
        loginServlet.setUserDao(userDao);
    }

    @After
    public void tearDown() throws Exception {
        mockedFactoryProvider.close();
    }

    @Test
    public void testLoginWithValidCredentials() throws ServletException, IOException {
        // Mock the request parameters
        when(request.getParameter("userEmail")).thenReturn("valid@example.com");
        when(request.getParameter("userPassword")).thenReturn("validpassword");

        // Mock the UserDao to return a valid user
        User user = new User();
        user.setUserType("normal");
        when(userDao.getUserByEmailAndPassword("valid@example.com", "validpassword")).thenReturn(user);

        // Mock the HttpSession
        when(request.getSession()).thenReturn(httpSession);

        // Mock the PrintWriter
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(printWriter);

        // Call the servlet's doPost method
        loginServlet.doPost(request, response);

        // Verify the session attribute and redirection
        verify(httpSession).setAttribute("current_user", user);
        verify(response).sendRedirect("index.jsp");
    }

    @Test
    public void testLoginWithInvalidCredentials() throws ServletException, IOException {
        // Mock the request parameters
        when(request.getParameter("userEmail")).thenReturn("example.com");
        when(request.getParameter("userPassword")).thenReturn("msh2000812");

        // Mock the UserDao to return null (invalid user)
        when(userDao.getUserByEmailAndPassword("example.com", "msh2000812")).thenReturn(null);

        // Mock the HttpSession
        when(request.getSession()).thenReturn(httpSession);

        // Call the servlet's doPost method
        loginServlet.doPost(request, response);

        // Verify the session attribute and redirection
        verify(httpSession).setAttribute("message", "Invalid Details! Try Again...");
        verify(response).sendRedirect("login.jsp");
    }

    @Test
    public void testLoginWithInvalidPassword() throws ServletException, IOException {
        // Mock the request parameters
        when(request.getParameter("userEmail")).thenReturn("minuri@gmail.com");
        when(request.getParameter("userPassword")).thenReturn("!!!");

        // Mock the UserDao to return null for the invalid password
        when(userDao.getUserByEmailAndPassword("minuri@gmail.com", "!!!")).thenReturn(null);

        // Mock the HttpSession
        when(request.getSession()).thenReturn(httpSession);

        // Call the servlet's doPost method
        loginServlet.doPost(request, response);

        // Verify the session attribute and redirection
        verify(httpSession).setAttribute("message", "Invalid Details! Try Again...");
        verify(response).sendRedirect("login.jsp");
    }

  @Test
    public void testEmptyEmailPassword() throws ServletException, IOException {
        // Mock the request parameters
        when(request.getParameter("userEmail")).thenReturn("");
        when(request.getParameter("userPassword")).thenReturn("");

        // Mock the UserDao to return null for the invalid password and email
        when(userDao.getUserByEmailAndPassword("", "")).thenReturn(null);

        // Mock the HttpSession
        when(request.getSession()).thenReturn(httpSession);

        // Call the servlet's doPost method
        loginServlet.doPost(request, response);

        // Verify the session attribute and redirection
        verify(httpSession).setAttribute("message", "Invalid Details! Try Again...");
        verify(response).sendRedirect("login.jsp");
    }


 @Test
    public void testEmptyEmail() throws ServletException, IOException {
        // Mock the request parameters
        when(request.getParameter("userEmail")).thenReturn("");
        when(request.getParameter("userPassword")).thenReturn("msh2000812");

        // Mock the UserDao to return null for the invalid password and email
        when(userDao.getUserByEmailAndPassword("", "msh2000812")).thenReturn(null);

        // Mock the HttpSession
        when(request.getSession()).thenReturn(httpSession);

        // Call the servlet's doPost method
        loginServlet.doPost(request, response);

        // Verify the session attribute and redirection
        verify(httpSession).setAttribute("message", "Invalid Details! Try Again...");
        verify(response).sendRedirect("login.jsp");
    }

@Test
    public void testEmptyPassword() throws ServletException, IOException {
        // Mock the request parameters
        when(request.getParameter("userEmail")).thenReturn("minur@gmail.com");
        when(request.getParameter("userPassword")).thenReturn("");

        // Mock the UserDao to return null for the invalid password and email
        when(userDao.getUserByEmailAndPassword("minur@gmail.com", "")).thenReturn(null);

        // Mock the HttpSession
        when(request.getSession()).thenReturn(httpSession);

        // Call the servlet's doPost method
        loginServlet.doPost(request, response);

        // Verify the session attribute and redirection
        verify(httpSession).setAttribute("message", "Invalid Details! Try Again...");
        verify(response).sendRedirect("login.jsp");
    }


    @Test
    public void testAdminLoginWithValidCredentials() throws ServletException, IOException {
        // Mock the request parameters
        when(request.getParameter("userEmail")).thenReturn("admin@example.com");
        when(request.getParameter("userPassword")).thenReturn("admin");

        // Mock the UserDao to return a valid admin user
        User user = new User();
        user.setUserType("admin");
        when(userDao.getUserByEmailAndPassword("admin@example.com", "admin")).thenReturn(user);

        // Mock the HttpSession
        when(request.getSession()).thenReturn(httpSession);

        // Call the servlet's doPost method
        loginServlet.doPost(request, response);

        // Verify the session attribute and redirection
        verify(httpSession).setAttribute("current_user", user);
        verify(response).sendRedirect("admin.jsp");
    }

@Test
    public void testAdminLoginWithInvalidUserMail() throws ServletException, IOException {
        // Mock the request parameters
        when(request.getParameter("userEmail")).thenReturn("invalid@example.com");
        when(request.getParameter("userPassword")).thenReturn("adminpassword");

        // Mock the UserDao to return null (invalid user)
        when(userDao.getUserByEmailAndPassword("admin@gmail.com", "adminpassword")).thenReturn(null);

        // Mock the HttpSession
        when(request.getSession()).thenReturn(httpSession);

        // Call the servlet's doPost method
        loginServlet.doPost(request, response);

        // Verify the session attribute and redirection
        verify(httpSession).setAttribute("message", "Invalid Details! Try Again...");
        verify(response).sendRedirect("login.jsp");
    }

@Test
    public void testAdminLoginWithInvalidPassword() throws ServletException, IOException {
        // Mock the request parameters
        when(request.getParameter("userEmail")).thenReturn("admin@gmail.com");
        when(request.getParameter("userPassword")).thenReturn("adminpassword");

        // Mock the UserDao to return null (invalid user)
        when(userDao.getUserByEmailAndPassword("admin@gmail.com", "admin")).thenReturn(null);

        // Mock the HttpSession
        when(request.getSession()).thenReturn(httpSession);

        // Call the servlet's doPost method
        loginServlet.doPost(request, response);

        // Verify the session attribute and redirection
        verify(httpSession).setAttribute("message", "Invalid Details! Try Again...");
        verify(response).sendRedirect("login.jsp");
    }

@Test
    public void testAdminEmptyEmailPassword() throws ServletException, IOException {
        // Mock the request parameters
        when(request.getParameter("userEmail")).thenReturn("");
        when(request.getParameter("userPassword")).thenReturn("");

        // Mock the UserDao to return null (invalid user)
        when(userDao.getUserByEmailAndPassword("admin@gmail.com", "admin")).thenReturn(null);

        // Mock the HttpSession
        when(request.getSession()).thenReturn(httpSession);

        // Call the servlet's doPost method
        loginServlet.doPost(request, response);

        // Verify the session attribute and redirection
        verify(httpSession).setAttribute("message", "Invalid Details! Try Again...");
        verify(response).sendRedirect("login.jsp");
    }


@Test
    public void testAdminEmptyEmail() throws ServletException, IOException {
        // Mock the request parameters
        when(request.getParameter("userEmail")).thenReturn("");
        when(request.getParameter("userPassword")).thenReturn("admin");

        // Mock the UserDao to return null (invalid user)
        when(userDao.getUserByEmailAndPassword("admin@gmail.com", "admin")).thenReturn(null);

        // Mock the HttpSession
        when(request.getSession()).thenReturn(httpSession);

        // Call the servlet's doPost method
        loginServlet.doPost(request, response);

        // Verify the session attribute and redirection
        verify(httpSession).setAttribute("message", "Invalid Details! Try Again...");
        verify(response).sendRedirect("login.jsp");
    }

@Test
    public void testAdminEmptyPassword() throws ServletException, IOException {
        // Mock the request parameters
        when(request.getParameter("userEmail")).thenReturn("admin@gmail.com");
        when(request.getParameter("userPassword")).thenReturn("");

        // Mock the UserDao to return null (invalid user)
        when(userDao.getUserByEmailAndPassword("admin@gmail.com", "admin")).thenReturn(null);

        // Mock the HttpSession
        when(request.getSession()).thenReturn(httpSession);

        // Call the servlet's doPost method
        loginServlet.doPost(request, response);

        // Verify the session attribute and redirection
        verify(httpSession).setAttribute("message", "Invalid Details! Try Again...");
        verify(response).sendRedirect("login.jsp");
    }



}
