package com.jack.demo.common;

/**
 * @author jack
 * @version 1.0
 * @date 2022/2/27 21:22
 * 返回的 信息码 包括 成功 失败 前端输入信息有误
 */
public interface ResultCode {
    public static Integer SUCCESS=20000;

    public static Integer ERROR=20001;

    public static Integer DATA_ERROR=20002;
}
