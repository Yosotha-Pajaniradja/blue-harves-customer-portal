package com.blue.harvest.customer.management.api.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Arrays;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;
import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

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
          config.setAllowedHeaders(Arrays.asList("X-Requested-With", "Origin", "Content-Type", "Accept", "Authorization"));
          config.setAllowedMethods(Arrays.asList("GET", "POST", "OPTIONS", "DELETE", "PUT", "PATCH"));
          config.addAllowedOrigin("http://localhost:4200");
          config.setAllowCredentials(true);
          return config;
        }).and().csrf().disable().authorizeHttpRequests()
        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/h2-console/**","/h2-console/",
            "/api/v1/customers/*", "/localhost:4200/**").permitAll().anyRequest().authenticated().and()
        .headers().frameOptions().sameOrigin()
        .and().sessionManagement().sessionCreationPolicy(STATELESS);  ;
    http.oauth2ResourceServer().jwt().jwtAuthenticationConverter(jwtAuthConverter);
    return http.build();
  }
  @Bean
  public WebSecurityCustomizer webSecurityCustomizer() {
    return (web) -> web.ignoring().requestMatchers(new AntPathRequestMatcher("/h2-console/**"));
  }


}
