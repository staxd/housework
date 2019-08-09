package com.houseWork.security.config;

import com.alibaba.fastjson.JSON;
import com.houseWork.security.bean.AjaxResponseBody;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 未登录的提示
 * @params
 * @return
 * @date 2019/7/11 13:48
 * @author zjw
 */
@Component
public class AjaxAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        AjaxResponseBody responseBody = new AjaxResponseBody();

        responseBody.setStatus("000");
        responseBody.setMsg("Need Authorities!");

        httpServletResponse.getWriter().write(JSON.toJSONString(responseBody));
    }
}
