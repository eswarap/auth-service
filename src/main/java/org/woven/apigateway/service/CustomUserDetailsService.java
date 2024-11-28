package org.woven.apigateway.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.woven.apigateway.entity.User;
import org.woven.apigateway.model.UserInfo;
import org.woven.apigateway.repository.UserRepository;

@Slf4j
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("Entering in loadUserByUsername Method...");
        User user = userRepository.findByUsername(username);
        if(user == null) {
            log.error("Username not found: " + username);
            throw new UsernameNotFoundException("could not find user..!!");
        }

        return new UserInfo(user);
    }


}
