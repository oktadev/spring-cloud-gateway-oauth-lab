package com.okta.example.mvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;

@SpringBootApplication
public class TrooperMvcApplication {

  public static void main(String[] args) {
    SpringApplication.run(TrooperMvcApplication.class, args);
  }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        return http
            // Form login, see log file for generated password, username is... `user`
            .formLogin().and()

            .authorizeRequests()
                .anyRequest().authenticated()
                .and()

            .requestCache().disable()
            .build();
    }
}
