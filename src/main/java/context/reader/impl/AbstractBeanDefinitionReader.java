package context.reader.impl;

import bean.beandefinition.BeanDefinitionRegistry;
import context.reader.BeanDefinitionReader;
import context.resource.Resource;

/**
 * @Auther: Administrator
 * @Date: 2018-12-26 14:28
 * @Description:
 */
public abstract class AbstractBeanDefinitionReader implements BeanDefinitionReader {

    protected BeanDefinitionRegistry registry;

    public AbstractBeanDefinitionReader(BeanDefinitionRegistry registry) {
        this.registry = registry;
    }
}
