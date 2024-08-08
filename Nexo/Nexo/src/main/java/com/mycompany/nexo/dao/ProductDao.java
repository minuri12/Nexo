package com.mycompany.nexo.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import com.mycompany.mycart.entities.Product;
import java.util.List;
import org.hibernate.query.Query;

public class ProductDao {

    private SessionFactory factory;

    // Constructor to initialize the SessionFactory
    public ProductDao(SessionFactory factory) {
        this.factory = factory;
    }

    public void saveProduct(Product product) {
        Transaction tx = null;
        Session session = null;
        try {
            session = this.factory.openSession(); 
            tx = session.beginTransaction(); 

            session.save(product); 
            tx.commit(); 
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback(); 
            }
            e.printStackTrace(); 
        } finally {
            if (session != null && session.isOpen()) {
                session.close(); 
            }
        }
    }

       public List<Product> getAllProducts() {
        Session session = null;
        List<Product> list = null;
        try {
            session = this.factory.openSession();
            Query<Product> query = session.createQuery("from Product", Product.class);
            list = query.list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return list;
    }

public boolean deleteProduct(int productId) {
        Transaction tx = null;
        boolean result = false;
        try (Session session = this.factory.openSession()) {
            tx = session.beginTransaction();
            Product product = session.get(Product.class, productId);
            if (product != null) {
                session.delete(product);
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


    public void updateProduct(Product product) throws IllegalArgumentException {
       
        Transaction tx = null;
        Session session = null;
        try {
            session = this.factory.openSession();
            tx = session.beginTransaction();

            session.update(product);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }
    public Product getProductById(int productId) {
        Product product = null;
        Session session = null;
        try {
            session = this.factory.openSession();
            product = session.get(Product.class, productId);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return product;
    }


public List<Product> getCategoryAllProducts(int cid) {
    Session session = null;
    List<Product> list = null;
    try {
        session = this.factory.openSession();
        Query<Product> query = session.createQuery("from Product as p where p.category.categoryId = :id", Product.class);
        query.setParameter("id", cid);
        list = query.list();
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        if (session != null && session.isOpen()) {
            session.close();
        }
    }
    return list;
}

}