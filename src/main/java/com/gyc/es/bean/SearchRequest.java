package com.gyc.es.bean;

import lombok.Data;

import java.util.List;

/**
 * 描述:
 * 搜索实体
 *
 * @author gengyc
 * @create 2020-11-02 14:19
 */
@Data
public class SearchRequest {

    /**
     * 排序方式
     */
    protected List<SortEntry> sorts;
    /**
     * 起始位置
     */
    protected int from;

    /**
     * 终点位置
     */
    protected int to;
    /**
     * 起始页
     */
    protected int page;
    /**
     * 分页数量
     */
    protected int size;
    /**
     * 未经加工查询
     */
    protected String rawQuery;


}