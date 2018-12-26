package aop.creator.impl;

import aop.advisor.Advisor;
import aop.creator.AopFactory;
import aop.proxy.AopProxy;
import aop.proxy.impl.CglibDynamicProxy;
import aop.proxy.impl.JDKDynamicProxy;
import bean.factory.BeanFactory;

import java.util.List;

/**
 * @Auther: Administrator
 * @Date: 2018-12-12 09:59
 * @Description:
 */
public class GenericAopFactory implements AopFactory {

    @Override
    public AopProxy createAopProxyInstance(Object target, List<Advisor> advisor, BeanFactory beanFactory, String beanName) {
        boolean res = judgeUseWhichProxyMode(target);
        if(res){
            //JDK
            return new JDKDynamicProxy(target, advisor, beanFactory);
        }else {
            //cglib
            return new CglibDynamicProxy(target, advisor, beanFactory, beanName);
        }
    }

}
