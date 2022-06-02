package com.mb.mbqx.config;

import com.mb.mbqx.util.ErrorCode;
import com.mb.mbqx.util.Result;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @description: 统一异常捕捉--只做了部分拦截
 * @author chenjunliang
 * @date 2022/6/2 17:48
 */
@RestControllerAdvice
public class MyExceptionHandler {

    /**
     * 捕捉没有security无权限异常
     * @param ex
     * @return
     */
    @ExceptionHandler(AccessDeniedException.class)
    public Result handleAccessDeniedException(Exception ex){
        return Result.error(ErrorCode.FORBIDDEN);
    }

    @ExceptionHandler(RuntimeException.class)
    public Result RuntimeException(RuntimeException ex){
        return Result.error(ex.getMessage());
    }
}
