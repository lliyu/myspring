package context.reader.impl;

import bean.beandefinition.BeanDefinition;
import bean.beandefinition.BeanDefinitionRegistry;
import bean.beandefinition.impl.DefaultBeanDefinition;
import context.resource.Resource;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @Auther: Administrator
 * @Date: 2018-12-26 14:33
 * @Description:解析传入的文件流 生成BeanDefinition并注册
 */
public class XmlBeanDefinitionReader extends AbstractBeanDefinitionReader {

    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry) {
        super(registry);
    }

    @Override
    public void loadBeandefinition(Resource resource) {
        loadBeandefinition(resource);
    }

    @Override
    public void loadBeandefinition(Resource... resource) throws IOException, DocumentException, ClassNotFoundException {
        for(Resource res:resource){
            parse(res);
        }
    }

    private void parse(Resource res) throws IOException, DocumentException, ClassNotFoundException {
        InputStream inputStream = res.getInputStream();
        //获取document对象
        SAXReader reader = new SAXReader();
        Document document = reader.read(inputStream);
        Element rootElement = document.getRootElement();
        //解析
        List<Element> elements = rootElement.elements();
        for(Element element:elements){
            List<Attribute> attributes = element.attributes();
            BeanDefinition beanDefinition = new DefaultBeanDefinition();
            for(Attribute attribute:attributes){
                //class标签
                if("class".equals(attribute.getName())){
                    String data = (String) attribute.getData();
                    Class<?> aClass = Class.forName(data);
                    ((DefaultBeanDefinition) beanDefinition).setClazz(aClass);
                }else if("id".equals(attribute.getName())){
                    //class标签
                    String data = (String) attribute.getData();
                    ((DefaultBeanDefinition) beanDefinition).setBeanName(data);
                }
                //todo 其他标签
            }

            if(beanDefinition.getBeanName() != null){
                this.registry.register(beanDefinition, beanDefinition.getBeanName());
            }
        }
    }
}
