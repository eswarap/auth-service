package org.woven.apigateway.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.woven.apigateway.exception.AuthException;
import org.woven.apigateway.model.LoginRequest;
import org.woven.apigateway.util.JwtUtil;

@Service
@Slf4j
public class JwtService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    public String createJwtToken(LoginRequest loginRequest) throws AuthException {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(), loginRequest.getPassword()));
        log.info("authentication details {1} ", authentication);
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AuthException("Invalid username or password");
        } else {
            final UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());
            return jwtUtil.generateToken(userDetails);
        }
    }
}
