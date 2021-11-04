package com.example.demo.multi.infrastructure.helper;

import com.example.demo.multi.infrastructure.ports.input.dto.AuthorDTO;
import com.example.demo.multi.infrastructure.ports.output.data.Author;
import com.example.demo.multi.infrastructure.ports.output.data.Book;
import com.example.demo.multi.infrastructure.ports.output.data.Convention;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class AuthorRequestMapper {

    public Author entityFrom(AuthorDTO dto) {
        Set<Book> books = dto.getBooks().stream().map(bookTitle -> new Book().setTitle(bookTitle))
                .collect(Collectors.toSet());

        List<Convention> conventions = dto.getLocations().stream()
                .map(location -> new Convention().setLocation(location)).collect(Collectors.toList());

        Author entity = new Author().setName(dto.getAuthorName());
        conventions.forEach(entity::addConvention);
        books.forEach(book -> book.addAuthor(entity));
        return entity;
    }

    public AuthorDTO toDto(Author author) {
        AuthorDTO dto = new AuthorDTO();
        dto.setAuthorName(author.getName());
        Set<String> books = author.getBooks().stream().map(Book::getTitle).collect(Collectors.toSet());
        dto.setBooks(books);
        List<String> conventionLocations = author.getConventions().stream().map(Convention::getLocation)
                .collect(Collectors.toList());
        dto.setLocations(conventionLocations);
        return dto;
    }


}
