package aop;

import aop.advice.Advice;
import aop.advisor.Advisor;
import aop.advisor.PointCutAdvisor;
import aop.chain.AopAdviceChain;
import bean.factory.BeanFactory;
import org.apache.commons.collections.CollectionUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: Administrator
 * @Date: 2018-12-11 11:04
 * @Description:
 */
public class AopUtils {

    public static Object applyAdvice(Object target, Object proxy,List<Advisor> advisors, Object[] args, Method method, BeanFactory beanFactory) throws Exception {
        List<Advice> advices = getMatchMethodAdvice(method,target.getClass(), advisors, beanFactory);
        if(CollectionUtils.isEmpty(advices)){
            //如果没有匹配的增强器 就直接执行方法
            return method.invoke(target,args);
        }else {
            //存在着增强该方法的增强器
            return new AopAdviceChain(method, target, args, proxy, advices).invoke();
        }
    }

    /**
     * 获取与方法匹配的advice
     * @param method
     * @param aClass
     * @param advisors
     * @param beanFactory
     * @return 通知列表
     */
    public static List<Advice> getMatchMethodAdvice(Method method, Class<?> aClass, List<Advisor> advisors, BeanFactory beanFactory) throws Exception {
        if(CollectionUtils.isEmpty(advisors)){
            return null;
        }
        List<Advice> advices = new ArrayList<>();
        for(Advisor advisor:advisors){
            if(advisor instanceof PointCutAdvisor){
                PointCutAdvisor pointCutAdvisor = (PointCutAdvisor) advisor;
                boolean res = pointCutAdvisor.getPointCutResolver().matchsMethod(aClass, method, advisor.getExpression());
                if(res){
                       advices.add((Advice) beanFactory.getBean(advisor.getAdviceBeanName()));
                }
            }
        }
        return advices;
    }
}
