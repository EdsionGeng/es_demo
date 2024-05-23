package com.gyc.es.bean;

import lombok.Data;

import java.util.List;

@Data
public class BookSearchResponse extends ESSearchResponse {

    private List<ESBook> docs;
}
