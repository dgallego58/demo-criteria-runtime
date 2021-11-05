package com.example.demo.multi.infrastructure.ports.output.service;

import com.example.demo.multi.infrastructure.helper.AuthorRequestMapper;
import com.example.demo.multi.infrastructure.ports.input.dto.AuthorDTO;
import com.example.demo.multi.infrastructure.ports.input.dto.PageFilterDTO;
import com.example.demo.multi.infrastructure.ports.output.data.Author;
import com.example.demo.multi.infrastructure.ports.output.repo.AuthorRepository;
import com.example.demo.multi.usecase.EditorialUseCase;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EditorialAdapter implements EditorialUseCase {

    private final AuthorRepository authorRepository;
    private final AuthorRequestMapper authorRequestMapper;

    public EditorialAdapter(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
        this.authorRequestMapper = new AuthorRequestMapper();
    }

    @Override
    @Transactional
    public AuthorDTO register(AuthorDTO dto) {
        Author entity = authorRequestMapper.entityFrom(dto);
        Author saved = authorRepository.save(entity);
        return authorRequestMapper.toDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AuthorDTO> nPlus1Filter(PageFilterDTO pageFilterDTO) {
        List<Author> authors = authorRepository.authorsNPlus1(pageFilterDTO);
        return authors.stream().map(authorRequestMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<AuthorDTO> inMemoryFilterPagination(PageFilterDTO pageFilterDTO) {
        var result = authorRepository.authorsInMemoryFilterPagination(pageFilterDTO);
        return result.stream().map(authorRequestMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<AuthorDTO> partitionFilter(PageFilterDTO pageFilterDTO) {
        var result = authorRepository.authorsPartitionQuery(pageFilterDTO);
        return result.stream().map(authorRequestMapper::toDto).collect(Collectors.toList());
    }
}
