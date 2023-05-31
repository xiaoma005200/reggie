package com.xiaoma.reggie.util;

/**
 * 基于ThreadLocal封装工具类，用户保存和获取当前登录用户id
 * 我们可以在LoginCheckFilter的doFilter方法中获取当前登录用户id，
 * 并调用ThreadLocal的set方法来设置当前线程的线程局部变量的值（用户id)，
 * 然后在MyMetaObjectHandler的updateFill方法中调用ThreadLocal的get方法来获得当前线程所对应的线程局部变量的值（用户id)。
 */
public class BaseContext {

    private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    public static void setCurrentId(Long id){
        threadLocal.set(id);
    }

    public static Long getCurrentId(){
        return threadLocal.get();
    }
}
