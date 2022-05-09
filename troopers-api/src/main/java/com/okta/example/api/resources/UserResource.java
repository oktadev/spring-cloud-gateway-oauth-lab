package com.okta.example.api.resources;

import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Map;

/**
 * NOTE: The token sent to this server may represent another service and NOT an actual user.
 */
@RestController
public class UserResource {

    @GetMapping(path = "/user")
    Map<String, Object> currentUser(JwtAuthenticationToken token) {
        return Map.of("token-claims", token.getToken().getClaims());
    }
}
