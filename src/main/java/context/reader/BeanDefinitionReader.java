package context.reader;

import context.resource.Resource;
import org.dom4j.DocumentException;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

/**
 * 读取文件传入的字节流
 */
public interface BeanDefinitionReader {
    void loadBeandefinition(Resource resource);
    void loadBeandefinition(Resource... resource) throws IOException, ParserConfigurationException, SAXException, DocumentException, ClassNotFoundException;
}
