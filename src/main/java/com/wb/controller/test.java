package com.wb.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/hello")
@Slf4j
public class test {

    @RequestMapping("/world")
    @ResponseBody
    public String a(){
        return "helloworld";
    }
}
