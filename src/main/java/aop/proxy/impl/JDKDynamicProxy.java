package aop.proxy.impl;

import aop.AopUtils;
import aop.advisor.Advisor;
import aop.proxy.AopProxy;
import factory.BeanFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

/**
 * @Auther: Administrator
 * @Date: 2018-12-11 10:11
 * @Description: JDK动态代理实现
 */
public class JDKDynamicProxy implements InvocationHandler,AopProxy {

    private static final Log logger = LogFactory.getLog(JDKDynamicProxy.class);

    private Object target;
    private List<Advisor> advisors;
    private BeanFactory beanFactory;

    public JDKDynamicProxy(Object target, List<Advisor> advisors, BeanFactory beanFactory) {
        this.target = target;
        this.advisors = advisors;
        this.beanFactory = beanFactory;
    }

    @Override
    public Object getProxy() {
        return this.getProxy(this.getClass().getClassLoader());
    }

    @Override
    public Object getProxy(ClassLoader classLoader) {
        if (logger.isDebugEnabled()) {
            logger.debug("为" + target + "创建jdk代理");
        }
        return Proxy.newProxyInstance(classLoader, this.getClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Exception {
        //不确定有哪些方法需要增强
        //不确定有哪些advice会对该类增强
        return AopUtils.applyAdvice(target, getMatchAdvisors(target.getClass(), advisors), args, method, beanFactory);
    }
}
