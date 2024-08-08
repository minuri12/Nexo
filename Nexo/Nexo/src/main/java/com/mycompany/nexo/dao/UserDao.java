package com.mycompany.nexo.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import com.mycompany.mycart.entities.User;


public class UserDao {
    private SessionFactory factory;
   

    public UserDao(SessionFactory factory) {
        this.factory = factory;
    }

    // Get user by email and password
    public User getUserByEmailAndPassword(String email, String password) {
        User user = null;
        
        try (Session session = this.factory.openSession()) {
            String hql = "FROM User WHERE userEmail = :e AND userPassword = :p";
            Query<User> query = session.createQuery(hql, User.class);
            query.setParameter("e", email);
            query.setParameter("p", password);
            user = query.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return user;
    }
}
