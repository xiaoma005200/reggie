package com.xiaoma.reggie.common;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.xiaoma.reggie.util.BaseContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 自定义的元数据对象处理器
 * 作用：用户执行插入和新增操作时，自动填充表中的某些字段
 */
@Slf4j
@Component
public class MyMateObjectHandler implements MetaObjectHandler {
    /**
     * 新增数据时，自动填充
     * ThreadLocal
     * 客户端发送的每次http请求，对应的在服务器都会分配一个新的线程来处理，
     * LoginCheckFilter的doFilter方法,EmployeeCtroller的update方法，MyMetaObjectHandler的updateFill方法同属于一个线程
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("执行新增操作，公共数据自动填充...");
        metaObject.setValue("createTime", LocalDateTime.now());
        metaObject.setValue("updateTime", LocalDateTime.now());
        metaObject.setValue("createUser", BaseContext.getCurrentId());
        metaObject.setValue("updateUser", BaseContext.getCurrentId());
    }

    /**
     * 更新数据时，自动填充
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("执行更新操作，公共数据自动填充...");
        metaObject.setValue("updateUser", BaseContext.getCurrentId());
        metaObject.setValue("updateTime", LocalDateTime.now());
    }
}
