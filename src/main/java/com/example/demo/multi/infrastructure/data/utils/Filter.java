package com.example.demo.multi.infrastructure.data.utils;

public enum Filter {

    AUTHOR_NAME("name"),
    BOOK_TITLE("title"),
    CONVENTION_LOCATION("location"),
    ;

    private final String keyword;

    Filter(String keyword) {
        this.keyword = keyword;
    }

    public String getKeyword() {
        return keyword;
    }
}
