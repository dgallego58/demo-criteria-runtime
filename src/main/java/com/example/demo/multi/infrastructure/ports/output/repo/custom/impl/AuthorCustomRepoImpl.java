package com.example.demo.multi.infrastructure.ports.output.repo.custom.impl;

import com.example.demo.multi.infrastructure.ports.input.dto.PageFilterDTO;
import com.example.demo.multi.infrastructure.ports.output.data.Author;
import com.example.demo.multi.infrastructure.ports.output.data.Book;
import com.example.demo.multi.infrastructure.ports.output.data.Convention;
import com.example.demo.multi.infrastructure.ports.output.repo.custom.AuthorCustomRepo;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class AuthorCustomRepoImpl implements AuthorCustomRepo {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * método consultando con joins (Posible problema N+1 queries), ya que no trae en la consulta todas las relaciones
     * si no hasta que esta se llame
     *
     * @param conditions los filtros a consultar
     * @return la lista de autores
     */
    @Transactional(readOnly = true)
    public List<Author> authors(PageFilterDTO conditions) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Author> criteriaQuery = builder.createQuery(Author.class);
        Root<Author> author = criteriaQuery.from(Author.class);
        Join<Author, Book> books = author.join("books", JoinType.LEFT);
        Join<Author, Convention> conventions = author.join("conventions", JoinType.LEFT);
        List<Predicate> predicates = new ArrayList<>();
        conditions.getFilters().forEach((field, value) -> {
            switch (field) {
                case AUTHOR_NAME:
                    predicates.add(builder.like(author.get(field.getKeyword()), "%" + value + "%"));
                    break;
                case BOOK_TITLE:
                    predicates.add(builder.like(books.get(field.getKeyword()), "%" + value + "%"));
                    break;
                case CONVENTION_LOCATION:
                    predicates.add(builder.like(conventions.get(field.getKeyword()), "%" + value + "%"));
                    break;
            }
        });
        var query = criteriaQuery.select(author).where(builder.and(predicates.toArray(new Predicate[0])));
        return entityManager.createQuery(query)
                .setFirstResult(conditions.getOffset())
                .setMaxResults(conditions.getLimit())
                .getResultList();
    }

    /**
     * método seguro que hace Fetch en la consulta para obtener las relaciones
     *
     * @param conditions los filtros a consultar
     * @return la lista de autores
     */
    @Transactional(readOnly = true)
    public List<Author> fetchAuthorsWithRelations(PageFilterDTO conditions) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Author> criteriaQuery = builder.createQuery(Author.class);
        Root<Author> author = criteriaQuery.from(Author.class);
        Join<Author, Book> books = author.join("books", JoinType.LEFT);
        author.fetch("books", JoinType.LEFT); //se hace fetch para jalar la relación en la query
        Join<Author, Convention> conventions = author.join("conventions", JoinType.LEFT);
        author.fetch("conventions", JoinType.LEFT);
        List<Predicate> predicates = new ArrayList<>();
        conditions.getFilters().forEach((field, value) -> {
            switch (field) {
                case AUTHOR_NAME:
                    predicates.add(builder.like(author.get(field.getKeyword()), "%" + value + "%"));
                    break;
                case BOOK_TITLE:
                    predicates.add(builder.like(books.get(field.getKeyword()), "%" + value + "%"));
                    break;
                case CONVENTION_LOCATION:
                    predicates.add(builder.like(conventions.get(field.getKeyword()), "%" + value + "%"));
                    break;
            }
        });
        var query = criteriaQuery.select(author).where(builder.and(predicates.toArray(new Predicate[0])));
        return entityManager.createQuery(query)
                .setFirstResult(conditions.getOffset())
                .setMaxResults(conditions.getLimit())
                .getResultList();
    }


}
