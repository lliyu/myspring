package beandefinition;

/**
 * 接口描述:
 * 作者: ly
 * 创建时间:17:23 2018/12/1
 * 注册bean定义
 **/
public interface BeanDefinitionRegistry {

    /**
     * 注册bean定义到bean工厂
     * @param bd
     * @param beanName
     */
    void register(BeanDefinition bd, String beanName);

    boolean containsBeanDefinition(String beanName);

    BeanDefinition getBeanDefinition(String beanName);
}
