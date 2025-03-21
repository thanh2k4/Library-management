package com.example.librarymanagementbackend.repository;

import com.example.librarymanagementbackend.constants.BookCopyStatus;
import com.example.librarymanagementbackend.entity.BookCopy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookCopyRepository extends JpaRepository<BookCopy, Long> {
    @Query("SELECT bc FROM BookCopy bc WHERE (:bookId IS NULL OR bc.book.id = :bookId) " +
            "AND (:bookTitle IS NULL OR bc.book.title LIKE %:bookTitle%) " +
            "AND (:status IS NULL OR bc.status = :status) " +
            "ORDER BY bc.updatedAt DESC")
    List<BookCopy> findAllByFilters(@Param("bookId") Long bookId,
            @Param("bookTitle") String bookTitle,
            @Param("status") BookCopyStatus status);

    @Query("SELECT COUNT(bc) FROM BookCopy bc WHERE (:bookId IS NULL OR bc.book.id = :bookId) " +
            "AND (:bookTitle IS NULL OR bc.book.title LIKE %:bookTitle%) " +
            "AND (:status IS NULL OR bc.status = :status)")
    long countByFilters(@Param("bookId") Long bookId,
            @Param("bookTitle") String bookTitle,
            @Param("status") BookCopyStatus status);

    Optional<BookCopy> findFirstByBookIdAndStatus(Long bookId, BookCopyStatus status);
}