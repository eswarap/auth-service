package org.woven.apigateway.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.woven.apigateway.entity.User;

public interface UserRepository extends JpaRepository<User,String> {
    User findByUsername(String username);
}
