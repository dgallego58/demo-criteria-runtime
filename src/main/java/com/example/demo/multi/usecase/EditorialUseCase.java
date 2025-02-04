package com.example.demo.multi.usecase;

import com.example.demo.multi.infrastructure.ports.input.dto.AuthorDTO;
import com.example.demo.multi.infrastructure.ports.input.dto.PageFilterDTO;

import java.util.List;

public interface EditorialUseCase {

    AuthorDTO register(AuthorDTO dto);

    List<AuthorDTO> nPlus1Filter(PageFilterDTO pageFilterDTO);

    List<AuthorDTO> inMemoryFilterPagination(PageFilterDTO pageFilterDTO);

    List<AuthorDTO> partitionFilter(PageFilterDTO pageFilterDTO);
}
