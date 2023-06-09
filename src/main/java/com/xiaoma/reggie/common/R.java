package com.xiaoma.reggie.common;

import lombok.Data;
import java.util.HashMap;
import java.util.Map;

/**
 * 此类是一个通用结果类，服务端响应的所有结果最终都会包装成此种类型返回给前端页面
 *
 * @param <T>
 */
@Data
public class R<T> {

    private Integer code; //编码：1成功，0和其它数字为失败

    private String msg; //错误信息

    private T data; //数据

    private Map map = new HashMap(); //动态数据

    //<T>表明这个方法为泛型方法
    //R<T>表示返回的类型
    public static <T> R<T> success(T object) {
        R<T> r = new R<T>();
        r.data = object;
        r.code = 1;
        return r;
    }

    public static <T> R<T> error(String msg) {
        R r = new R();
        r.msg = msg;
        r.code = 0;
        return r;
    }

    public R<T> add(String key, Object value) {
        this.map.put(key, value);
        return this;
    }

}
