package utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Auther: Administrator
 * @Date: 2018-12-07 17:07
 * @Description:
 */
public class RegexMatchUtils {

    /**
     * 获取表达式的权限符
     * @param expression
     * @return
     * @throws Exception
     */
    public static String matchModifier(String expression) throws Exception {
        Pattern compile = Pattern.compile("(?<=\\().+?\\)");
        Matcher matcher = compile.matcher(expression);
        if(!matcher.find()){
            throw new Exception("表达式错误。");
        }
        String[] split = matcher.group().split(" ");
        if(split.length <= 1){
            throw new Exception("表达式错误。");
        }
        return split[0];
    }

    /**
     * 匹配类名
     * @param expression
     * @return
     * @throws Exception
     */
    public static String matchClassName(String expression) throws Exception {
        Pattern compile = Pattern.compile("(?<=\\().+?\\)");
        Matcher matcher = compile.matcher(expression);
        if(!matcher.find()){
            throw new Exception("表达式错误。");
        }
        String[] split = matcher.group().split(" ");
        if(split.length <= 1){
            throw new Exception("表达式错误。");
        }
        //aop.pointcut.impl.RegexExpressionPointCutResolver.*(..)
        String longName = split[1];
        compile = Pattern.compile("(.+?)(?=\\..+\\()");
        matcher = compile.matcher(longName);
        StringBuilder sb = new StringBuilder();
        while (matcher.find()){
            sb.append(matcher.group());
        }
        return sb.toString();
    }
}
