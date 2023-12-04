package com.blue.harvest.customer.management.api.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Collections;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final JwtAuthConverter jwtAuthConverter;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.cors().configurationSource(request -> {
          CorsConfiguration config = new CorsConfiguration();
          config.setAllowedHeaders(Collections.singletonList("*"));
          config.setAllowedMethods(Collections.singletonList("*"));
          config.addAllowedOrigin("*");
          config.setAllowCredentials(true);
          return config;
        }).and().csrf().disable().authorizeHttpRequests()
        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/h2-console/**",
            "/api/v1/customers/*").permitAll().anyRequest().authenticated().and();

    http.oauth2ResourceServer().jwt().jwtAuthenticationConverter(jwtAuthConverter);
    http.sessionManagement().sessionCreationPolicy(STATELESS);

    return http.build();
  }



}
