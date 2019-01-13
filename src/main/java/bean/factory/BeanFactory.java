package bean.factory;

import bean.postprocessor.AopPostProcessor;

import java.util.Map;

/**
 * 接口描述:
 * 作者: ly
 * 创建时间:17:40 2018/12/1
 **/
public interface BeanFactory {

    Object getBean(String beanName) throws Exception;

    void registerBeanPostProcessor(AopPostProcessor processor);

    String[] getBeanNameForType(Class<?> tClass);

    Map<String, Object> getBeansForType(Class<?> clazz);

    Class getType(String beanName);
}
