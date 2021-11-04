package com.example.demo.multi.infrastructure.repo;

import com.example.demo.multi.infrastructure.data.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, UUID> {
}
