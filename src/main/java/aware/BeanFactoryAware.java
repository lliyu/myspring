package aware;

import factory.BeanFactory;

public interface BeanFactoryAware extends Aware{

    void setBeanFactory(BeanFactory beanFactory);
}
