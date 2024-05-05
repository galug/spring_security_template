package com.example.security.domain.auth.entity;

import com.example.security.global.common.Common;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="USER")
@Getter
@NoArgsConstructor(access= AccessLevel.PROTECTED)
public class User extends Common {

    @Column(name ="email", nullable = true, unique = true, length = 50)
    private String email;

    @Column(name = "user_id", nullable = true, unique = true, length = 20)
    private String userId;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private Type type;

    @Builder
    public User(String email, String userId, String password, Role role, Type type) {
        this.email = email;
        this.userId = userId;
        this.password = password;
        this.role = role;
        this.type = type;
    }

    @Override
    public String toString() {
        return "User{" +
            "email='" + email + '\'' +
            ", userId='" + userId + '\'' +
            ", password='" + password + '\'' +
            ", role=" + role +
            ", type=" + type +
            '}';
    }
}
