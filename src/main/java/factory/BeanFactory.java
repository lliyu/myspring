package factory;

import beandefinition.AopPostProcessor;

/**
 * 接口描述:
 * 作者: ly
 * 创建时间:17:40 2018/12/1
 **/
public interface BeanFactory {

    Object getBean(String beanName) throws Exception;

    void registerBeanPostProcessor(AopPostProcessor processor);

}
