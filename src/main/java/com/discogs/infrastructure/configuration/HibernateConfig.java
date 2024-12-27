package com.discogs.infrastructure.configuration;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class HibernateConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(HibernateConfig.class);
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            Configuration configuration = new Configuration();
            configuration.configure("hibernate.cfg.xml");

            return configuration.configure().buildSessionFactory();
        } catch (Exception ex) {
            LOGGER.error("Initial SessionFactory creation failed.");
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    private HibernateConfig() {}
}
