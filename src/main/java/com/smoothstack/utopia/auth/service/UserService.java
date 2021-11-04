package com.smoothstack.utopia.auth.service;

import com.smoothstack.utopia.auth.entity.UserRole;
import com.smoothstack.utopia.auth.entity.User;

import java.util.List;

public interface UserService {
    User saveUser(User user);

    UserRole saveRole(UserRole userRole);

    void addRoleToUser(String username, String roleName);

    User getUser(String username);

    List<User> getUsers();
}
