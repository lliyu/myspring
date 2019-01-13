package bean.aware;

import context.app.ApplicationContext;

/**
 * @author ly
 * @create 2019-01-12 16:06
 **/
public interface ApplicationContextAware {
    void setApplicationContext(ApplicationContext applicationContext);
}
