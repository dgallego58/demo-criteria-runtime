package com.example.demo.multi.infrastructure.data.utils;

import java.util.EnumMap;

public class QueryRequest {

    private int limit;
    private int offset;
    private EnumMap<Filter, Object> filters;

    public QueryRequest() {
        this.offset = 0;
        this.limit = Integer.MAX_VALUE;
        this.filters = new EnumMap<>(Filter.class);
    }

    public EnumMap<Filter, Object> getFilters() {
        return filters;
    }

    public QueryRequest setFilters(EnumMap<Filter, Object> filters) {
        this.filters = filters;
        return this;
    }

    public int getLimit() {
        return limit;
    }

    public QueryRequest setLimit(int limit) {
        this.limit = limit;
        return this;
    }

    public int getOffset() {
        return offset;
    }

    public QueryRequest setOffset(int offset) {
        this.offset = offset;
        return this;
    }
}
