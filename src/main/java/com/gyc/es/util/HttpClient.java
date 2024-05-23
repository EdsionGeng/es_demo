
package com.gyc.es.util;


import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public enum HttpClient {
    Instance;

    public static final int DEFAULT_TIMEOUT = 10;
    public static final int MAX_CONNECTION = 5;
    public static final int KEEP_ALIVE = 5;
    //logger
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpClient.class);

    /**
     * OkHttp
     */
    private OkHttpClient.Builder builder;
    private OkHttpClient okHttpClient;


    HttpClient() {
        this.builder = new OkHttpClient.Builder();

        this.builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        this.builder.readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        this.builder.writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        this.builder.connectionPool(new ConnectionPool(MAX_CONNECTION, KEEP_ALIVE, TimeUnit.MINUTES));


    }

    public HttpClient build() {
        return Instance;
    }

    public HttpClient connectionPool(int maxConn, long keepAlive, TimeUnit timeUnit) {
        this.builder.connectionPool(new ConnectionPool(maxConn, keepAlive, timeUnit));
        return this;
    }


    public HttpClient connectTimeout(int connectTimeout, TimeUnit timeUnit) {
        if (connectTimeout <= 0) {
            LOGGER.error(" ===> Connect timeout must not be less than 0.");
            return this;
        }
        this.builder.connectTimeout(connectTimeout, timeUnit);
        return this;
    }


    public HttpClient connectTimeout(int connectTimeout) {
        return connectTimeout(connectTimeout, TimeUnit.SECONDS);
    }


    public HttpClient readTimeout(int readTimeout, TimeUnit timeUnit) {
        if (readTimeout <= 0) {
            LOGGER.error(" ===> Read timeout must not be less than 0.");
            return this;
        }
        this.builder.readTimeout(readTimeout, timeUnit);
        return this;
    }


    public HttpClient readTimeout(int readTimeout) {
        return readTimeout(readTimeout, TimeUnit.SECONDS);
    }


    public HttpClient writeTimeout(int writeTimeout, TimeUnit timeUnit) {
        if (writeTimeout <= 0) {
            LOGGER.error(" ===> Write timeout must not be less than 0.");
            return this;
        }
        this.builder.writeTimeout(writeTimeout, timeUnit);
        return this;
    }

    public HttpClient writeTimeout(int writeTimeout) {
        return writeTimeout(writeTimeout, TimeUnit.SECONDS);
    }


    public OkHttpClient getOkHttpClient() {
        if (this.okHttpClient == null) {
            synchronized (Instance) {
                if (this.okHttpClient == null) {
                    this.okHttpClient = builder.build();
                }
            }
        }
        return this.okHttpClient;
    }

    public OkHttpClient.Builder getOkHttpClientBuilder() {
        this.getOkHttpClient();
        return this.builder;
    }

}
