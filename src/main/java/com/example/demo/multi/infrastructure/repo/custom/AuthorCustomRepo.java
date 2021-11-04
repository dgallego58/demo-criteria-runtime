package com.example.demo.multi.infrastructure.repo.custom;

import com.example.demo.multi.infrastructure.data.Author;
import com.example.demo.multi.infrastructure.data.utils.QueryRequest;

import java.util.List;

public interface AuthorCustomRepo {

    List<Author> authors(QueryRequest conditions);

    List<Author> fetchAuthorsWithRelations(QueryRequest conditions);
}
