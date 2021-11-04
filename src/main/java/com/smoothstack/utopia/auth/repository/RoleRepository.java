package com.smoothstack.utopia.auth.repository;

import com.smoothstack.utopia.auth.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<UserRole, Integer> {
    UserRole findByName(String name);
}
