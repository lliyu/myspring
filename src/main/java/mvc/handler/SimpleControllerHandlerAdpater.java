package mvc.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author ly
 * @create 2019-01-12 15:57
 **/
public class SimpleControllerHandlerAdpater implements HandlerAdapter {
    @Override
    public Object handler(HttpServletRequest request, HttpServletResponse response) {
        return null;
    }

    @Override
    public boolean isMatch(HttpServletRequest request) {
        return false;
    }
}
