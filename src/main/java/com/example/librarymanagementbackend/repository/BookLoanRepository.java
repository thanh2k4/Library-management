package com.example.librarymanagementbackend.repository;

import com.example.librarymanagementbackend.constants.BookLoanStatus;
import com.example.librarymanagementbackend.entity.BookLoan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookLoanRepository extends JpaRepository<BookLoan, Long> {

        @Query("SELECT bl FROM BookLoan bl WHERE (:userId IS NULL OR bl.user.id = :userId) " +
                        "AND (:bookTitle IS NULL OR bl.bookCopy.book.title LIKE %:bookTitle%) " +
                        "AND (:status IS NULL OR bl.status = :status) " +
                        "ORDER BY bl.UpdatedAt DESC")
        List<BookLoan> findAllByFilters(@Param("userId") Long userId,
                        @Param("bookTitle") String bookTitle,
                        @Param("status") BookLoanStatus status);

        @Query("SELECT COUNT(bl) FROM BookLoan bl WHERE (:userId IS NULL OR bl.user.id = :userId) " +
                        "AND (:bookTitle IS NULL OR bl.bookCopy.book.title LIKE %:bookTitle%) " +
                        "AND (:status IS NULL OR bl.status = :status)")
        long countByFilters(@Param("userId") Long userId,
                        @Param("bookTitle") String bookTitle,
                        @Param("status") BookLoanStatus status);
}