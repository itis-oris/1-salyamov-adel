package com.example.utils;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebListener
public class DbListener implements ServletContextListener {
    final static Logger logger = LogManager.getLogger(DbListener.class);

    public void contextInitialized(ServletContextEvent event) {
        DBConnection.getInstance();
        logger.info("Context initialized");
    }

    public void contextDestroyed(ServletContextEvent event) {
        DBConnection.getInstance().destroy();
        logger.info("Context destroyed");
    }
}