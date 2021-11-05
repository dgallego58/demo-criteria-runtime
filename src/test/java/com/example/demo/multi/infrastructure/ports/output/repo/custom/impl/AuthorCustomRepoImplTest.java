package com.example.demo.multi.infrastructure.ports.output.repo.custom.impl;

import com.example.demo.multi.infrastructure.ports.input.dto.PageFilterDTO;
import com.example.demo.multi.infrastructure.ports.input.utils.Filter;
import com.example.demo.multi.infrastructure.ports.output.data.Author;
import com.example.demo.multi.infrastructure.ports.output.data.Book;
import com.example.demo.multi.infrastructure.ports.output.data.Convention;
import com.example.demo.multi.infrastructure.ports.output.repo.AuthorRepository;
import com.example.demo.multi.infrastructure.ports.output.repo.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
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
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(properties = {"spring.jpa.database-platform=org.hibernate.dialect.PostgreSQL94Dialect"})
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Slf4j
@ActiveProfiles("test")
@SuppressWarnings(value = {"rawtypes"})
class AuthorCustomRepoImplTest {

    @Container
    static PostgreSQLContainer database = new PostgreSQLContainer<>("postgres:13")
            .withDatabaseName("testing")
            .withPassword("testing")
            .withUsername("testing");
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

    @BeforeEach
    void setUp() {
        authorRepository.saveAndFlush(createAuthor());
    }


    @Test
    void testWithNPlus1() {
        log.info("Authors result with no fetch");
        var result = authorRepository.authorsNPlus1(stubFilter());
        assertThat(result).isNotEmpty()
                .first()
                .matches(author -> author.getConventions()
                        .stream()
                        .anyMatch(convention -> "Aracataca".equalsIgnoreCase(convention.getLocation())))
                .matches(author -> author.getBooks()
                        .stream()
                        .anyMatch(book -> book.getTitle().contains("Soledad")));
        //generating N +1 since the relation it is not Fetched at the time the query is executed
        var booksTitle = result.stream()
                .flatMap(author -> author.getBooks().stream())
                .map(Book::getTitle)
                .collect(Collectors.toSet());
        log.info("Books Title are {}", booksTitle);

        //same for the one to many
        var conventionLocations = result.stream()
                .flatMap(author -> author.getConventions().stream())
                .map(Convention::getLocation)
                .collect(Collectors.toSet());
        log.info("Conventions  are {}", conventionLocations);
    }


    @Test
    void testWithMemoryPagination() {
        log.info("Authors result fetch relations and pagination in memory");
        // HHH000104 warn:  fetch a collection of relationships in the cartesian product making pagination very big and unhandled by
        // the persistence provider
        var result = authorRepository.authorsInMemoryFilterPagination(stubFilter());
        result.forEach(author -> log.info("books {}", author.getBooks()));
        assertThat(result).isNotEmpty()
                .first()
                .matches(author -> author.getConventions()
                        .stream()
                        .anyMatch(convention -> "Aracataca".equalsIgnoreCase(convention.getLocation())))
                .matches(author -> author.getBooks()
                        .stream()
                        .anyMatch(book -> book.getTitle().contains("Soledad")));
        var booksTitle = result.stream()
                .flatMap(author -> author.getBooks().stream())
                .map(Book::getTitle)
                .collect(Collectors.toSet());
        log.info("Books Title are {}", booksTitle);
        var conventionLocations = result.stream()
                .flatMap(author -> author.getConventions().stream())
                .map(Convention::getLocation)
                .collect(Collectors.toSet());
        log.info("Conventions  are {}", conventionLocations);
    }

    @Test
    @Transactional
    void testWithPartitionSolvingMemoryPagination() {

        log.info("Querying partition");
        var result = authorRepository.authorsPartitionQuery(stubFilter());
        assertThat(result).isNotEmpty()
                .first()
                .matches(author -> author.getConventions()
                        .stream()
                        .anyMatch(convention -> "Aracataca".equalsIgnoreCase(convention.getLocation())))
                .matches(author -> author.getBooks()
                        .stream()
                        .anyMatch(book -> book.getTitle().contains("Soledad")));
        var booksTitle = result.stream()
                .flatMap(author -> author.getBooks().stream())
                .map(Book::getTitle)
                .collect(Collectors.toSet());
        log.info("Books Title are {}", booksTitle);
        var conventionLocations = result.stream()
                .flatMap(author -> author.getConventions().stream())
                .map(Convention::getLocation)
                .collect(Collectors.toSet());
        log.info("Conventions  are {}", conventionLocations);
    }

    private PageFilterDTO stubFilter() {
        PageFilterDTO pageFilterDTO = new PageFilterDTO()
                .setOffset(0)
                .setLimit(10);
        var filters = new EnumMap<>(Filter.class);
        filters.put(Filter.BOOK_TITLE, "Cien"); //comentar para ver el log
        filters.put(Filter.AUTHOR_NAME, "García");
        filters.put(Filter.CONVENTION_LOCATION, "Aracataca");
        pageFilterDTO.setFilters(filters);
        return pageFilterDTO;
    }

    private Author createAuthor() {
        Author author = new Author();
        author.setName("Gabriel García Marquez");
        author.addConvention(new Convention().setLocation("Aracataca"));
        author.addConvention(new Convention().setLocation("Universidad Nacional de Colombia"));
        author.addBook(new Book().setTitle("Cien Años de Soledad"));
        author.addBook(new Book().setTitle("Memorias de mis putas tristes"));
        author.addBook(new Book().setTitle("Relato de un náufrago"));
        return author;
    }
}
