package mvc.handler;

import bean.aware.ApplicationContextAware;
import context.app.ApplicationContext;
import mvc.controller.RequestMappingInfo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author ly
 * @create 2019-01-12 15:55
 **/
public class RequestMappingHandlerMapping implements HandlerMapping,InitializingBean, ApplicationContextAware {

    private ApplicationContext applicationContext;

    private List<RequestMappingInfo> requestMappingInfos;

    private Map<String, List<RequestMappingInfo>> urlMaps;

    @Override
    public Object getHandler(HttpServletRequest request) {

        return null;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void afterPropertiesSet() {
        String[] beanNameForType = applicationContext.getBeanNameForType(Object.class);
        for(String beanName:beanNameForType){
            Class type = applicationContext.getType(beanName);
            //判断是否是控制器类型
            if (isHandler(type)) {
                //注册控制器的类型
                detectHandlerMethod(type);
            }

        }
    }

    private void detectHandlerMethod(Class type) {

    }

    public boolean isHandler(Class beanType){

        return false;
    }
}
