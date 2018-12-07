package utils;

import org.junit.Test;

import java.io.*;

/**
 * @Auther: Administrator
 * @Date: 2018-12-07 09:27
 * @Description:
 */
public class MyClassLoader extends ClassLoader {

    @Test
    public void test() throws Exception {
        File file = new File(MyClassLoader.class.getResource("/").getPath());
        findFiles(file);
    }

    public void findFiles(File file) throws Exception {
        if(file == null){
            return;
        }
        File[] files = file.listFiles();
        for(File fi:files){
            if(fi.isDirectory()){
                //是目录就递归调用
                findFiles(fi);
            }else {
                //加载字节码
                Class clazz = loadClass(fi);
            }
        }
    }

    //将class文件加载为class对象
    private Class loadClass(File file) throws Exception {
        //判断文件是否合法
        boolean isClass = isClassFile(file);
        if(!isClass)
            return null;
        InputStream is = new FileInputStream(file);
        BufferedInputStream bis = new BufferedInputStream(is);
        ByteArrayOutputStream  bos = new ByteArrayOutputStream ();
        //这里不能一次读取多个字节 new byte[1024];这样会可能导致最后多处一部分空白的字节
        int res = 0;
        while ((res = bis.read()) != -1){
            bos.write(res);
        }
        byte[] bytes = bos.toByteArray();
        String[] split = file.getName().split("\\.");
        Class<?> aClass = this.defineClass(split[0], bytes, 0, bytes.length);
        return aClass;
    }

    private boolean isClassFile(File file) {
        if(file == null)
            return false;
        String name = file.getName();
        String[] split = name.split("\\.");
        if(split.length == 0)
            return false;
        if(split[split.length-1].equals("class"))
            return true;
        return true;
    }

}
