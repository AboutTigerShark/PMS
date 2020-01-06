package com.wb.controller;

import com.google.common.collect.Maps;
import com.wb.beans.PageQuery;
import com.wb.beans.PageResult;
import com.wb.common.JsonData;
import com.wb.model.SysUser;
import com.wb.param.UserParam;
import com.wb.service.SysRoleService;
import com.wb.service.SysTreeService;
import com.wb.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/sys/user")
@Slf4j
public class SysUserController {

    @Resource
    private SysUserService sysUserService;

    @Resource
    private SysTreeService sysTreeService;

    @Resource
    private SysRoleService sysRoleService;


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

    @RequestMapping("/acls.json")
    @ResponseBody
    public JsonData acls(@RequestParam("userId") int userId) {
        Map<String, Object> map = Maps.newHashMap();
        map.put("acls", sysTreeService.userAclTree(userId));
        map.put("roles", sysRoleService.getRoleListByUserId(userId));
        return JsonData.success(map);
    }

}

