package com.gyc.es.bean;

import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class Book implements Serializable {

    private Long id;

    private String title;

    private String tag;

    private String author;

    private BigDecimal price;

    private Date createTime;

}
