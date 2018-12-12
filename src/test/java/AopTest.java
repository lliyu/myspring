import advice.BeforeAdvice;
import aop.advisor.impl.RegexMatchAdvisor;
import aop.creator.impl.AopProxyCreator;
import aop.pointcut.impl.RegexExpressionPointCutResolver;
import bean.User;
import beandefinition.impl.DefaultBeanDefinition;
import factory.impl.DefaultBeanFactory;
import org.junit.Test;

/**
 * @Auther: Administrator
 * @Date: 2018-12-12 11:00
 * @Description:
 */
public class AopTest {
    static DefaultBeanFactory factory = new DefaultBeanFactory();

    @Test
    public void test() throws Exception {
        DefaultBeanDefinition bd = new DefaultBeanDefinition();
        bd.setClazz(User.class);
        bd.setSingleton(true);
        bd.setBeanFactoryName("TestFactory");
        bd.setCreateBeanMethodName("createMethod");
        bd.setStaticCreateBeanMethodName("staticCreateMethod");

        factory.register(bd, "user");
        bd = new DefaultBeanDefinition();
        bd.setClazz(BeforeAdvice.class);
        factory.register(bd, "myBeforeAdvice");

        AopProxyCreator aapc = new AopProxyCreator();
        aapc.setBeanFactory(factory);
        factory.registerBeanPostProcessor(aapc);
        // 向AdvisorAutoProxyCreator注册Advisor
        aapc.register(new RegexMatchAdvisor("myBeforeAdvice", "execution(* bean.User.*())", new RegexExpressionPointCutResolver()));

        User user = (User) factory.doGetBean("user");
        user.sayHello();
    }
}
