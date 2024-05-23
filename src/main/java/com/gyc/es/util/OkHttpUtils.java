package com.gyc.es.util;



import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.util.StringUtils;
import java.io.IOException;
import java.util.Base64;


/**
 * @author internet
 */
@Slf4j
public abstract class OkHttpUtils {


    public static String call(String url, String json, String method) {

        RequestBody
                body;
        if (!StringUtils.isEmpty(json)) {
            body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        } else {
            body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), "{}");
        }

        Request request = null;
        if ("POST".equalsIgnoreCase(method)) {
            request = new Request.Builder()
                    .post(body)
                    .url(url)
                    .build();
        } else if ("PUT".equalsIgnoreCase(method)) {
            request = new Request.Builder()
                    .put(body)
                    .url(url)
                    .build();
        } else if ("DELETE".equalsIgnoreCase(method)) {
            request = new Request.Builder()
                    .delete()
                    .url(url)
                    .build();
        } else if ("GET".equalsIgnoreCase(method)) {
            request = new Request.Builder()
                    .get()
                    .url(url)
                    .build();
        } else {
            log.error("请求异常->{}{}{}", url, json, method);
        }

        Call call = HttpClient.Instance.getOkHttpClient().newCall(request);
        try {
            Response response = call.execute();
            return response.body().string();
        } catch (IOException e) {
            log.error("OkHttp  execute make error ->{}", e);
        }
        return null;
    }

    public static String callWithAuth(String url, String json, String authUsername, String authPassword) {
        RequestBody body;
        if (!StringUtils.isEmpty(json)) {
            body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        } else {
            body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), "{}");
        }
        Request request = new Request.Builder()
                .post(body)
                .header("Authorization", getEsAuth(authUsername, authPassword))
                .url(url)
                .build();
        Call call = HttpClient.Instance.getOkHttpClient().newCall(request);
        try {
            Response response = call.execute();
            return response.body().string();
        } catch (IOException e) {
            log.error("OkHttp execute make error ->{}", e);
        }
        return null;
    }
    public static String getEsAuth(String username, String password) {
        StringBuilder builder = new StringBuilder();
        builder.append(username)
                .append(":")
                .append(password);
        return "Basic  " + Base64.getEncoder().encodeToString(builder.toString().getBytes());
    }


}

