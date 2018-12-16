package aop.creator.impl;

import aop.advisor.Advisor;
import aop.advisor.AdvisorRegistry;
import aop.advisor.PointCutAdvisor;
import aop.creator.AopFactory;
import aware.BeanFactoryAware;
import postprocessor.AopPostProcessor;
import factory.BeanFactory;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: Administrator
 * @Date: 2018-12-12 10:10
 * @Description:
 */
public class AopProxyCreator implements AopPostProcessor, BeanFactoryAware,AdvisorRegistry {

    private BeanFactory beanFactory;

    private List<Advisor> advisors;

    public AopProxyCreator() {
        advisors = new ArrayList<>();
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    public Object postProcessWeaving(Object bean, String beanName) throws Exception {
        //获取和当前bean匹配的advisor
        List<Advisor> matchAdvisor = new ArrayList<>();
        matchAdvisor = getMatchAdvisor(bean);
        //对bean进行增强
        if(!CollectionUtils.isEmpty(matchAdvisor)){
            bean = AopFactory.createProxyInstance().createAopProxyInstance(bean, matchAdvisor, beanFactory, beanName).getProxy();
        }
        return bean;
    }

    private List<Advisor> getMatchAdvisor(Object bean) throws Exception {
        if(CollectionUtils.isEmpty(advisors) || bean ==null){
            return null;
        }
        List<Advisor> matchAdvisor = new ArrayList<>();
        Class<?> aClass = bean.getClass();
        for(Advisor advisor:advisors){
            if(advisor instanceof PointCutAdvisor){
                if(((PointCutAdvisor) advisor).getPointCutResolver().matchsClass(aClass, advisor.getExpression())){
                    matchAdvisor.add(advisor);
                }
            }
        }

        return matchAdvisor;
    }

    @Override
    public void register(Advisor advisor) {
        this.advisors.add(advisor);
    }

    @Override
    public List<Advisor> getAdvisor() {
        return this.advisors;
    }
}
