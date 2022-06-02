package com.mb.mbqx.config;

import com.google.gson.Gson;
import com.mb.mbqx.util.ErrorCode;
import com.mb.mbqx.util.Result;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @description: 未登录拦截返回自定义数据
 * @author chenjunliang
 * @date 2022/6/2 17:46
 */
@Component
public class MyAuthenticationExceptionHandler implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws
            IOException {
        Result error = null;
        if (e instanceof InsufficientAuthenticationException) {
            error = Result.error(ErrorCode.UNAUTHORIZED);
        }
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.getWriter().write(new Gson().toJson(error));
    }
}
