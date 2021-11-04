package com.example.demo.multi.infrastructure.ports.output.repo.custom;

import com.example.demo.multi.infrastructure.ports.input.dto.PageFilterDTO;
import com.example.demo.multi.infrastructure.ports.output.data.Author;

import java.util.List;

public interface AuthorCustomRepo {

    List<Author> authors(PageFilterDTO conditions);

    List<Author> fetchAuthorsWithRelations(PageFilterDTO conditions);
}
