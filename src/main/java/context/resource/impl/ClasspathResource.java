package context.resource.impl;

import context.resource.Resource;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * @Auther: Administrator
 * @Date: 2018-12-26 11:18
 * @Description:
 */
public class ClasspathResource implements Resource {

    private ClassLoader classLoader;

    private String path;

    private Class clazz;

    public ClasspathResource(ClassLoader classLoader, String path, Class clazz) {
        this.classLoader = classLoader;
        this.path = path;
        this.clazz = clazz;
    }

    @Override
    public boolean isExist() {
        if(StringUtils.isNotBlank(path)){
            if(clazz != null){
                return clazz.getResource(path) != null;
            }else if(classLoader != null){
                return classLoader.getResource(path) != null;
            }
            return this.getClass().getResource(path) != null;
        }
        return false;
    }

    @Override
    public File getFile() {
        return null;
    }

    @Override
    public InputStream getInputStream() {
        if(StringUtils.isNotBlank(path)){
            if(clazz != null){
                return clazz.getResourceAsStream(path);
            }else if(classLoader != null){
                return classLoader.getResourceAsStream(path);
            }
            return this.getClass().getResourceAsStream(path);
        }
        return null;
    }
}
