package com.gyc.es.bean;

import com.alibaba.fastjson.JSON;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class BookSearchResponse extends ESSearchResponse {

    private List<ESBook> docs;


    public void convertBody() {
        List<ESBook> list = new ArrayList<>();
        for (int i = 0, length = this.getHits().getHits().size(); i < length; i++) {
            EsResponseEntity esResponseEntity = this.getHits().getHits().get(i);
            ESBook esBook = JSON.parseObject(JSON.toJSONString(esResponseEntity.get_source()), ESBook.class);
            list.add(esBook);
        }
        this.setDocs(list);
    }
}
