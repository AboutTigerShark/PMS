package com.wb.common;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

//用于规范接口定义,
@Getter
@Setter
public class JsonData {
    private boolean ret; //返回结果(true or false)
    private String msg; //异常信息
    private Object data; //返回给前台的数据

    public JsonData(boolean ret){
        this.ret = ret;
    }

    public static JsonData success(Object object, String msg){
        JsonData jsonData = new JsonData(true);
        jsonData.data = object;
        jsonData.msg = msg;
        return jsonData;
    }

    public static JsonData success(Object object){
        JsonData jsonData = new JsonData(true);
        jsonData.data = object;
        return jsonData;
    }

    public static JsonData success(){
        return new JsonData(true);
    }

    public static JsonData fail(String msg){
        JsonData jsonData = new JsonData(false);
        jsonData.msg = msg;
        return jsonData;
    }

    public Map<String, Object> toMap(){
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("ret", ret);
        hashMap.put("msg", msg);
        hashMap.put("data", data);
        return hashMap;
    }
}
