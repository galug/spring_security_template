package com.example.security.global.common;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@NoArgsConstructor
public class Common {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id", nullable = false)
    private Long id;

    // @CreationTimestamp
    // @Column(nullable = false, length =  20, updatable = false)
    // private LocalDateTime createdAt;
    //
    // @UpdateTimestamp
    // @Column(length = 20)
    // private LocalDateTime updatedAt;
}
