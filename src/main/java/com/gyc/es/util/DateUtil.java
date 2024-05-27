package com.gyc.es.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    public static String convert2Str(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }

    public static String convert2Str(String dateStr) {
        SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat targetFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            // 解析原始字符串到Date对象
            Date date = originalFormat.parse(dateStr);
            // 使用目标格式重新格式化Date对象
            String formattedString = targetFormat.format(date);
            return formattedString;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
