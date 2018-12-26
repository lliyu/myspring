package aop.chain;

import aop.advice.Advice;
import aop.advice.AfterAdvice;
import aop.advice.AroundAdvice;
import aop.advice.BeforeAdvice;
import aop.advisor.Advisor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @Auther: Administrator
 * @Date: 2018-12-11 15:06
 * @Description: 责任链执行advice的增强
 */
public class AopAdviceChain {

    private Method nextMethod;
    private Method method;
    private Object target;
    private Object[] args;
    private Object proxy;
    private List<Advice> advices;

    //通知的索引 记录执行到第多少个advice
    private int index = 0;

    public AopAdviceChain(Method method, Object target, Object[] args, Object proxy, List<Advice> advices) {
        try {
            nextMethod = AopAdviceChain.class.getMethod("invoke", null);
        } catch (NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
        }

        this.method = method;
        this.target = target;
        this.args = args;
        this.proxy = proxy;
        this.advices = advices;
    }

    public Object invoke() throws InvocationTargetException, IllegalAccessException {
        if(index < this.advices.size()){
            Advice advice = this.advices.get(index++);
            if(advice instanceof BeforeAdvice){
                //前置增强
                ((BeforeAdvice) advice).before(method, args, target);
            }else if(advice instanceof AroundAdvice){
                //环绕增强
                return ((AroundAdvice) advice).around(nextMethod, args, this);
            } else if(advice instanceof AfterAdvice){
                //后置增强
                //如果是后置增强需要先取到返回值
                Object res = this.invoke();
                ((AfterAdvice) advice).after(method, args, target, res);
                //后置增强后返回  否则会多执行一次
                return res;
            }
            return this.invoke();
        }else {
            return method.invoke(target, args);
        }
    }


}
