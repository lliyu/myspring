package bean.aware;

import bean.factory.BeanFactory;

public interface BeanFactoryAware extends Aware{

    void setBeanFactory(BeanFactory beanFactory);
}
