package beandefinition;

import org.apache.commons.lang3.StringUtils;

/**
 * @author ly
 * @create 2018-11-28 22:41
 * bean定义接口 提供创建bean的信息
 **/
public interface BeanDefinition {

    String SINGLETION = "singleton";

    String PROTOTYPE = "prototype";

    /**
     * 获取bean的字节码对象
     * @return
     */
    Class<?> getBeanClass();

    /**
     * 获取创建bean的工厂名
     * @return
     */
    String getBeanFactory();

    /**
     * 获取创建bean的实例方法
     * @return
     */
    String getCreateBeanMethod();

    String getStaticCreateBeanMethod();

    String getBeanInitMethodName();

    String getBeanDestoryMethodName();

    /**
     * 获取bean的类型
     * @return
     */
    String getScope();

    boolean isSingleton();

    boolean isPrototype();

    /**
     * 校验传入的bean定义是否正确
     * @return
     */
    default boolean validate(){

        if(getBeanClass() == null){
            //字节码文件为空 并且创建bean的工厂为空 不合法
            if(StringUtils.isBlank(getBeanFactory()) && StringUtils.isBlank(getCreateBeanMethod())){
                return false;
            }
        }
        return true;
    }

}
