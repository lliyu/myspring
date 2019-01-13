package mvc;

import context.app.ApplicationContext;
import mvc.handler.HandlerAdapter;
import mvc.handler.HandlerMapping;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author ly
 * @create 2019-01-12 22:41
 **/
public class DispatcherServlet extends HttpServlet {

    private ApplicationContext webApplicationContext;

    private List<HandlerMapping> handlerMappings;

    private List<HandlerAdapter> handlerAdapters;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    private void initHandlerMapping(){

    }

    private void initHandlerAdapter(){

    }

    @Override
    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        this.doDispatcher(req, res);
    }

    private void doDispatcher(HttpServletRequest req, HttpServletResponse res){

    }

    private void noHandlerFound(HttpServletRequest req, HttpServletResponse response) throws IOException {
        // 如果没有特定的处理规则，就直接404了
        response.sendError(HttpServletResponse.SC_NOT_FOUND);

    }

    private Object getHandler(HttpServletRequest req) throws Exception {
        Object handler = null;
        for (HandlerMapping hm : this.handlerMappings) {
            handler = hm.getHandler(req);
            if (handler != null) {
                return handler;
            }
        }
        return null;
    }


    @Override
    public void destroy() {
        super.destroy();
    }
}
