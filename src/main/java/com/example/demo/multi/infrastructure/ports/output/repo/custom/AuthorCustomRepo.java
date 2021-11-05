package com.example.demo.multi.infrastructure.ports.output.repo.custom;

import com.example.demo.multi.infrastructure.ports.input.dto.PageFilterDTO;
import com.example.demo.multi.infrastructure.ports.output.data.Author;

import java.util.List;

public interface AuthorCustomRepo {

    List<Author> authorsNPlus1(PageFilterDTO conditions);

    List<Author> authorsInMemoryFilterPagination(PageFilterDTO conditions);

    List<Author> authorsPartitionQuery(PageFilterDTO filterDTO);
}
