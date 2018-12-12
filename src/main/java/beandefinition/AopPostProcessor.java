package beandefinition;

/**
 * 接口描述:
 * 作者: ly
 * 创建时间:19:33 2018/12/9
 **/
public interface AopPostProcessor {

    Object postProcessWeaving(Object bean, String beanName) throws Exception;
}
