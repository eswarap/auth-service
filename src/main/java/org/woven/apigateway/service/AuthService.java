package org.woven.apigateway.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.woven.apigateway.exception.AuthException;
import org.woven.apigateway.model.LoginRequest;

@Service
public class AuthService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    public String login(final LoginRequest loginRequest) throws AuthException {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(),
                loginRequest.getPassword()
        ));

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AuthException("Invalid username or password");
        }
        else {
            SecurityContextHolder.getContext().setAuthentication(authentication);

            return jwtService.createJwtToken(loginRequest);
        }
    }
}
