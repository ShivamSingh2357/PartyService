package com.candescent.PartyService.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Database configuration class for PostgreSQL connection.
 * Spring Boot auto-configures HikariCP DataSource from application.properties.
 * This class enables JPA features like auditing and transaction management.
 */
@Configuration
@EnableTransactionManagement
@EnableJpaAuditing
@EnableJpaRepositories(basePackages = "com.candescent.PartyService.repository")
public class DatabaseConfig {

    private static final Logger log = LoggerFactory.getLogger(DatabaseConfig.class);

    public DatabaseConfig() {
        log.info("Database configuration initialized with JPA Auditing and Transaction Management enabled");
    }
}
