import bean.User;
import beandefinition.BeanDefinition;
import beandefinition.impl.DefaultBeanDefinition;
import factory.impl.DefaultBeanFactory;
import org.junit.Test;

/**
 * @author ly
 * @create 2018-12-02 11:41
 **/
public class MySpringTest {

    static DefaultBeanFactory factory = new DefaultBeanFactory();

    @Test
    public void test() throws IllegalAccessException, InstantiationException {
        DefaultBeanDefinition bd = new DefaultBeanDefinition();
        bd.setClazz(User.class);
        bd.setSingleton(true);
        bd.setBeanFactoryName("TestFactory");
        bd.setCreateBeanMethodName("createMethod");
        bd.setStaticCreateBeanMethodName("staticCreateMethod");

        bd.setBeanInitMethodName("init");

        factory.register(bd, "user");

        System.out.println(factory.doGetBean("user"));
    }
}
