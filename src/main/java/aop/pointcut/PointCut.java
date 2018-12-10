package aop.pointcut;

import java.lang.reflect.Method;

public interface PointCut {
    boolean matchsClass(Class<?> targetClass, String expresseion) throws Exception;
    boolean matchsMethod(Method method, Class<?> targetClass,  String expresseion);
}
