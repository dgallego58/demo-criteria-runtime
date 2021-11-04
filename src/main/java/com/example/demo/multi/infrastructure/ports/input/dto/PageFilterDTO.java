package com.example.demo.multi.infrastructure.ports.input.dto;

import com.example.demo.multi.infrastructure.ports.input.utils.Filter;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.EnumMap;

public class PageFilterDTO {

    private int limit;
    private int offset;
    @Schema(description = "the filters used", example = "{\n" +
            "  \"name\":" +
            "  \"Gabriel García Marquez\",\n" +
            "  \"title\": \"Cien Años de Soledad\",\n" +
            "  \"location\": \"UNAL\"\n" +
            "}")
    private EnumMap<Filter, Object> filters;

    public PageFilterDTO() {
        this.offset = 0;
        this.limit = 20;
        this.filters = new EnumMap<>(Filter.class);
    }

    public EnumMap<Filter, Object> getFilters() {
        return filters;
    }

    public PageFilterDTO setFilters(EnumMap<Filter, Object> filters) {
        this.filters = filters;
        return this;
    }

    public int getLimit() {
        return limit;
    }

    public PageFilterDTO setLimit(int limit) {
        this.limit = limit;
        return this;
    }

    public int getOffset() {
        return offset;
    }

    public PageFilterDTO setOffset(int offset) {
        this.offset = offset;
        return this;
    }
}
