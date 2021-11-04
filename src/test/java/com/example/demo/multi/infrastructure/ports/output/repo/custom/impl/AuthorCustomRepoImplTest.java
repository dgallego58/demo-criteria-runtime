package com.example.demo.multi.infrastructure.ports.output.repo.custom.impl;

import com.example.demo.multi.infrastructure.ports.input.dto.PageFilterDTO;
import com.example.demo.multi.infrastructure.ports.input.utils.Filter;
import com.example.demo.multi.infrastructure.ports.output.data.Author;
import com.example.demo.multi.infrastructure.ports.output.data.Book;
import com.example.demo.multi.infrastructure.ports.output.data.Convention;
import com.example.demo.multi.infrastructure.ports.output.repo.AuthorRepository;
import com.example.demo.multi.infrastructure.ports.output.repo.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.EnumMap;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(properties = {"spring.jpa.database-platform=org.hibernate.dialect.PostgreSQL94Dialect"})
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Slf4j
@ActiveProfiles("test")
class AuthorCustomRepoImplTest {

    @Container
    static PostgreSQLContainer database = new PostgreSQLContainer<>("postgres:13")
            .withDatabaseName("testing")
            .withPassword("testpsw")
            .withUsername("testusr");
    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    BookRepository bookRepository;

    @DynamicPropertySource
    static void setDataSourceProps(DynamicPropertyRegistry propertyRegistry) {
        propertyRegistry.add("spring.datasource.url", database::getJdbcUrl);
        propertyRegistry.add("spring.datasource.username", database::getUsername);
        propertyRegistry.add("spring.datasource.password", database::getPassword);
    }

    @Test
    @Transactional
    void testInsertion() {
        authorRepository.save(createAuthor());

        PageFilterDTO pageFilterDTO = new PageFilterDTO()
                .setOffset(0)
                .setLimit(1);
        var filters = new EnumMap<>(Filter.class);
        filters.put(Filter.BOOK_TITLE, "Cien"); //comentar para ver el log
        filters.put(Filter.AUTHOR_NAME, "Author1");
        filters.put(Filter.CONVENTION_LOCATION, "drid");
        pageFilterDTO.setFilters(filters);
        log.info("Authors result with no fetch");
        var result = authorRepository.authors(pageFilterDTO);
        //assertThat(result).isNotEmpty();
        result.forEach(author -> log.info("books {}", author.getBooks()));

        log.info("Authors result with fetch relations");
        var fetch = authorRepository.fetchAuthorsWithRelations(pageFilterDTO);
        fetch.forEach(author -> log.info("books {}", author.getBooks()));
        assertThat(fetch)
                .first()
                .matches(author -> author.getBooks()
                        .stream()
                        .anyMatch(book -> book.getTitle().contains("Soledad")));
    }

    private Author createAuthor() {
        Author author = new Author();
        author.setName("Tester Author1");
        author.addConvention(new Convention().setLocation("Madrid"));

        Book book = new Book().setTitle("Cien Años de Soledad");
        book.addAuthor(author);
        return author;
    }
}
