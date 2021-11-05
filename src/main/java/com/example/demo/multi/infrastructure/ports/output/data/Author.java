package com.example.demo.multi.infrastructure.ports.output.data;

import org.hibernate.Hibernate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
public class Author {

    @Id
    @GeneratedValue
    private UUID id;
    @Column(name = "name", nullable = false)
    private String name;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "books_author",
            joinColumns = @JoinColumn(name = "author_id", foreignKey = @ForeignKey(name = "author_book_fk")),
            inverseJoinColumns = @JoinColumn(name = "book_id", foreignKey = @ForeignKey(name = "book_author_fk")))
    private Set<Book> books;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Convention> conventions;

    public Author() {
        this.books = new HashSet<>();
        this.conventions = new HashSet<>();
        this.id = UUID.randomUUID();
    }

    public void addBook(Book book) {
        this.books.add(book);
        book.addAuthor(this);
    }

    public void removeBook(Book book) {
        this.books.add(book);
        book.getAuthors().remove(this);
    }

    public void addConvention(Convention convention) {
        this.conventions.add(convention);
        convention.setAuthor(this);
    }

    public void removeConvention(Convention convention) {
        this.conventions.remove(convention);
        convention.setAuthor(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        Author author = (Author) o;
        return id != null && Objects.equals(id, author.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public UUID getId() {
        return id;
    }

    public Author setId(UUID id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Author setName(String name) {
        this.name = name;
        return this;
    }

    public Set<Book> getBooks() {
        return books;
    }

    public Author setBooks(Set<Book> books) {
        this.books = books;
        return this;
    }

    public Set<Convention> getConventions() {
        return conventions;
    }

    public Author setConventions(Set<Convention> conventions) {
        this.conventions = conventions;
        return this;
    }
}
