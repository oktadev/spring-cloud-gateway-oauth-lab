package com.okta.example.mvc.resources;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Map;

@RestController
public class UserResource {

    @GetMapping(path = "/user")
    Map<String, String> currentUser(Principal principal) {
        return Map.of("username", principal.getName());
    }
}
