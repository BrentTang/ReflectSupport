package com.tzh.reflect;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class TZHClassLoader extends ClassLoader{

    /**
     * 加载classPath，得到支持方法调用的的BaseInvoke类
     * @param classPath
     * @return
     * @throws IOException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public TZHInvoke getBaseInvoke(String classPath) throws IOException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        Class clazz = load(classPath);
        Object instance = clazz.newInstance();
        return new TZHInvoke(instance);
    }

    /**
     * 加载classPath，获取Class
     * @param classPath
     * @return
     * @throws IOException
     */
    public Class load(String classPath) throws IOException, ClassNotFoundException {

        if (!(classPath != null && classPath.trim().length() > 0)){
            return null;
        }

        if (classPath.contains(File.separator)){
            return loadAbsolutePathClass(classPath);
        } else {
            return loadClassesPathClass(classPath);
        }
    }

    /**
     * 加载类全限定名的类
     * @param name
     * @return
     */
    private Class loadClassesPathClass(String name) throws ClassNotFoundException {
        return Class.forName(name);
    }

    /**
     * 加载绝对路径的类
     * @param classPath
     * @return
     * @throws IOException
     */
    private Class loadAbsolutePathClass(String classPath) throws IOException {

        File file = new File(classPath);
        if (!file.exists()){
            throw new FileNotFoundException();
        }

        FileInputStream in = new FileInputStream(file);
        byte[] bytes = new byte[in.available()];
        in.read(bytes);
        in.close();
        return baseLoader(bytes);
    }

    /**
     * 通过字节数组文件加载类
     * @param bytes
     * @return
     */
    private Class baseLoader(byte[] bytes){
        try {
            return defineClass(bytes, 0, bytes.length);
        } catch (Exception e){
            return null;
        }
    }

}
