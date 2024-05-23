package com.gyc.es.bean;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 描述:
 * ES搜索返回结果
 *
 * @author gengyc
 * @create 2020-03-24 9:41
 */
@Data
public class ESSearchResponse {
    private Integer took;
    private Boolean timed_out;
    private Hits hits;
    @Data
    public static class Hits {
        protected List<EsResponseEntity> hits;
        protected Map<String, Object> total;
    }
    @Data
    public static class EsResponseEntity {
        private String _id;
        private Double _score;
        private Map<String, Object> _source;
    }

    @Data
    public static class BucketResult {
        private Integer key;
        private Integer doc_count;
    }


}