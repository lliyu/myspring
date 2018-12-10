package aop.pointcut.impl;

import aop.pointcut.RegexExpreseionPointCut;
import utils.RegexMatchUtils;

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
    public boolean matchsClass(Class<?> targetClass, String expression) throws Exception {
        //表达式中的类名
        String className = RegexMatchUtils.matchClassName(expression);

        //将.转换为普通的字符
        expression.replace(".", "\\.");
        expression.replace("*", ".*");
        //全路径
        String name = targetClass.getName();
        boolean matches = Pattern.matches(className, name);
        return matches;
    }

    @Override
    public boolean matchsMethod(Method method, Class<?> targetClass, String expresseion) {
        return false;
    }

    public static void main(String[] args) {
        //execution(* aop.pointcut.impl.RegexExpressionPointCutResolver.*(..))
        boolean matcher = Pattern.matches("aop\\.pointcut\\..*\\.Regex.*", "aop.pointcut.impl.RegexExpressionPointCutResolver");
        System.out.println(matcher);
        Method[] methods = RegexExpressionPointCutResolver.class.getDeclaredMethods();
        for(int i=0;i<methods.length;i++){
            System.out.println(methods[i].getName());
        }

    }
}
