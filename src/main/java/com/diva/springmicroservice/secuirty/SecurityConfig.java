package com.diva.springmicroservice.secuirty;

import com.sap.cloud.security.xsuaa.XsuaaServiceConfiguration;
import com.sap.cloud.security.xsuaa.token.TokenAuthenticationConverter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig {

    private final XsuaaServiceConfiguration xsuaaServiceConfiguration;

    public SecurityConfig(XsuaaServiceConfiguration xsuaaServiceConfiguration) {
        this.xsuaaServiceConfiguration = xsuaaServiceConfiguration;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/anubhav.svc/**").hasAuthority("Display")
                        .requestMatchers("/webapp/**").hasAuthority("Edit")
                        .requestMatchers("/vendors/**").authenticated()
                        .anyRequest().denyAll())
                .oauth2ResourceServer(
                        oauth2 -> oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())));

        return http.build();
    }

    private Converter<Jwt, AbstractAuthenticationToken> jwtAuthenticationConverter() {
        TokenAuthenticationConverter converter = new TokenAuthenticationConverter(xsuaaServiceConfiguration);

        // This maps $XSAPPNAME.Display -> Display
        converter.setLocalScopeAsAuthorities(true);

        return converter;
    }
}

// In anubhav tutorial some code is old that why it is out dated i need to
// configure based on my requirement.
