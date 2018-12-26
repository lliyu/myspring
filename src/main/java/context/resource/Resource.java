package context.resource;

import java.io.File;

/**
 * 存放读取的配置文件的接口
 */
public interface Resource extends InputStreamSource{

    boolean isExist();

    File getFile();

}
