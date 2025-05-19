package com.example.librarymanagementbackend.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "name", unique = true, nullable = false)
    String name;

    @Column(name = "user_name")
    String userName;

    @Column(name = "email", unique = true)
    String email;

    @Column(name = "password", nullable = false)
    String password;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    Date CreatedAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    Date UpdatedAt;

    @ManyToOne
    Role role;

    @PrePersist
    protected void onCreate() {
        CreatedAt = new Date();
        UpdatedAt = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        UpdatedAt = new Date();
    }
}