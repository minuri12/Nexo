package com.mycompany.nexo.helper;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * FactoryProvider for Hibernate SessionFactory
 * Author: HP
 */
public class FactoryProvider {

    private static SessionFactory factory;

    public static SessionFactory getFactory() {
        if (factory == null) {
            try {
                // Create the SessionFactory from hibernate.cfg.xml
                factory = new Configuration().configure().buildSessionFactory();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return factory;
    }

    public static void closeFactory() {
        if (factory != null) {
            factory.close();
        }
    }
}
