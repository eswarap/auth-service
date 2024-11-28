package org.woven.apigateway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.woven.apigateway.entity.Role;
import org.woven.apigateway.entity.User;
import org.woven.apigateway.exception.AuthException;
import org.woven.apigateway.model.LoginRequest;
import org.woven.apigateway.model.LoginResponse;
import org.woven.apigateway.service.AuthService;
import org.woven.apigateway.service.UserService;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserService userService;


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody final LoginRequest loginRequest) {
        try {
            String token = authService.login(loginRequest);
            LoginResponse loginResponse = new LoginResponse(token,"Bearer");
            loginResponse.setAccessToken(token);
            return ResponseEntity.ok().body(loginResponse);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody final LoginRequest loginRequest) {
        if (userService.findByUsername(loginRequest.getUsername()) != null) {
            return ResponseEntity.badRequest().body("Username is already taken.");
        }
        User user = getUser(loginRequest);
        userService.save(user);
        return ResponseEntity.ok("User registered successfully.");
    }

    private static User getUser(LoginRequest loginRequest) {
        User user = new User();
        user.setUsername(loginRequest.getUsername());
        user.setPassword(loginRequest.getPassword());
        Role role = new Role();
        role.setRoleName("USER");
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);
        return user;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/ping")
    public String test() {
        try {
            return "Welcome";
        } catch (Exception e){
            throw new AuthException(e);
        }
    }
}
