package aop.creator;

import aop.advisor.Advisor;
import aop.creator.impl.GenericAopFactory;
import aop.proxy.AopProxy;
import bean.factory.BeanFactory;

import java.util.List;

/**
 * @Auther: Administrator
 * @Date: 2018-12-12 09:52
 * @Description: AOP工厂
 */
public interface AopFactory {

    AopProxy createAopProxyInstance(Object target, List<Advisor> advisor, BeanFactory beanFactory, String beanName);

    /**
     * 用于判断使用哪种代理方式来完成增强功能
     * 简单判断：类实现了接口就用JDK代理 没实现接口就用cglib代理
     * @param target
     * @return
     */
    default boolean judgeUseWhichProxyMode(Object target){
        Class<?>[] interfaces = target.getClass().getInterfaces();
        return interfaces.length>0;
    }

    //不通过创建对象 直接调用
    static AopFactory createProxyInstance(){
        return new GenericAopFactory();
    }
}
