package advice;

import java.lang.reflect.Method;

/**
 * @Auther: Administrator
 * @Date: 2018-12-12 11:07
 * @Description:
 */
public class AfterAdvice implements aop.advice.AfterAdvice {
    @Override
    public void after(Method method, Object[] args, Object target, Object returnVal) {
        System.out.println("after...");
    }
}
