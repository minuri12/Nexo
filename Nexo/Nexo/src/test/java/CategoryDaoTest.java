//package com.mycompany.nexo.dao;
//
//import com.mycompany.mycart.entities.Category;
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.hibernate.Transaction;
//import org.hibernate.query.Query;
//import org.junit.Before;
//import org.junit.Test;
//import org.mockito.*;
//
//import java.util.Arrays;
//import java.util.List;
//
//import static org.junit.Assert.*;
//import static org.mockito.Mockito.*;
//
//public class CategoryDaoTest {
//
//    @Mock
//    private SessionFactory sessionFactory;
//
//    @Mock
//    private Session session;
//
//    @Mock
//    private Transaction transaction;
//
//    @InjectMocks
//    private CategoryDao categoryDao;
//
//    @Before
//    public void setUp() {
//        MockitoAnnotations.initMocks(this);
//        when(sessionFactory.openSession()).thenReturn(session);
//        when(session.beginTransaction()).thenReturn(transaction);
//    }
//
//    @Test
//    public void testSaveCategory() {
//        Category category = new Category();
//        category.setCategoryTitle("Electronics");
//
//        when(session.save(category)).thenReturn(1);
//
//        int categoryId = categoryDao.saveCategory(category);
//
//        assertEquals(1, categoryId);
//        verify(session).save(category);
//        verify(transaction).commit();
//        verify(session).close();
//    }
//
//@Test
//    public void testUpdateCategory() {
//        Category category = new Category();
//        category.setCategoryTitle("Electronics");
//        category.setCategoryDescription("All kinds of electronic items");
//
//        categoryDao.updateCategory(category);
//
//        verify(session).update(category);
//        verify(transaction).commit();
//        verify(session).close();
//    }
//
//    @Test(expected = IllegalArgumentException.class)
//    public void testUpdateCategoryWithEmptyTitle() {
//        Category category = new Category();
//        category.setCategoryTitle("");
//        category.setCategoryDescription("Some description");
//
//        categoryDao.updateCategory(category);
//    }
//
//    @Test(expected = IllegalArgumentException.class)
//    public void testUpdateCategoryWithEmptyDescription() {
//        Category category = new Category();
//        category.setCategoryTitle("Electronics");
//        category.setCategoryDescription("");
//
//        categoryDao.updateCategory(category);
//    }
//    @Test
//    public void testDeleteCategory() {
//        Category category = new Category();
//        category.setCategoryId(1);
//
//        when(session.get(Category.class, 1)).thenReturn(category);
//
//        boolean result = categoryDao.deleteCategory(1);
//
//        assertTrue(result);
//        verify(session).delete(category);
//        verify(transaction).commit();
//        verify(session).close();
//    }
//
//   
//    @Test
//    public void testGetCategoryById() {
//        Category category = new Category();
//        category.setCategoryId(1);
//        category.setCategoryTitle("Electronics");
//
//        when(session.get(Category.class, 1)).thenReturn(category);
//
//        // Simulate session isOpen and close behavior
//        when(session.isOpen()).thenReturn(true).thenReturn(false);
//
//        Category result = categoryDao.getCategoryById(1);
//
//        assertNotNull(result);
//        assertEquals("Electronics", result.getCategoryTitle());
//        verify(session).get(Category.class, 1);
//        verify(session).close();
//    }
//
//    @Test
//    public void testGetCategoryByTitle() {
//        Category category = new Category();
//        category.setCategoryTitle("Electronics");
//
//        String hql = "FROM Category WHERE categoryTitle = :title";
//        Query<Category> query = mock(Query.class);
//        when(query.uniqueResult()).thenReturn(category);
//        when(query.setParameter("title", "Electronics")).thenReturn(query);
//        when(session.createQuery(hql, Category.class)).thenReturn(query);
//
//        // Simulate session isOpen and close behavior
//        when(session.isOpen()).thenReturn(true).thenReturn(false);
//
//        Category result = categoryDao.getCategoryByTitle("Electronics");
//
//        assertNotNull(result);
//        assertEquals("Electronics", result.getCategoryTitle());
//        verify(session).createQuery(hql, Category.class);
//        verify(query).setParameter("title", "Electronics");
//        verify(session).close();
//    }
//}
