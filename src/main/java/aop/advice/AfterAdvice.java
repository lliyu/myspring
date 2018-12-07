package aop.advice;

import java.lang.reflect.Method;

public interface AfterAdvice extends Advice {
    void after(Method method, Object[] args, Object target, Object returnVal);
}
