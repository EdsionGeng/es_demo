package com.gyc.es.bean;

import lombok.Data;

/**
 * @author gengyc
 * 排序实体
 */
@Data
public class SortEntry {
    public final String fieldName;
    public String sortType;

    public SortEntry(String fieldName, String sortType) {
        this.fieldName = fieldName;
        this.sortType = sortType;
    }

    @Override
    public String toString() {
        return "{" + fieldName + ":" + sortType + "}";
    }
}
