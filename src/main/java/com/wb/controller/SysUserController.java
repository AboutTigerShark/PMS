package com.wb.controller;

import com.wb.beans.PageQuery;
import com.wb.beans.PageResult;
import com.wb.common.JsonData;
import com.wb.model.SysUser;
import com.wb.param.UserParam;
import com.wb.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/sys/user")
@Slf4j
public class SysUserController {

    @Resource
    private SysUserService sysUserService;


    @RequestMapping("/save.json")
    @ResponseBody
    public JsonData save(UserParam userParam){
        sysUserService.save(userParam);
        return JsonData.success();
    }

    @RequestMapping("/update.json")
    @ResponseBody
    public JsonData update(UserParam userParam){
        sysUserService.update(userParam);
        return JsonData.success();
    }

    @RequestMapping("/list.json")
    @ResponseBody
    public JsonData list(int deptId, PageQuery pageQuery){
        PageResult<SysUser> result = sysUserService.getPageByDeptId(deptId, pageQuery);
        return JsonData.success(result);
    }

}

