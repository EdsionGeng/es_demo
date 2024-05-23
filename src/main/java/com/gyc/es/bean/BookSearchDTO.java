package com.gyc.es.bean;

import lombok.Data;

@Data
public class BookSearchDTO  extends SearchRequest {
    private String keyword;
}
