package com.jack.demo.common;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jack
 * @version 1.0
 * @date 2022/2/27 21:18
 * 用于 规定 方法执行 是否成功
 */
@Data
public class R {
    private Boolean success; //是否成功

    private Integer code; //返回码

    private String message; //返回的信息

    private Map<String,Object> data=new HashMap<>();

    private R(){}

    //成功
    public static R ok(){
        R r=new R();
        r.setSuccess(true);
        r.setCode(ResultCode.SUCCESS);
        r.setMessage("成功");
        return r;
    }

    //输入信息有误：用于返回给前端 用户输入信息有误，或信息已被占用
    public static R dataError(){
        R r=new R();
        r.setSuccess(false);
        r.setCode(ResultCode.DATA_ERROR);
        r.setMessage("信息有误");
        return r;
    }

    //失败：该方法主要是返回给前端 后端服务上面的错误 让前端无法执行业务方法
    public static R error(){
        R r=new R();
        r.setSuccess(false);
        r.setCode(ResultCode.ERROR);
        r.setMessage("失败");
        return r;
    }

    //可以让方法设置 是否成功
    public R success(Boolean success){
        this.setSuccess(success);
        return this;
    }

    //可以让方法设置 信息
    public R message(String message){
        this.setMessage(message);
        return this;
    }

    //可以让方法设置 状态
    public R code(Integer code){
        this.setCode(code);
        return this;
    }

    //可以让方法设置 信息
    public R data(String key,Object value){
        this.data.put(key,value);
        return this;
    }

    //可以让方法设置 信息
    public R data(Map<String,Object> map){
        this.setData(map);
        return this;
    }
}
