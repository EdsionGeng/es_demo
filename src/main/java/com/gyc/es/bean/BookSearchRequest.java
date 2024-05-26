package com.gyc.es.bean;

import lombok.Data;

@Data
public class BookSearchRequest extends SearchRequest {

    private String keyword;

    private float[] vector;

}
