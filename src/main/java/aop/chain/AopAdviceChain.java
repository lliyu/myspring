package aop.chain;

import aop.advice.Advice;
import aop.advice.AfterAdvice;
import aop.advice.AroundAdvice;
import aop.advice.BeforeAdvice;

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
                ((AroundAdvice) advice).around(nextMethod, null, this);
            } else if(advice instanceof AfterAdvice){
                //后置增强
                //如果是后置增强需要先取到返回值
                Object res = this.invoke();
                ((AfterAdvice) advice).after(method, args, target, res);
            }
            return this.invoke();
        }else {
            return method.invoke(target, args);
        }
    }


}
