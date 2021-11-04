package com.example.demo.multi.infrastructure.repo;

import com.example.demo.multi.infrastructure.data.Convention;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ConventionRepository extends JpaRepository<Convention, UUID> {
}
