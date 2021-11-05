package com.example.demo.multi.infrastructure.ports.output.data;

import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Objects;
import java.util.UUID;


@Table(name = "Convention", indexes = {
        @Index(name = "idx_convention_location", columnList = "location")
})
@Entity
public class Convention {
    @Id
    @GeneratedValue
    private UUID id;

    private String location;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", foreignKey = @ForeignKey(name = "convention_author_fk"))
    private Author author;


    public String getLocation() {
        return location;
    }

    public Convention setLocation(String location) {
        this.location = location;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        Convention that = (Convention) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public Author getAuthor() {
        return author;
    }

    public Convention setAuthor(Author author) {
        this.author = author;
        return this;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
