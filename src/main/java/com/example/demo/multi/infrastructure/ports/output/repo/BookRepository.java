package com.example.demo.multi.infrastructure.ports.output.repo;

import com.example.demo.multi.infrastructure.ports.output.data.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, UUID> {
}
