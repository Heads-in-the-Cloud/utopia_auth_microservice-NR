package com.smoothstack.utopia.auth.repository;

import com.smoothstack.utopia.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);
}
