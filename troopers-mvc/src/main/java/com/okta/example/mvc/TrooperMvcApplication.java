package com.okta.example.mvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.preauth.RequestHeaderAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;

@SpringBootApplication
public class TrooperMvcApplication {

  public static void main(String[] args) {
    SpringApplication.run(TrooperMvcApplication.class, args);
  }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        RequestHeaderAuthenticationFilter filter = new RequestHeaderAuthenticationFilter();
        filter.setAuthenticationManager(authenticationManager());
        filter.setPrincipalRequestHeader("X-Forward-User");

        CookieCsrfTokenRepository csrfTokenRepository = new CookieCsrfTokenRepository();
        csrfTokenRepository.setCookiePath("/");

        return http
            // Form login, see log file for generated password, username is... `user`
            .formLogin().and()

            .authorizeRequests()
            .anyRequest().authenticated()
            .and()
            .addFilter(filter)
            .csrf(csrf -> csrf.csrfTokenRepository(csrfTokenRepository))
            .requestCache().disable()
            .build();
    }

    // PLACE HOLDER for custom implementation, database, ldap lookup, etc
    private AuthenticationManager authenticationManager() {
        return authentication -> {
            // Replace this logic with something that looks up your users
            authentication.setAuthenticated(true);
            return authentication;
        };
    }

    @Bean
    public CookieSerializer cookieSerializer() {
        DefaultCookieSerializer cookieSerializer = new DefaultCookieSerializer();
        cookieSerializer.setUseBase64Encoding(false);
        return cookieSerializer;
    }
}
