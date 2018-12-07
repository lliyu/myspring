import bean.User;
import beandefinition.impl.DefaultBeanDefinition;
import factory.impl.DefaultBeanFactory;
import org.junit.Test;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author ly
 * @create 2018-12-02 11:41
 **/
public class MySpringTest {

    static DefaultBeanFactory factory = new DefaultBeanFactory();

    @Test
    public void test() throws Exception {
        DefaultBeanDefinition bd = new DefaultBeanDefinition();
        bd.setClazz(User.class);
        bd.setSingleton(true);
        bd.setBeanFactoryName("TestFactory");
        bd.setCreateBeanMethodName("createMethod");
        bd.setStaticCreateBeanMethodName("staticCreateMethod");
        List<Object> args = new LinkedList<>();
        args.add("liyu");
        args.add(22);
        bd.setConstructorArg(args);
        bd.setBeanInitMethodName("init");
        Map<String, Object> values = new HashMap<>();
        values.put("name", "lingling");
        bd.setPropertyKeyValue(values);
        factory.register(bd, "user");

        System.out.println(factory.doGetBean("user"));
    }
}
