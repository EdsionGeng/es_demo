package com.gyc.es.config;

import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class ESConfig implements InitializingBean {
    @Value("${es.domain}")
    private String esServerName;
    @Value("${es.auth.username}")
    private String username;
    @Value("${es.auth.password}")
    private String password;
    @Value("${book.query.alias}")
    private String bookQueryAliasName;

    /**
     * 商品ES查询地址
     */
    private String bookESUrl;
    @Override
    public void afterPropertiesSet() throws Exception {
        this.bookESUrl = String.format("http://%s/%s/_doc/_search", this.esServerName, this.bookQueryAliasName);

    }
}
