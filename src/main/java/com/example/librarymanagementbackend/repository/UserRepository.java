package com.example.librarymanagementbackend.repository;

import com.example.librarymanagementbackend.dto.user.response.UserResponse;
import com.example.librarymanagementbackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
        Optional<User> findByName(String name);

        @Query("SELECT new com.example.librarymanagementbackend.dto.user.response.UserResponse(" +
                        "u.id, u.name, u.userName, u.email, u.CreatedAt, u.UpdatedAt, r.id, r.name) " +
                        "FROM User u " +
                        "LEFT JOIN u.role r " +
                        "WHERE (:name IS NULL OR u.name = :name) " +
                        "AND (:email IS NULL OR u.email = :email) " +
                        "AND (:roleId IS NULL OR r.id = :roleId) " +
                        "ORDER BY u.UpdatedAt DESC")
        List<UserResponse> findAllByFilters(@Param("name") String name,
                        @Param("email") String email,
                        @Param("roleId") Long roleId);

        @Query("SELECT COUNT(u) " +
                        "FROM User u " +
                        "LEFT JOIN u.role r " +
                        "WHERE (:name IS NULL OR u.name = :name) " +
                        "AND (:email IS NULL OR u.email = :email) " +
                        "AND (:roleId IS NULL OR r.id = :roleId)")
        long countByFilters(@Param("name") String name,
                        @Param("email") String email,
                        @Param("roleId") Long roleId);
}
