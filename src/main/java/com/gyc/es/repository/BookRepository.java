package com.gyc.es.repository;

import com.alibaba.fastjson.JSON;
import com.gyc.es.bean.*;
import com.gyc.es.config.ESConfig;
import com.gyc.es.util.OkHttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;

@Repository
@Slf4j
public class BookRepository extends BaseRepository<ESSearchResponse, SearchRequest> {

    @Autowired
    private ESConfig esConfig;
    private static String bookQueryBody = "{\n" +
            "    \"_source\": [\"title\",\"tag\",\"author\",\"price\",\"id\",\"category\"],\n" +
            "    \"query\": {\n" +
            "     \"bool\": {\n" +
            "     \"must\":[{\"multi_match\": {\"query\": \"%s\",\"fields\":[\"title^2\",  \"author^1\",\"tag^1\"] ,\"minimum_should_match\":\"%100%\",\"type\":\"cross_fields\"\n" +
            "  }\n" +
            " }]\n" +
            "     }\n" +
            "    },\n" +
            "     \"from\":0,\n" +
            "     \"size\":300,\n" +
            "    \"timeout\": \"5s\"\n" +
            " }";

    private static String bookQueryVecBody = " {\n" +
            "  \"_source\": [\"title\",\"tag\",\"author\",\"price\",\"id\",\"category\"],\n" +
            "  \"from\":0,\n" +
            "  \"size\":300,\n" +
            "  \"timeout\": \"5s\",\n" +
            "  \"query\": {\n" +
            "    \"script_score\": {\n" +
            "      \"query\": {\n" +
            "        \"match_all\": {}\n" +
            "      },\n" +
            "      \"script\": {\n" +
            "        \"source\": \"cosineSimilarity(params.query_vector, doc['title_vec']) + 1.0\", \n" +
            "        \"params\": {\n" +
            "          \"query_vector\": %s\n" +
            "        }\n" +
            "      }\n" +
            "  }\n" +
            "  }\n" +
            "}";

    @Override
    public BookSearchResponse search(SearchRequest request) {
        BookSearchRequest bookSearchRequest;
        if (request instanceof BookSearchRequest) {

            bookSearchRequest = (BookSearchRequest) request;
            String bookQueryDsl = String.format(bookQueryBody, bookSearchRequest.getKeyword());
            log.info("Machine address:{} ,Solution query dsl ->{} ", esConfig.getBookESUrl(), bookQueryDsl);
            try {
                //查询ES,解析返回数据 组装实体
                String result = OkHttpUtils.callWithAuth(esConfig.getBookESUrl(), bookQueryDsl,
                        esConfig.getUsername(), esConfig.getPassword());

                BookSearchResponse searchResponse = JSON.parseObject(result, BookSearchResponse.class);
                searchResponse.convertBody();
                return searchResponse;
            } catch (Exception e) {
                log.error("book search make error ->{}", e);
            }
            return new BookSearchResponse();
        } else {
            throw new RuntimeException("Product search request convert make error,please check it!");
        }

    }


    public BookSearchResponse vectorSearch(SearchRequest request) {
        BookSearchRequest bookSearchRequest;
        if (request instanceof BookSearchRequest) {

            bookSearchRequest = (BookSearchRequest) request;
            //TODO DSL 进行组装处理
            String bookQueryDsl = String.format(bookQueryVecBody, Arrays.toString(bookSearchRequest.getVector()));
            log.info("query Dsl->{}", bookQueryDsl);
            try {
                //查询ES,解析返回数据 组装实体
                String result = OkHttpUtils.callWithAuth(esConfig.getBookESUrl(), bookQueryDsl,
                        esConfig.getUsername(), esConfig.getPassword());
                BookSearchResponse searchResponse = JSON.parseObject(result, BookSearchResponse.class);
                searchResponse.convertBody();
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
        String str = OkHttpUtils.callWithAuth(esConfig.getBulkUrl(), insertBody, esConfig.getUsername(), esConfig.getPassword());
        return 1;
    }
}
