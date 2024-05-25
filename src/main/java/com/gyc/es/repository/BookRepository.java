package com.gyc.es.repository;

import com.alibaba.fastjson.JSON;
import com.gyc.es.bean.*;
import com.gyc.es.config.ESConfig;
import com.gyc.es.util.OkHttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

@Repository
@Slf4j
public class BookRepository extends BaseRepository<ESSearchResponse, SearchRequest> {

    @Autowired
    private ESConfig esConfig;
    private static String bookQueryBody = "\"query\": {\n" +
            "    \"multi_match\" : {\n" +
            "      \"query\":    \"%s\",\n" +
            "      \"fields\": [ \"author\", \"title\" ] \n" +
            "    }\n" +
            "  }";

    @Override
    public BookSearchResponse search(SearchRequest request) {
        BookSearchRequest bookSearchRequest;
        if (request instanceof BookSearchRequest) {

            bookSearchRequest = (BookSearchRequest) request;
            //TODO DSL 进行组装处理
            String bookQueryDsl = String.format(bookQueryBody, bookSearchRequest.getKeyword());

            try {
                //查询ES,解析返回数据 组装实体
                String result = OkHttpUtils.callWithAuth(esConfig.getBookESUrl(), bookQueryDsl,
                        esConfig.getUsername(), esConfig.getPassword());
                BookSearchResponse searchResponse = JSON.parseObject(result, BookSearchResponse.class);
                return searchResponse;
            } catch (Exception e) {
                log.error("book search make error ->{}", e);
            }
            return new BookSearchResponse();
        } else {
            throw new RuntimeException("Product search request convert make error,please check it!");
        }

    }

    /**
     * 添加数据
     *
     * @param insertBody
     * @return
     */
    public int addBooks(String insertBody) {
        if (StringUtils.isEmpty(insertBody)) {
            return 0;
        }
        String str = OkHttpUtils.callWithAuth(esConfig.getEsServerName() + "_bulk", insertBody, esConfig.getUsername(), esConfig.getPassword());
        return 1;
    }
}
