package mvc.controller;

import mvc.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * @author ly
 * @create 2019-01-12 16:09
 **/
public class RequestMappingInfo {
    private RequestMapping classRequestMapping;

    private String beanName;

    private RequestMapping methodRequestMapping;

    private Method method;

    private Object handler;

    public boolean isMatch(HttpServletRequest request){

        //todo
        return false;
    }
}
