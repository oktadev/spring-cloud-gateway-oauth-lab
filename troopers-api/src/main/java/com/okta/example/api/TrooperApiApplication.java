package com.okta.example.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@SpringBootApplication
public class TrooperApiApplication {

  public static void main(String[] args) {
    SpringApplication.run(TrooperApiApplication.class, args);
  }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        return http
            .authorizeRequests()
                .anyRequest().authenticated() // all request require auth
                .and()
            .oauth2ResourceServer().jwt().and().and() // validate JWT bearer token from header
            .sessionManagement().disable() // no Sessions
            .build();
    }
}
