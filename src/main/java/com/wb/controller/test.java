package com.wb.controller;

import com.wb.common.JsonData;
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
}
