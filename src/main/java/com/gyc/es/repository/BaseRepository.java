package com.gyc.es.repository;

import com.gyc.es.bean.ESSearchResponse;
import com.gyc.es.bean.SearchRequest;


/**
 * 描述:
 * 搜索repository
 *
 * @author gengyc
 * @create 2020-11-03 10:45
 */
public abstract class BaseRepository<T extends ESSearchResponse, R extends SearchRequest> {


    /**
     * 基类搜索方法
     * @param request
     * @return
     */
    public abstract T search(R request);

}