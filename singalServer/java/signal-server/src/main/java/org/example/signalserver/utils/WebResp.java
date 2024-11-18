package org.example.signalserver.utils;
import lombok.Data;

import java.util.HashMap;


@Data
public class WebResp<T> {


    private int code;
    private String msg;
    private T data;

    public static final Integer CodeSuccess=200;
    public static final String MsgSuccess="success";

    public static final Integer CodeError=500;  //这个是报错
    public static final Integer CodeFailed=100;  //逻辑执行结果返回失败 但不是报错  101是报错

    public static WebResp<Void> Success() {
        return new WebResp<>(WebResp.CodeSuccess, MsgSuccess, null);
    }

    public static <T> WebResp<T> Success(T data, String msg){
        return new WebResp<>(WebResp.CodeSuccess,msg,data);
    }

    public static <T>WebResp<T> Success(Integer code,T data,String msg){
        return new WebResp<>(code,msg,data);
    }
    public static <T> WebResp<T> Success(T data){
        return new WebResp<>(WebResp.CodeSuccess,WebResp.MsgSuccess,data);
    }
    public static WebResp Failed(String msg){
        return new WebResp<>(WebResp.CodeFailed,msg, new HashMap<>());
    }
    public static <T> WebResp<T> Failed(T data,String msg){
        return new WebResp<>(WebResp.CodeFailed,msg, data);
    }

    public static <T> WebResp<T> error(String msg) {
        return new WebResp<>(WebResp.CodeError, msg, null);
    }

    public static <T> WebResp<T> error(int code, String msg) {
        return new WebResp<>(code, msg, null);
    }

    public WebResp(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public boolean success(){
        return code == CodeSuccess;
    }


}