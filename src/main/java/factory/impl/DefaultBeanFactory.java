package factory.impl;

import beandefinition.BeanDefinition;
import beandefinition.BeanDefinitionRegistry;
import factory.BeanFactory;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.Closeable;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * bean工厂的实现
 *
 * @author ly
 * @create 2018-12-01 17:46
 **/
//实现Closeable接口用于销毁对象
public class DefaultBeanFactory implements BeanFactory, BeanDefinitionRegistry, Closeable {

    private Log log = LogFactory.getLog(this.getClass());

    //ConcurrentHashMap应对并发环境
    private Map<String, BeanDefinition> bdMap = new ConcurrentHashMap<>();

    private Map<String, Object> beanMap = new ConcurrentHashMap<>();

    @Override
    public void register(BeanDefinition bd, String beanName) {

        if(StringUtils.isBlank(beanName)){
            log.error("beanName不能为空 beanName:" + beanName);
        }
        if(bd != null){
            log.error("BeanDefinition不能为空 bd:" + beanName);
        }

        if(bdMap.containsKey(beanName)){
            log.info("[" + beanName + "]已经存在");
        }

        if(!bd.validate()){
            log.info("BeanDefinition不合法");
        }

        if(!bdMap.containsKey(beanName)){
            bdMap.put(beanName, bd);
        }
    }

    @Override
    public boolean containsBeanDefinition(String beanName) {
        return bdMap.containsKey(beanName);
    }

    @Override
    public BeanDefinition getBeanDefinition(String beanName) {
        if(!bdMap.containsKey(beanName)){
            log.info("[" + beanName + "]不存在");
        }
        return bdMap.get(beanName);
    }

    public Object doGetBean(String beanName) throws InstantiationException, IllegalAccessException {
        if(!beanMap.containsKey(beanName)){
            log.info("[" + beanName + "]不存在");
        }

        Object instance = beanMap.get(beanName);

        if(instance != null){
            return instance;
        }

        //不存在则进行创建
        if(!this.bdMap.containsKey(beanName)){
            log.info("不存在名为：[" + beanName + "]的bean定义");
        }

        BeanDefinition bd = this.bdMap.get(beanName);

        Class<?> beanClass = bd.getBeanClass();

        if(beanClass != null){
            instance = createBeanByConstruct(beanClass);
            if(instance == null){
                instance = createBeanByStaticFactoryMethod(bd);
            }
        }else if(instance == null && StringUtils.isNotBlank(bd.getStaticCreateBeanMethod())){
            instance = createBeanByFactoryMethod(bd);
        }

        this.doInit(bd, instance);

        if(instance != null && bd.isSingleton()){
            beanMap.put(beanName, instance);
        }

        return instance;
    }

    private void doInit(BeanDefinition bd, Object instance) {
        Class<?> beanClass = instance.getClass();
        if(StringUtils.isNotBlank(bd.getBeanInitMethodName())){
            try {
                Method method = beanClass.getMethod(bd.getBeanInitMethodName(), null);
                method.invoke(instance, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 构造方法创建实例
     * @param beanClass
     * @return
     */
    private Object createBeanByConstruct(Class<?> beanClass) {
        Object instance = null;
        try {
            instance = beanClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return instance;
    }

    /**
     * 普通工厂方法创建实例
     * @param bd
     * @return
     */
    private Object createBeanByFactoryMethod(BeanDefinition bd) {
        Object instance = null;
        try {
            //获取工厂类
            Object factory = doGetBean(bd.getBeanFactory());
            //获取创建实例的方法
            Method method = factory.getClass().getMethod(bd.getCreateBeanMethod());
            //执行方法
            instance = method.invoke(factory, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return instance;
    }

    /**
     * 静态方法创建实例
     * @param bd
     * @return
     */
    private Object createBeanByStaticFactoryMethod(BeanDefinition bd) {
        Object instance = null;
        try {
            Class<?> beanClass = bd.getBeanClass();
            //获取创建实例的方法
            Method method = beanClass.getMethod(bd.getStaticCreateBeanMethod());
            instance = method.invoke(beanClass, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return instance;
    }

    @Override
    public Object getBean(String beanName) {
        if(!beanMap.containsKey(beanName)){
            log.info("[" + beanName + "]不存在");
        }
        return beanMap.get(beanName);
    }

    @Override
    public void close() throws IOException {
        Set<Map.Entry<String, BeanDefinition>> entries = bdMap.entrySet();
        for(Map.Entry<String, BeanDefinition>  entry: entries){
            BeanDefinition value = entry.getValue();
            String destoryMethodName = value.getBeanDestoryMethodName();
            try {
                Method method = value.getBeanClass().getMethod(destoryMethodName, null);
                method.invoke(value.getBeanClass(), null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
