package context.app.impl;

import bean.factory.BeanFactory;
import bean.factory.impl.DefaultBeanFactory;
import bean.postprocessor.AopPostProcessor;
import context.app.ApplicationContext;
import context.resource.Resource;
import context.resource.ResourceFactory;
import context.resource.impl.ClasspathResource;
import context.resource.impl.FileSystemResource;
import context.resource.impl.URLResource;

import java.text.ParseException;

/**
 * @Auther: Administrator
 * @Date: 2018-12-26 10:54
 * @Description:
 */
public abstract class AbstractApplicationContext implements ApplicationContext,ResourceFactory {

    protected BeanFactory beanFactory;

    public AbstractApplicationContext() {
        this.beanFactory = new DefaultBeanFactory();
    }

    @Override
    public Object getBean(String beanName) throws Exception {
        return beanFactory.getBean(beanName);
    }

    @Override
    public void registerBeanPostProcessor(AopPostProcessor processor) {
        this.beanFactory.registerBeanPostProcessor(processor);
    }

    /**
     * 根据前缀返回不同的解析对象
     * @param localtions
     * @return
     * @throws Exception
     */
    @Override
    public Resource getResource(String localtions) throws Exception {
        if(localtions.contains(":")){
            String[] split = localtions.split(":");
            StringBuilder sb = new StringBuilder();
            sb.append(split[1]);
            for(int i=2;i<split.length;i++){
                sb.append(":" + split[i]);
            }
            if("url".equals(split[0])){
                return new URLResource(sb.toString());
            }else if("classpath".equals(split[0])){
                return new ClasspathResource(null, sb.toString(), null);
            }else if("file".equals(split[0])){
                return new FileSystemResource(sb.toString());
            }
        }else {
            throw new Exception("传入配置文件loc格式出错");
        }
        return null;
    }
}
