package advice;

import java.lang.reflect.Method;

/**
 * @Auther: Administrator
 * @Date: 2018-12-12 11:07
 * @Description:
 */
public class BeforeAdvice implements aop.advice.BeforeAdvice {
    @Override
    public void before(Method method, Object[] args, Object target) {
        System.out.println("before...");
    }
}
