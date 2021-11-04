package com.example.demo.multi.infrastructure.ports.output.repo;

import com.example.demo.multi.infrastructure.ports.output.data.Convention;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ConventionRepository extends JpaRepository<Convention, UUID> {
}
