package com.lpjeremy.libmodule.gson;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @desc:Gson工具类
 * @date:2019/1/27 10:56
 * @auther:lp
 * @version:1.0
 */
public class GsonUtil {

    Gson gson;

    private GsonUtil() {

    }

    public static GsonUtil getInstance() {
        return GsonUtilHolder.sInstance;
    }

    private static class GsonUtilHolder {
        private static final GsonUtil sInstance = new GsonUtil();
    }

    /**
     * 创建gson对象
     *
     * @return
     */
    public Gson createGson() {
        if (gson == null) {
            gson = new GsonBuilder()
                    .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
                    .setDateFormat("yyyy-MM-dd HH:mm:ss")
                    .create();
        }
        return gson;
    }


    /**
     * 转换data 为指定类型
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T gsonToObject(JsonElement data, Class<T> clazz) {
        if (data == null) return null;
        if (gson == null) createGson();
        return gson.fromJson(data, clazz);
    }

    /**
     * 解析成数组
     *
     * @param json
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> List<T> jsonToList(JsonElement json, Class<T> clazz) {
        try {
            List<T> arrayList = new ArrayList<>();
            if (json == null) return arrayList;
            if (gson == null) createGson();
            JsonArray jsonArray = json.getAsJsonArray(); // 将JsonElement转换成JsonArray
            Iterator it = jsonArray.iterator(); // Iterator处理
            while (it.hasNext()) { // 循环
                JsonElement jsonElement = (JsonElement) it.next(); // 提取JsonElement
                String gsonStr = jsonElement.toString(); // JsonElement转换成String
                arrayList.add(gson.fromJson(gsonStr, clazz)); // 加入List
            }

            return arrayList;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 对象转成json
     * @param object
     * @return
     */
    public String objectToJson(Object object) {
        if (object == null) return "";
        if (gson == null) createGson();
        return gson.toJson(object);
    }
}
