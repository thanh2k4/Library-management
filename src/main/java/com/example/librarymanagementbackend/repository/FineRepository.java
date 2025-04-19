package com.example.librarymanagementbackend.repository;

import com.example.librarymanagementbackend.entity.Fine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FineRepository extends JpaRepository<Fine, Long> {
        @Query("SELECT f FROM Fine f " +
                        "JOIN f.bookLoan bl " +
                        "WHERE (:userId IS NULL OR bl.user.id = :userId) " +
                        "ORDER BY f.UpdatedAt DESC")
        List<Fine> findAllFinesByFilters(@Param("userId") Long userId);

        @Query("SELECT COUNT(f) " +
                        "FROM Fine f " +
                        "JOIN f.bookLoan bl " +
                        "WHERE (:userId IS NULL OR bl.user.id = :userId)")
        long countByFilters(@Param("userId") Long userId);
}