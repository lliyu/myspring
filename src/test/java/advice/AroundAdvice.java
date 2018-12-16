package advice;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @Auther: Administrator
 * @Date: 2018-12-12 11:07
 * @Description:
 */
public class AroundAdvice implements aop.advice.AroundAdvice {

    @Override
    public Object around(Method method, Object[] args, Object target) throws InvocationTargetException, IllegalAccessException {
        System.out.println("before...");
        Object invoke = method.invoke(target, args);
        System.out.println("after...");
        return invoke;
    }
}
