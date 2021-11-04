package com.example.demo.multi.infrastructure.repo;

import com.example.demo.multi.infrastructure.data.Author;
import com.example.demo.multi.infrastructure.repo.custom.AuthorCustomRepo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AuthorRepository extends JpaRepository<Author, UUID>, AuthorCustomRepo {
}
