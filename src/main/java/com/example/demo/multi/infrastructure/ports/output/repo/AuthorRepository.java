package com.example.demo.multi.infrastructure.ports.output.repo;

import com.example.demo.multi.infrastructure.ports.output.data.Author;
import com.example.demo.multi.infrastructure.ports.output.repo.custom.AuthorCustomRepo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AuthorRepository extends JpaRepository<Author, UUID>, AuthorCustomRepo {
}
