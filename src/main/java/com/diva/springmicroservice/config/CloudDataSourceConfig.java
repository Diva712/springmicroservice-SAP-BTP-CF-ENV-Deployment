//-------------------------Hana DB configuration------------------------------//
package com.diva.springmicroservice.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@Profile("cloud")
public class CloudDataSourceConfig {

    @Bean
    @Primary
    public DataSource dataSource() {
        String vcap = System.getenv("VCAP_SERVICES");
        if (vcap == null) {
            throw new IllegalStateException("VCAP_SERVICES not found");
        }

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(vcap);

            JsonNode hanaCloud = root.get("hana-cloud");
            if (hanaCloud == null || !hanaCloud.isArray() || hanaCloud.size() == 0) {
                throw new IllegalStateException("hana-cloud service binding not found");
            }

            JsonNode credentials = hanaCloud.get(0).get("credentials");
            if (credentials == null) {
                throw new IllegalStateException("credentials not found");
            }

            String jdbcUrl = credentials.get("url").asText();
            
            // Get database credentials from environment variables
            String dbUser = System.getenv("HANA_USER");
            String dbPassword = System.getenv("HANA_PASSWORD");
            
            if (dbUser == null || dbPassword == null) {
                throw new IllegalStateException(
                    "HANA_USER and HANA_PASSWORD environment variables must be set. " +
                    "Use: cf set-env springmicroservice HANA_USER DBADMIN && " +
                    "cf set-env springmicroservice HANA_PASSWORD <password>"
                );
            }

            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(jdbcUrl);
            config.setUsername(dbUser);
            config.setPassword(dbPassword);
            config.setDriverClassName("com.sap.db.jdbc.Driver");
            
            // Connection pool settings for free tier
            config.setMaximumPoolSize(5);
            config.setMinimumIdle(1);
            config.setConnectionTimeout(30000);
            config.setIdleTimeout(600000);
            config.setMaxLifetime(1800000);
            
            // Additional HANA-specific settings
            config.addDataSourceProperty("encrypt", "true");
            config.addDataSourceProperty("validateCertificate", "true");

            return new HikariDataSource(config);

        } catch (Exception e) {
            throw new RuntimeException("Failed to configure HANA datasource: " + e.getMessage(), e);
        }
    }
}
//------------------------------------PostgreSQL DB configuration------------------------------//
// package com.diva.springmicroservice.config;

// import javax.sql.DataSource;

// import org.springframework.boot.jdbc.DataSourceBuilder;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.context.annotation.Primary;
// import org.springframework.context.annotation.Profile;

// import com.fasterxml.jackson.databind.JsonNode;
// import com.fasterxml.jackson.databind.ObjectMapper;

// @Configuration
// @Profile("cloud")
// public class CloudDataSourceConfig {

//     @Bean
//     @Primary
//     public DataSource dataSource() {
//         String vcapServices = System.getenv("VCAP_SERVICES");

//         if (vcapServices == null || vcapServices.isEmpty()) {
//             throw new RuntimeException("❌ VCAP_SERVICES environment variable is not set");
//         }

//         try {
//             ObjectMapper mapper = new ObjectMapper();
//             JsonNode root = mapper.readTree(vcapServices);

//             // Find PostgreSQL service
//             JsonNode postgresNode = null;
//             if (root.has("postgresql-db")) {
//                 postgresNode = root.get("postgresql-db").get(0).get("credentials");
//             } else if (root.has("postgres")) {
//                 postgresNode = root.get("postgres").get(0).get("credentials");
//             }

//             if (postgresNode == null) {
//                 throw new RuntimeException("❌ PostgreSQL service not found in VCAP_SERVICES");
//             }

//             // Extract credentials separately (NOT from URI)
//             String username = postgresNode.get("username").asText();
//             String password = postgresNode.get("password").asText();
//             String hostname = postgresNode.get("hostname").asText();
//             String port = postgresNode.get("port").asText();
//             String dbname = postgresNode.get("dbname").asText();

//             // 🔥 BUILD JDBC URL MANUALLY (Don't use the 'uri' field directly!)
//             String jdbcUrl = String.format(
//                 "jdbc:postgresql://%s:%s/%s",
//                 hostname,
//                 port,
//                 dbname
//             );

//             System.out.println("✅ Successfully configured DataSource from VCAP_SERVICES");
//             System.out.println("🔗 JDBC URL: " + jdbcUrl);
//             System.out.println("👤 Username: " + username);
//             System.out.println("🏠 Hostname: " + hostname);
//             System.out.println("🔌 Port: " + port);
//             System.out.println("💾 Database: " + dbname);

//             // Build DataSource
//             return DataSourceBuilder
//                     .create()
//                     .url(jdbcUrl)
//                     .username(username)
//                     .password(password)
//                     .driverClassName("org.postgresql.Driver")
//                     .build();

//         } catch (Exception e) {
//             System.err.println("❌ Failed to configure DataSource: " + e.getMessage());
//             e.printStackTrace();
//             throw new RuntimeException("Failed to parse VCAP_SERVICES: " + e.getMessage(), e);
//         }
//     }
// }