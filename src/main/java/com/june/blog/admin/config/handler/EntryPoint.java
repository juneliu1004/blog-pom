package com.june.blog.admin.config.handler;

import com.alibaba.fastjson.JSON;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@Component
public class EntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {

        Map<String,String> map = new HashMap<>();
        map.put("msg","宁还没有登录");
        map.put("code", "403");

        httpServletResponse.setContentType("application/json;charset=utf-8");
        final PrintWriter writer = httpServletResponse.getWriter();
        writer.write(JSON.toJSONString(map));
        writer.flush();
        writer.close();


    }
}
