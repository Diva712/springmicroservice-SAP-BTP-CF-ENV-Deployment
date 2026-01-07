package com.diva.springmicroservice.config;

import javax.sql.DataSource;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@Profile("cloud")
public class CloudDataSourceConfig {

    @Bean
    @Primary
    public DataSource dataSource() {
        String vcapServices = System.getenv("VCAP_SERVICES");

        if (vcapServices == null || vcapServices.isEmpty()) {
            throw new RuntimeException("❌ VCAP_SERVICES environment variable is not set");
        }

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(vcapServices);

            // Find PostgreSQL service
            JsonNode postgresNode = null;
            if (root.has("postgresql-db")) {
                postgresNode = root.get("postgresql-db").get(0).get("credentials");
            } else if (root.has("postgres")) {
                postgresNode = root.get("postgres").get(0).get("credentials");
            }

            if (postgresNode == null) {
                throw new RuntimeException("❌ PostgreSQL service not found in VCAP_SERVICES");
            }

            // Extract credentials separately (NOT from URI)
            String username = postgresNode.get("username").asText();
            String password = postgresNode.get("password").asText();
            String hostname = postgresNode.get("hostname").asText();
            String port = postgresNode.get("port").asText();
            String dbname = postgresNode.get("dbname").asText();

            // 🔥 BUILD JDBC URL MANUALLY (Don't use the 'uri' field directly!)
            String jdbcUrl = String.format(
                "jdbc:postgresql://%s:%s/%s",
                hostname,
                port,
                dbname
            );

            System.out.println("✅ Successfully configured DataSource from VCAP_SERVICES");
            System.out.println("🔗 JDBC URL: " + jdbcUrl);
            System.out.println("👤 Username: " + username);
            System.out.println("🏠 Hostname: " + hostname);
            System.out.println("🔌 Port: " + port);
            System.out.println("💾 Database: " + dbname);

            // Build DataSource
            return DataSourceBuilder
                    .create()
                    .url(jdbcUrl)
                    .username(username)
                    .password(password)
                    .driverClassName("org.postgresql.Driver")
                    .build();

        } catch (Exception e) {
            System.err.println("❌ Failed to configure DataSource: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to parse VCAP_SERVICES: " + e.getMessage(), e);
        }
    }
}