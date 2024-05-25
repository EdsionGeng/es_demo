package com.gyc.es.bean;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class ESBook {

    private Long id;
    private String title;
    private Double[] titleVec;
    private String tag;
    private String author;
    private Integer price;
    private String category;
    @JSONField(name = "create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

}
