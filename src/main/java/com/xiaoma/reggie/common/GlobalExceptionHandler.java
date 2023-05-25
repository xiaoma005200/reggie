package com.xiaoma.reggie.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 全局异常处理
 */
@ControllerAdvice(annotations = {RestController.class, Controller.class})
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {
    //一个方法处理一个特定的异常(异常尽量精确)
    /**
     * 员工新增,账号重复异常,响应友好提示
     * @param ex
     * @return
     */
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public R<String> exceptionHandler(SQLIntegrityConstraintViolationException ex){
        ex.printStackTrace();
        log.info("错误信息为:{}", ex.getMessage());
        //匹配SQL错误中的实体重复错误    Duplicate entry "xiaoma" ....
        if(ex.getMessage().contains("Duplicate entry")){
            String[] split = ex.getMessage().split(" ");//空格分隔
            String msg = split[2]+"已存在";
            return R.error(msg);
        }
        //在前端或者客户端返回友好提示
        return R.error("未知错误,无法定位");
    }
}
