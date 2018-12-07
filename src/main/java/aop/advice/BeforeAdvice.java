package aop.advice;

import java.lang.reflect.Method;

public interface BeforeAdvice extends Advice {
    void before(Method method, Object[] args, Object target);
}
