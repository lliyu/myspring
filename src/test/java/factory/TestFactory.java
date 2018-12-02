package factory;

import bean.User;

/**
 * @author ly
 * @create 2018-12-02 13:46
 **/
public class TestFactory {
    public Object createMethod(){
        return new User();
    }

    public static Object staticCreateMethod(){
        return new User();
    }
}
