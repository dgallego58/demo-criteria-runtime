package com.example.demo.multi.infrastructure.ports.input.controller;

import com.example.demo.multi.infrastructure.ports.input.controller.swagger.ContractSwaggerDefinition;
import com.example.demo.multi.infrastructure.ports.input.dto.AuthorDTO;
import com.example.demo.multi.infrastructure.ports.input.dto.PageFilterDTO;
import com.example.demo.multi.usecase.EditorialUseCase;
import io.micrometer.core.annotation.Timed;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/editorial")
public class EditorialRestController implements ContractSwaggerDefinition {

    private final EditorialUseCase editorialUseCase;

    public EditorialRestController(EditorialUseCase editorialUseCase) {
        this.editorialUseCase = editorialUseCase;
    }

    public ResponseEntity<AuthorDTO> register(AuthorDTO authorDTO) {
        return ResponseEntity.ok(editorialUseCase.register(authorDTO));
    }

    @Timed(description = "Metricas de la lista de autores filtrados",
            extraTags = {"cardinalidad", "multiple",
                    "formato", "html",
                    "informacion", "persona",
                    "presentacion", "reporte"},
            value = "metric_authors")
    public ResponseEntity<List<AuthorDTO>> nPlus1Fetch(PageFilterDTO pageFilterDTO) {
        return ResponseEntity.ok(editorialUseCase.nPlus1Filter(pageFilterDTO));
    }

    @Override
    public ResponseEntity<List<AuthorDTO>> inMemoryPaginationFetch(PageFilterDTO pageFilterDTO) {

        return ResponseEntity.ok(editorialUseCase.inMemoryFilterPagination(pageFilterDTO));
    }

    @Override
    public ResponseEntity<List<AuthorDTO>> partitionFetch(PageFilterDTO pageFilterDTO) {
        return ResponseEntity.ok(editorialUseCase.partitionFilter(pageFilterDTO));
    }
}
