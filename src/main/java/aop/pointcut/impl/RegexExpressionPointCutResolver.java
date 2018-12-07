package aop.pointcut.impl;

import aop.pointcut.RegexExpreseionPointCut;

import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Auther: Administrator
 * @Date: 2018-12-07 15:09
 * @Description:
 */
public class RegexExpressionPointCutResolver implements RegexExpreseionPointCut {

    @Override
    public boolean matchsClass(Class<?> targetClass, String expresseion) {
        //将.转换为普通的字符
        expresseion.replace(".", "\\.");
        expresseion.replace("*", ".*");
        //全路径
        String name = targetClass.getName();
        boolean matches = Pattern.matches(expresseion, name);
        return matches;
    }

    @Override
    public boolean matchsMethod(Method method, Class<?> targetClass, String expresseion) {
        return false;
    }

    public static void main(String[] args) {

        boolean matcher = Pattern.matches("aop\\.pointcut\\..*\\.Regex.*", "aop.pointcut.impl.RegexExpressionPointCutResolver");
        System.out.println(matcher);
        Method[] methods = RegexExpressionPointCutResolver.class.getMethods();

        System.out.println(methods[1].getName());

    }
}
