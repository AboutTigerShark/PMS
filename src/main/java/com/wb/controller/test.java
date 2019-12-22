package com.wb.controller;

import com.wb.common.ApplicationContextHelper;
import com.wb.common.JsonData;
import com.wb.dao.SysAclModuleMapper;
import com.wb.exception.ParamException;
import com.wb.model.SysAclModule;
import com.wb.param.TestVo;
import com.wb.util.BeanValidator;
import com.wb.util.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/test")
@Slf4j
public class test {

    @RequestMapping("/hello.json")
    @ResponseBody
    public JsonData a(){
        log.info("hello");
        return JsonData.success("helloworld");
    }

    @RequestMapping("/validate.json")
    @ResponseBody
    public JsonData validate(TestVo vo) throws ParamException {
        log.info("validate");
        SysAclModuleMapper moduleMapper = ApplicationContextHelper.popBean(SysAclModuleMapper.class);
        SysAclModule module = moduleMapper.selectByPrimaryKey(1);
        log.info(JsonMapper.obj2String(module));
        BeanValidator.check(vo);
        return JsonData.success("test validate");
    }
}
