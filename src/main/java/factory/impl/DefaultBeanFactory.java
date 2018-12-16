package factory.impl;

import beandefinition.BeanDefinition;
import beandefinition.BeanDefinitionRegistry;
import postprocessor.AopPostProcessor;
import beanreference.BeanReference;
import factory.BeanFactory;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;

import java.io.Closeable;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
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

    //记录正在创建的bean
    private ThreadLocal<Set<String>> initialedBeans = new ThreadLocal<>();

    //记录观察者
    private List<AopPostProcessor> aopPostProcessors = new ArrayList<>();


    @Override
    public void registerBeanPostProcessor(AopPostProcessor processor) {
        aopPostProcessors.add(processor);
    }

    @Override
    public void register(BeanDefinition bd, String beanName) {

        Assert.assertNotNull("beanName不能为空 beanName", beanName);
        Assert.assertNotNull("BeanDefinition不能为空", bd);

        if(bdMap.containsKey(beanName)){
            log.info("[" + beanName + "]已经存在");
        }

        if(!bd.validate()){
            log.error("BeanDefinition不合法");
            return;
        }

        bdMap.put(beanName, bd);
    }

    @Override
    public boolean containsBeanDefinition(String beanName) {
        return bdMap.containsKey(beanName);
    }

    @Override
    public BeanDefinition getBeanDefinition(String beanName) {
        if(!bdMap.containsKey(beanName)){
            log.info("[" + beanName + "]不存在");
            return null;
        }
        return bdMap.get(beanName);
    }

    public Object doGetBean(String beanName) throws Exception {
        if(!beanMap.containsKey(beanName)){
            log.info("[" + beanName + "]不存在");
        }

        // 记录正在创建的Bean
        Set<String> beans = this.initialedBeans.get();
        if (beans == null) {
            beans = new HashSet<>();
            this.initialedBeans.set(beans);
        }

        // 检测循环依赖
        if (beans.contains(beanName)) {
            throw new Exception("检测到" + beanName + "存在循环依赖：" + beans);
        }

        // 记录正在创建的Bean
        beans.add(beanName);

        Object instance = beanMap.get(beanName);

        if(instance != null){
            return instance;
        }

        //不存在则进行创建
        if(!this.bdMap.containsKey(beanName)){
            log.info("不存在名为：[" + beanName + "]的bean定义,即将进行创建");
        }

        BeanDefinition bd = this.bdMap.get(beanName);

        Class<?> beanClass = bd.getBeanClass();

        if(beanClass != null){
            instance = createBeanByConstruct(bd);
            if(instance == null){
                instance = createBeanByStaticFactoryMethod(bd);
            }
        }else if(instance == null && StringUtils.isNotBlank(bd.getStaticCreateBeanMethod())){
            instance = createBeanByFactoryMethod(bd);
        }

        this.doInit(bd, instance);

        //添加属性依赖
        this.parsePropertyValues(bd, instance);
        //创建完成 移除该bean的记录
        beans.remove(beanName);

        //添加aop处理
        instance = applyAopBeanPostProcessor(instance, beanName);

        if(instance != null && bd.isSingleton()){
            beanMap.put(beanName, instance);
        }

        return instance;
    }

    private Object applyAopBeanPostProcessor(Object instance, String beanName) throws Exception {
        for(AopPostProcessor postProcessor: aopPostProcessors){
            instance = postProcessor.postProcessWeaving(instance, beanName);
        }
        return instance;
    }

    /**
     * 解析传入的构造参数值
     * @param constructorArgs
     * @return
     */
    private Object[] parseConstructorArgs(List constructorArgs) throws Exception {

        if(constructorArgs==null || constructorArgs.size()==0){
            return null;
        }

        Object[] args = new Object[constructorArgs.size()];
        for(int i=0;i<constructorArgs.size();i++){
            Object arg = constructorArgs.get(i);
            Object value = null;
            if(arg instanceof BeanReference){
                String beanName = ((BeanReference) arg).getBeanName();
                value = this.doGetBean(beanName);
            }else if(arg instanceof List){
                value = parseListArg((List) arg);
            }else if(arg instanceof Map){
                //todo 处理map
            }else if(arg instanceof Properties){
                //todo 处理属性文件
            }else {
                value = arg;
            }
            args[i] = value;
        }
        return args;
    }

    private Constructor<?> matchConstructor(BeanDefinition bd, Object[] args) throws Exception {
        //先进行精确匹配 如果能匹配到相应的构造方法 则后续不用进行
        if(args == null){
            return bd.getBeanClass().getConstructor(null);
        }
        //如果已经缓存了 则直接返回
        if(bd.getConstructor() != null)
            return bd.getConstructor();

        int len = args.length;
        Class[] param = new Class[len];
        //构造参数列表
        for(int i=0;i<len;i++){
            param[i] = args[i].getClass();
        }
        //匹配
        Constructor constructor = null;
        try {
            constructor = bd.getBeanClass().getConstructor(param);
        } catch (Exception e) {
            //这里上面的代码如果没匹配到会抛出空指针异常
            //为了代码继续执行 这里我们来捕获 但是不需要做其他任何操作
        }
        if(constructor != null){
            return constructor;
        }

        //未匹配到 继续匹配
        List<Constructor> firstFilterAfter = new LinkedList<>();
        Constructor[] constructors = bd.getBeanClass().getConstructors();
        //按参数个数匹配
        for(Constructor cons:constructors){
            if(cons.getParameterCount() == len){
                firstFilterAfter.add(cons);
            }
        }

        if(firstFilterAfter.size()==1){
            return firstFilterAfter.get(0);
        }
        if(firstFilterAfter.size()==0){
            log.error("不存在对应的构造函数：" + args);
            throw new Exception("不存在对应的构造函数：" + args);
        }
        //按参数类型匹配
        //获取所有参数类型
        boolean isMatch = true;
        for(int i=0;i<firstFilterAfter.size();i++){
            Class[] types = firstFilterAfter.get(i).getParameterTypes();
            for(int j=0;j<types.length;j++){
                if(types[j].isAssignableFrom(args[j].getClass())){
                    isMatch = false;
                    break;
                }
            }
            if(isMatch){
                //对于原型bean 缓存方法
                if(bd.isPrototype()){
                    bd.setConstructor(firstFilterAfter.get(i));
                }
                return firstFilterAfter.get(i);
            }
        }
        //未能匹配到
        throw new Exception("不存在对应的构造函数：" + args);
    }

    private void parsePropertyValues(BeanDefinition bd, Object instance) throws Exception {
        Map<String, Object> propertyKeyValue = bd.getPropertyKeyValue();
        if(propertyKeyValue==null || propertyKeyValue.size()==0){
            return ;
        }
        Class<?> aClass = instance.getClass();
        Set<Map.Entry<String, Object>> entries = propertyKeyValue.entrySet();
        for(Map.Entry<String, Object> entry:entries){
            //获取指定的字段信息
            Field field = aClass.getDeclaredField(entry.getKey());
            //将访问权限设置为true
            field.setAccessible(true);
            Object arg = entry.getValue();
            Object value = null;
            if(arg instanceof BeanReference){
                String beanName = ((BeanReference) arg).getBeanName();
                value = this.doGetBean(beanName);
            }else if(arg instanceof List){
                List param = parseListArg((List) arg);
                value = param;
            }else if(arg instanceof Map){
                //todo 处理map
            }else if(arg instanceof Properties){
                //todo 处理属性文件
            }else {
                value = arg;
            }
            field.set(instance, value);
        }
    }

    private List parseListArg(List arg) throws Exception {
        //遍历list
        List param = new LinkedList();
        for(Object value:arg){
            Object res = new Object();
            if(arg instanceof BeanReference){
                String beanName = ((BeanReference) value).getBeanName();
                res = this.doGetBean(beanName);
            }else if(arg instanceof List){
                //递归 因为list中可能还存有list
                res = parseListArg(arg);
            }else if(arg instanceof Map){
                //todo 处理map
            }else if(arg instanceof Properties){
                //todo 处理属性文件
            }else {
                res = arg;
            }
            param.add(res);
        }
        return param;
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
     * @param bd
     * @return
     */
    private Object createBeanByConstruct(BeanDefinition bd) {
        Object instance = null;
        try {
            //解析构造参数
            List<?> constructorArg = bd.getConstructorArg();
            Object[] objects = parseConstructorArgs(constructorArg);
            //匹配构造参数
            Constructor<?> constructor = matchConstructor(bd, objects);
            if(constructor != null){
                instance = constructor.newInstance(objects);
            }else {
                instance = bd.getBeanClass().newInstance();
            }
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
    public Object getBean(String beanName) throws Exception {
        return doGetBean(beanName);
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
