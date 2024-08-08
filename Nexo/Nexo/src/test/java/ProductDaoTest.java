//package com.mycompany.nexo.dao;
//
//import com.mycompany.mycart.entities.Product;
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.hibernate.Transaction;
//import org.hibernate.query.Query;
//import org.junit.Before;
//import org.junit.Test;
//import static org.junit.Assert.*;
//import static org.mockito.Mockito.*;
//
//import java.util.List;
//
//public class ProductDaoTest {
//
//    private SessionFactory sessionFactory;
//    private Session session;
//    private Transaction transaction;
//    private ProductDao productDao;
//
//    @Before
//    public void setUp() {
//        sessionFactory = mock(SessionFactory.class);
//        session = mock(Session.class);
//        transaction = mock(Transaction.class);
//        productDao = new ProductDao(sessionFactory);
//
//        when(sessionFactory.openSession()).thenReturn(session);
//        when(session.beginTransaction()).thenReturn(transaction);
//    }
//
//
//    @Test
//    public void testGetAllProducts() {
//        List<Product> productList = List.of(new Product(), new Product());
//        Query<Product> query = mock(Query.class);
//        when(session.createQuery("from Product", Product.class)).thenReturn(query);
//        when(query.list()).thenReturn(productList);
//
//        List<Product> result = productDao.getAllProducts();
//
//        assertEquals(productList, result);
//        verify(session).createQuery("from Product", Product.class);
//        verify(query).list();
//    }
//
//    @Test
//    public void testDeleteProduct() {
//        int productId = 1;
//        Product product = new Product();
//        when(session.get(Product.class, productId)).thenReturn(product);
//        doNothing().when(session).delete(product);
//        doNothing().when(transaction).commit();
//
//        boolean result = productDao.deleteProduct(productId);
//
//        assertTrue(result);
//        verify(session).get(Product.class, productId);
//        verify(session).delete(product);
//        verify(transaction).commit();
//    }
//
//    @Test
//    public void testUpdateProduct() {
//        Product product = new Product();
//        doNothing().when(session).update(product);
//        doNothing().when(transaction).commit();
//
//        productDao.updateProduct(product);
//
//        verify(session).update(product);
//        verify(transaction).commit();
//    }
//
//
//
//    @Test
//    public void testGetProductById() {
//        int productId = 1;
//        Product product = new Product();
//        when(session.get(Product.class, productId)).thenReturn(product);
//
//        Product result = productDao.getProductById(productId);
//
//        assertEquals(product, result);
//        verify(session).get(Product.class, productId);
//    }
//
//    @Test
//    public void testGetCategoryAllProducts() {
//        int categoryId = 1;
//        List<Product> productList = List.of(new Product(), new Product());
//        Query<Product> query = mock(Query.class);
//        when(session.createQuery("from Product as p where p.category.categoryId = :id", Product.class)).thenReturn(query);
//        when(query.setParameter("id", categoryId)).thenReturn(query);
//        when(query.list()).thenReturn(productList);
//
//        List<Product> result = productDao.getCategoryAllProducts(categoryId);
//
//        assertEquals(productList, result);
//        verify(session).createQuery("from Product as p where p.category.categoryId = :id", Product.class);
//        verify(query).setParameter("id", categoryId);
//        verify(query).list();
//    }
//}
