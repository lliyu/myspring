package context.resource.impl;

import context.resource.Resource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * @Auther: Administrator
 * @Date: 2018-12-26 11:08
 * @Description:
 */
public class FileSystemResource implements Resource {

    private File file;

    public FileSystemResource(String path) {
        this.file = new File(path);
    }

    @Override
    public boolean isExist() {
        return file.exists();
    }

    @Override
    public File getFile() {
        return file;
    }

    @Override
    public InputStream getInputStream() throws FileNotFoundException {
        return new FileInputStream(file);
    }
}
