
import bean.User;
import context.app.ApplicationContext;
import context.app.impl.XmlApplicationContext;
import org.junit.Test;

/**
 * @Auther: Administrator
 * @Date: 2018-12-26 15:10
 * @Description:
 */
public class ContextTest {
    @Test
    public void test() throws Exception {
        ApplicationContext context = new XmlApplicationContext("file:F:\\dn\\mysrping\\src\\main\\java\\context\\xsd\\spring.xml");
        User user = (User) context.getBean("user");
        System.out.println(user.getAge());
    }
}
