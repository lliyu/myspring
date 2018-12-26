package context.app;

import bean.factory.BeanFactory;

/**
 * 对外暴露的接口
 * 用户通过该接口将配置文件的位置传入
 */
public interface ApplicationContext extends BeanFactory {

}
