package context.app.impl;

import bean.beandefinition.BeanDefinitionRegistry;
import context.reader.impl.XmlBeanDefinitionReader;
import context.resource.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: Administrator
 * @Date: 2018-12-26 10:59
 * @Description: 解析xml配置文件
 */
public class XmlApplicationContext extends AbstractApplicationContext {

        private List<Resource> resources;

        public XmlApplicationContext(String loc) throws Exception {
                //初始化
                resources = new ArrayList<>();
                resources.add(getResource(loc));
                //生成reader对象
                XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader((BeanDefinitionRegistry) this.beanFactory);
                //这样写不能转换
//                xmlBeanDefinitionReader.loadBeandefinition((Resource[]) resources.toArray());
                //生成resource数组对象
                Resource[] resource = new Resource[resources.size()];
                int i=0;
                for(Resource res:resources){
                        resource[i++] = res;
                }
                xmlBeanDefinitionReader.loadBeandefinition(resource);
        }

}
