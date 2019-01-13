package mvc.controller;

import mvc.model.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author ly
 * @create 2019-01-12 15:36
 **/
public interface Controller {
    ModelAndView handlerRequest(HttpServletRequest request, HttpServletResponse response);
}
