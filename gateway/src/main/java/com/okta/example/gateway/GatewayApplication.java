package com.okta.example.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.oidc.web.server.logout.OidcClientInitiatedServerLogoutSuccessHandler;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.csrf.CookieServerCsrfTokenRepository;
import org.springframework.session.data.redis.config.annotation.web.server.EnableRedisWebSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.security.Principal;
import java.util.Map;

@EnableRedisWebSession
@SpringBootApplication
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    @Bean
    SecurityWebFilterChain springWebFilterChain(ServerHttpSecurity http) throws Exception {

        return http
            .oauth2Login().and()

            .authorizeExchange()
                .anyExchange().authenticated()
                .and()

            .build();
    }

    @Bean
    public GlobalFilter customGlobalFilter() {
        return (exchange, chain) -> exchange.getPrincipal()
            .map(Principal::getName)
            .map(userName -> {
                //adds header to proxied request
                exchange.getRequest().mutate().header("X-Forward-User", userName).build();
                return exchange;
            })
            .flatMap(chain::filter);
    }


    @RestController
    static class UserResource {

        @GetMapping(path = "/user")
        Map<String, Object> currentUser(OAuth2AuthenticationToken token) {
            return Map.of("user", token.getPrincipal().getAttributes());
        }
    }

    @Controller
    static class IndexRedirect {
        @GetMapping("/")
        public Mono<ResponseEntity<String>> index() {
            return Mono.just(new HttpHeaders())
                .doOnNext(header -> header.add("Location", "/mvc/"))
                .map(header -> new ResponseEntity<>(null, header, HttpStatus.MOVED_PERMANENTLY));
        }
    }
}
