package com.wb.common;

import com.wb.exception.ParamException;
import com.wb.exception.PermissionException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SpringExceptionResolver implements HandlerExceptionResolver {
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object o, Exception e) {

        String url = request.getRequestURL().toString();
        ModelAndView mv;
        String defaultMsg = "System error"; //默认错误信息
        if(url.endsWith(".json")){
            if(e instanceof PermissionException || e instanceof ParamException){
                JsonData result = JsonData.fail(e.getMessage());
                mv = new ModelAndView("jsonView", result.toMap());
            }else {
                log.error("unknown json exception,url"+ url, e);
                JsonData result = JsonData.fail(defaultMsg);
                mv = new ModelAndView("jsonView", result.toMap());
            }
        }else if(url.endsWith(".page")) {
            log.error("unknown page exception,url"+ url, e);
            JsonData result = JsonData.fail(defaultMsg);
            mv = new ModelAndView("exception", result.toMap());
        }else {
            log.error("unknown json exception,url"+ url, e);
            JsonData result = JsonData.fail(defaultMsg);
            mv = new ModelAndView("jsonView", result.toMap());
        }
        return mv;
    }
}
