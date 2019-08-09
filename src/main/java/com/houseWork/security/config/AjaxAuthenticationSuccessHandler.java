package com.houseWork.security.config;

import com.alibaba.fastjson.JSON;
import com.houseWork.utils.JwtTokenUtil;
import com.houseWork.security.bean.AjaxResponseBody;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录成功的提示
 * @params
 * @return
 * @date 2019/7/11 13:49
 * @author zjw
 */
@Component
public class AjaxAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        AjaxResponseBody responseBody = new AjaxResponseBody();

        responseBody.setStatus("200");
        responseBody.setMsg("Login Success!");

        SelfUserDetails userDetails = (SelfUserDetails) authentication.getPrincipal();

        String jwtToken = JwtTokenUtil.generateToken(userDetails.getUsername(), "_secret");
        responseBody.setJwtToken(jwtToken);

        httpServletResponse.getWriter().write(JSON.toJSONString(responseBody));
    }
}

