package com.gyc.es.bean;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.Date;


@Data
public class ESBook {

    private Long id;
    private String title;
    @JSONField(name = "title_vec")
    private float[] titleVec;
    private String tag;
    private String author;
    private Integer price;
    private String category;
    @JSONField(name = "create_time")
    private String createTime;

}
