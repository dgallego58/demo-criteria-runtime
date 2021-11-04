package com.example.demo.multi.infrastructure.ports.input.controller.swagger;

import com.example.demo.multi.infrastructure.ports.input.dto.AuthorDTO;
import com.example.demo.multi.infrastructure.ports.input.dto.PageFilterDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping(path = "/default-editorial-path")
public interface ContractSwaggerDefinition {

    @PostMapping(path = "/register-author")
    ResponseEntity<AuthorDTO> register(@RequestBody AuthorDTO authorDTO);


    @Operation(description = "Gets all the authors by the given filters")
    @PostMapping(path = "/get-authors-by-filter")
    ResponseEntity<List<AuthorDTO>> getAuthorsByFilter(@RequestBody @Schema(description = "the request object to filter") PageFilterDTO pageFilterDTO);
}
