package com.example.librarymanagementbackend.entity;

import com.example.librarymanagementbackend.constants.BookLoanStatus;
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
@Table(name = "book_loan")
public class BookLoan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "book_copy_id", nullable = false)
    BookCopy bookCopy;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id", nullable = false)
    User user;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    Date loanDate;

    @Temporal(TemporalType.TIMESTAMP)
    Date returnDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = true)
    Date actualReturnDate;

    @Enumerated(EnumType.STRING)
    BookLoanStatus status;

    @Column(nullable = true)
    Long currentBookRequestId;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    Date createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    Date updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
        updatedAt = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Date();
    }
}
