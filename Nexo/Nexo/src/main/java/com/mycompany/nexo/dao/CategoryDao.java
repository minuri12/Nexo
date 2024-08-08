package com.mycompany.nexo.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query; // Import from org.hibernate package

import com.mycompany.mycart.entities.Category;
import com.mycompany.nexo.helper.FactoryProvider;

public class CategoryDao {

    private SessionFactory factory;

    // Constructor to initialize the SessionFactory
    public CategoryDao(SessionFactory factory) {
        this.factory = factory;
    }

    // Method to save a Category object to the database
    public int saveCategory(Category cat) {
        Session session = this.factory.openSession(); // Open a session
        Transaction tx = session.beginTransaction(); // Begin a transaction

        int catId = (int) session.save(cat); // Save the category and get the generated ID
        tx.commit(); // Commit the transaction

        session.close(); // Close the session
        return catId; // Return the generated category ID
    }

  public void updateCategory(Category category) {
        if (category.getCategoryTitle() == null || category.getCategoryTitle().trim().isEmpty() ||
            category.getCategoryDescription() == null || category.getCategoryDescription().trim().isEmpty()) {
            throw new IllegalArgumentException("Category title or description cannot be empty");
        }

        System.out.println("Updating category: " + category.getCategoryTitle());
        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();
        try {
            session.update(category);
            tx.commit();
            System.out.println("Category updated: " + category.getCategoryTitle());
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            System.out.println("Category update failed: " + e.getMessage());
        } finally {
            session.close();
        }
    }


public boolean deleteCategory(int categoryId) {
        Transaction tx = null;
        boolean result = false;
        try (Session session = this.factory.openSession()) {
            tx = session.beginTransaction();
            Category category = session.get(Category.class, categoryId);
            if (category != null) {
                session.delete(category);
                result = true;
            }
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (tx != null) {
                tx.rollback();
            }
        }
        return result;
    }


    public List<Category> getCategories(){
        Session s=this.factory.openSession();
        Query<Category> query=s.createQuery("from Category", Category.class); // Use org.hibernate.query.Query
        List<Category> list=query.list();
        return list;
    }

  

public Category getCategoryById(int categoryId) {
        Category cat = null;
        Session session = null;
        try {
            session = this.factory.openSession();
            cat = session.get(Category.class, categoryId);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return cat;
    }
 public Category getCategoryByTitle(String title) {
        Category category = null;
        Session session = null;
        try {
            session = this.factory.openSession();
            String hql = "FROM Category WHERE categoryTitle = :title";
            Query<Category> query = session.createQuery(hql, Category.class);
            query.setParameter("title", title);
            category = query.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return category;
    }

}
