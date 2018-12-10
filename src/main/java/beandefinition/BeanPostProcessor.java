package beandefinition;

/**
 * 接口描述:
 * 作者: 李宇
 * 创建时间:19:33 2018/12/9
 **/
public interface BeanPostProcessor {

    Object postProcessWeaving(Object bean, String beanName);
}
