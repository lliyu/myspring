package context.resource.impl;

import context.resource.Resource;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @Auther: Administrator
 * @Date: 2018-12-26 13:51
 * @Description:
 */
public class URLResource implements Resource {

    private URL url;

    public URLResource(String path) throws MalformedURLException {
        this.url = new URL(path);
    }

    @Override
    public boolean isExist() {
        return url != null;
    }

    @Override
    public File getFile() {
        return null;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return url.openStream();
    }
}
