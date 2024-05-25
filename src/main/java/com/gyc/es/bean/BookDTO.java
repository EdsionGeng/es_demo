package com.gyc.es.bean;

import cn.hutool.core.annotation.Alias;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class BookDTO {
    @Alias("ID")
    private Long id;
    @Alias("标题")
    private String title;
    @Alias("标签")
    private String tag;
    @Alias("作者")
    private String author;
    @Alias("类别")
    private String category;
    @Alias("价格")
    private BigDecimal priceRef;
    @Alias("创建时间")
    private Date createTime;

}
