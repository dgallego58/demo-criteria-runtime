package com.example.demo.multi.infrastructure.ports.input.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Schema(description = "The author to be created with his books and convention locations",
        example = "{\n" +
                "  \"authorName\": \"Gabriel García Márquez\",\n" +
                "  \"books\": [\n" +
                "    \"Memorias de mis putas tristes\",\n" +
                "    \"Relato de un Náufrago\",\n" +
                "    \"Cien Años de Soledad\",\n" +
                "    \"El coronel no tiene quien le escriba\"\n" +
                "  ],\n" +
                "  \"locations\": [\n" +
                "    \"Aracataca\",\n" +
                "    \"Universidad Nacional de Colombia\"\n" +
                "  ]\n" +
                "}")
public class AuthorDTO {

    private String authorName;
    private Set<String> books;
    private List<String> locations;

    public AuthorDTO() {
        this.books = new HashSet<>();
        this.locations = new ArrayList<>();
    }

    public String getAuthorName() {
        return authorName;
    }

    public AuthorDTO setAuthorName(String authorName) {
        this.authorName = authorName;
        return this;
    }

    public Set<String> getBooks() {
        return books;
    }

    public AuthorDTO setBooks(Set<String> books) {
        this.books = books;
        return this;
    }

    public List<String> getLocations() {
        return locations;
    }

    public AuthorDTO setLocations(List<String> locations) {
        this.locations = locations;
        return this;
    }
}
