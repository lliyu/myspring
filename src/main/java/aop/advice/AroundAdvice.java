package aop.advice;

import java.lang.reflect.Method;

public interface AroundAdvice extends Advice {
    void around(Method method, Object[] args, Object target);
}
