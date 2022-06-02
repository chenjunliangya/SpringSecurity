package com.mb.mbqx.config;

import com.google.gson.Gson;
import com.mb.mbqx.util.ErrorCode;
import com.mb.mbqx.util.Result;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @description: 自定义登录失败返回数据
 * @author chenjunliang
 * @date 2022/6/2 17:47
 */
@Component
public class MyAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        if("Bad credentials".equals(exception.getMessage())){
            response.getWriter().write(new Gson().toJson(Result.error(ErrorCode.ACCOUNT_PASSWORD_ERROR)));
        }else{
            response.getWriter().write(new Gson().toJson(Result.error(exception.getMessage())));
        }
    }
}
