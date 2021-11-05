package com.example.demo.multi.infrastructure.ports.output.data;

import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Table(name = "Book", indexes = {
        @Index(name = "idx_book_title", columnList = "title")
})
@Entity
public class Book {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "title", nullable = false)
    private String title;

    @ManyToMany(mappedBy = "books")
    private Set<Author> authors;

    public Book() {
        this.authors = new HashSet<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        Book book = (Book) o;
        return id != null && Objects.equals(id, book.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public void addAuthor(Author author) {
        this.authors.add(author);
        author.getBooks().add(this);
    }

    public void removeAuthor(Author author) {
        this.authors.remove(author);
        author.getBooks().remove(this);
    }

    public String getTitle() {
        return title;
    }

    public Book setTitle(String title) {
        this.title = title;
        return this;
    }

    public Set<Author> getAuthors() {
        return authors;
    }

    public Book setAuthors(Set<Author> authors) {
        this.authors = authors;
        return this;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
