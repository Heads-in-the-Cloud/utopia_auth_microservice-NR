package com.smoothstack.utopia.auth.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String givenName;
    private String familyName;
    private String email;
    private String phone;
    private String username;
    private String password;
    @ManyToOne
    @JoinColumn(name = "role_id")
    private UserRole userRole;
    @Transient
    private Collection<UserRole> userRoles = new ArrayList<>();

    public User(String username, String password, UserRole userRole) {
        this.username = username;
        this.password = password;
        UserRole role = Objects.requireNonNullElseGet(userRole, () -> new UserRole(3, "Traveler"));
        this.userRole = role;
        userRoles.add(role);
    }

    public Collection<UserRole> getUserRoles() {
        Collection<UserRole> roles = new ArrayList<>();
        roles.add(userRole);
        return roles;
    }
}
