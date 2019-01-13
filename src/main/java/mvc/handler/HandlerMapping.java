package mvc.handler;

import javax.servlet.http.HttpServletRequest;

/**
 * @author ly
 * @create 2019-01-12 15:54
 **/
public interface HandlerMapping {
    Object getHandler(HttpServletRequest request);
}
