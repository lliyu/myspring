package mvc.view;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author ly
 * @create 2019-01-12 15:50
 **/
public interface View {

    void render(HttpServletRequest req, HttpServletResponse response,Map<String,Object> model);
}
