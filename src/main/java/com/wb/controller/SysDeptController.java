package com.wb.controller;

import com.wb.common.JsonData;
import com.wb.dto.DeptLevelDto;
import com.wb.param.DeptParam;
import com.wb.service.SysDeptService;
import com.wb.service.SysTreeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/sys/dept")
@Slf4j
public class SysDeptController {

    @Resource
    private SysDeptService sysDeptService;

    @Resource
    private SysTreeService sysTreeService;

    @RequestMapping("/save.json")
    @ResponseBody
    public JsonData saveDept(DeptParam deptParam){
        sysDeptService.save(deptParam);
        return JsonData.success();
    }

    @RequestMapping("/tree.json")
    @ResponseBody
    public JsonData tree(){
        List<DeptLevelDto> deptLevelDtoList = sysTreeService.deptTree();
        return JsonData.success(deptLevelDtoList);
    }

    @RequestMapping("/update.json")
    @ResponseBody
    public JsonData update(DeptParam deptParam){
        sysDeptService.update(deptParam);
        return JsonData.success();
    }

}
