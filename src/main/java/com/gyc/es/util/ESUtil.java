package com.gyc.es.util;

import com.alibaba.fastjson.JSON;

public class ESUtil {

    public static String dealProductIndexBuildData(String indexName, Long docId, Object object) {
        String jsonString = "";
        try {
            StringBuilder bodyBuilder = new StringBuilder();
            bodyBuilder.append("{\"index\":{\"_index\":\"")
                    .append(indexName).append("\",").append("\"_id\":\"")
                    .append(docId).append("\"}}\n");
            bodyBuilder.append(JSON.toJSONString(object))
                    .append("\n");
            jsonString = bodyBuilder.toString();

        } catch (Exception e) {

        }
        return jsonString;
    }

    public String dealProductIndexBuildDataWithStr(String indexName, String docId, Object object) {
        String jsonString = "";
        try {
            StringBuilder bodyBuilder = new StringBuilder();
            bodyBuilder.append("{\"index\":{\"_index\":\"")
                    .append(indexName).append("\",").append("\"_id\":\"")
                    .append(docId).append("\"}}\n");
            bodyBuilder.append(JSON.toJSONString(object))
                    .append("\n");
            jsonString = bodyBuilder.toString();

        } catch (Exception e) {

        }
        return jsonString;
    }

    public void dealProductIndexBuild(String url, String username, String password, String dataJson) {
        String str = OkHttpUtils.callWithAuth(url + "_bulk", dataJson,  username, password);

    }

}
