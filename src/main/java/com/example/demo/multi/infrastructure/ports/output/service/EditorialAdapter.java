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

    public EditorialAdapter(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    @Transactional
    public AuthorDTO register(AuthorDTO dto) {
        AuthorRequestMapper authorRequestMapper = new AuthorRequestMapper();
        Author entity = authorRequestMapper.entityFrom(dto);
        Author saved = authorRepository.save(entity);
        return authorRequestMapper.toDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AuthorDTO> getAllAuthorsByFilter(PageFilterDTO pageFilterDTO) {
        AuthorRequestMapper authorRequestMapper = new AuthorRequestMapper();
        List<Author> authors = authorRepository.fetchAuthorsWithRelations(pageFilterDTO);
        return authors.stream().map(authorRequestMapper::toDto).collect(Collectors.toList());
    }
}
