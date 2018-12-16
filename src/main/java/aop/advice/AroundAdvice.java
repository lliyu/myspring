package aop.advice;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public interface AroundAdvice extends Advice {
    Object around(Method method, Object[] args, Object target) throws InvocationTargetException, IllegalAccessException;
}
