package mvc.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author ly
 * @create 2019-01-12 15:55
 **/
public interface HandlerAdapter {
    Object handler(HttpServletRequest request, HttpServletResponse response);

    boolean isMatch(HttpServletRequest request);
}
