package com.example.utils;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.FlywayException;

import java.sql.Connection;
import java.sql.SQLException;

public class DBConnection {

    final static Logger logger = LogManager.getLogger(DBConnection.class);

    private static DBConnection instance;

    private static HikariDataSource dataSource;

    private DBConnection() {

        try {
            Class.forName("org.postgresql.Driver");

            HikariConfig config = new HikariConfig();
            config.setJdbcUrl("jdbc:postgresql://localhost:5432/postgres");
            config.setUsername("postgres");
            config.setPassword("qwerty123456");
            config.setConnectionTimeout(50000);
            config.setMaximumPoolSize(10);
            dataSource = new HikariDataSource(config);

//            Flyway flyway = Flyway.configure().dataSource(dataSource).baselineOnMigrate(true).load();
//            logger.info("start migration");
//            flyway.migrate();
//            logger.info("migration done");
        } catch (ClassNotFoundException | FlywayException e) {
            logger.error("", e);
            e.printStackTrace();
        }
    }

    public static DBConnection getInstance() {
        if (instance == null) {
            synchronized (DBConnection.class) {
                if (instance == null) {
                    instance = new DBConnection();
                }
            }
        }
        return instance;
    }

    public synchronized Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public void destroy() {
        dataSource.close();
    }
}