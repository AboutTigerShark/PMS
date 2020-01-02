package com.wb.controller;


import com.wb.model.SysUser;
import com.wb.service.SysUserService;
import com.wb.util.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class UserController {

    @Resource
    private SysUserService sysUserService;

    //注销
    @RequestMapping("/logout.page")
    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.getSession().invalidate(); //用于清除session的所有信息
        response.sendRedirect("signin.jsp"); //跳转到signin.jsp
    }

    //登录
    @RequestMapping("/login.page")
    public void login(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        SysUser user = sysUserService.findByKeyWord(username); //数据库中查找此用户
        String errorMsg = ""; //错误提示
        String ret = request.getParameter("ret");

        if(StringUtils.isBlank(username)){
            errorMsg = "用户名不能为空";
        }else if (StringUtils.isBlank(password)){
            errorMsg = "密码不能为空";
        }else if (user == null){
            errorMsg = "查询不到指定用户";
        }else if (!user.getPassword().equals(MD5Util.encrypt(password))){
            errorMsg = "用户名或密码错误";
        }else if (user.getStatus() != 1){
            errorMsg = "用户被冻结,请联系管理员";
        }else { //登录成功
            request.getSession().setAttribute("user", user); //将用户放入会话存储
            if (StringUtils.isNotBlank(ret)) {
                response.sendRedirect(ret); //跳转回原来的网址
            } else {
                response.sendRedirect("/admin/index.page"); //跳转到首页
            }
        }

        request.setAttribute("error", errorMsg);
        request.setAttribute("username", username);
        if (StringUtils.isNotBlank(ret)) {
            request.setAttribute("ret", ret);
        }

        String path = "signin.jsp";
        request.getRequestDispatcher(path).forward(request, response); //转发到登录页
    }

}
