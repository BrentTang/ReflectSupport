package com.tzh.reflect.scan;

import com.tzh.reflect.TZHClassLoader;
import com.tzh.reflect.filter.ClassFilter;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class PackageScan {

    private final String URLPREFIX = "file:/";
    private final String CLASSSUFFIX = ".class";

    /**
     * 扫描packagePath下的所有类
     * @param packagePath
     * @return
     * @throws IOException
     */
    public List<Class<?>> scan(String packagePath) throws IOException, ClassNotFoundException {
        return scan(packagePath, new ClassFilter() {
            public boolean doFilter(Class clazz) {
                return true;
            }
        });
    }

    /**
     * 扫描packagePath包下所有满足classFilter的Class并返回
     * 如果classFilter为空，则返回packagePath及其子包下的所有Class
     * @param packagePath
     * @param classFilter
     * @return
     * @throws IOException
     */
    public List<Class<?>> scan(String packagePath, ClassFilter classFilter) throws IOException, ClassNotFoundException {
        return doScan(getPackage(packagePath), classFilter);
    }

    /**
     * 执行扫描
     * @param dir
     * @param classFilter
     * @return
     * @throws IOException
     */
    private List<Class<?>> doScan(File dir, ClassFilter classFilter) throws IOException, ClassNotFoundException {

        List<Class<?>> classes = new ArrayList<Class<?>>();
        TZHClassLoader loader = new TZHClassLoader();
        File[] files = dir.listFiles();
        for (File f : files){
            if (f.isFile() && f.getName().endsWith(".class")){
                Class clazz = loader.load(absolute2ClassPath(f.getAbsolutePath()));
                if (classFilter == null){
                    classes.add(clazz);
                } else if (classFilter.doFilter(clazz)){
                    classes.add(clazz);
                }
            } else if (f.isDirectory()){
                classes.addAll(doScan(f, classFilter));
            }
        }
        return classes;
    }

    /**
     * 获取包
     * @param packagePath
     * @return
     */
    private File getPackage(String packagePath) {
        String basePath = getPackageAbsolutePath(packagePath);
        if (basePath == null){
            throw new IllegalArgumentException("packagePath不正确！");
        }
        File basePackage = new File(basePath);
        if (!basePackage.isDirectory()){
            throw new IllegalArgumentException("packagePath不是包！");
        }
        return basePackage;
    }

    /**
     * 通过Class注册链进行扫描
     * @param packagePath
     * @param classRegisters
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void scan(String packagePath, ClassRegister...classRegisters) throws IOException, ClassNotFoundException {
        doScan(getPackage(packagePath), classRegisters);
    }

    /**
     * 通过Class注册链进行扫描，执行器
     * @param dir
     * @param classRegisters
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private void doScan(File dir, ClassRegister... classRegisters) throws IOException, ClassNotFoundException {

        TZHClassLoader loader = new TZHClassLoader();
        File[] files = dir.listFiles();
        for (File f : files){
            if (f.isFile() && f.getName().endsWith(".class")){
                Class clazz = loader.load(absolute2ClassPath(f.getAbsolutePath()));
                classRegisterChain(clazz, classRegisters);
            } else if (f.isDirectory()){
                doScan(f, classRegisters);
            }
        }
    }

    /**
     * Class注册链
     * @param clazz
     * @param classRegisters
     */
    private void classRegisterChain(Class clazz, ClassRegister... classRegisters) {
        for (ClassRegister classRegister : classRegisters) {
            classRegister.doFilter(clazz);
        }
    }

    /**
     * 绝对路径转相对路径
     * @param absolutePath
     * @return
     */
    private String absolute2ClassPath(String absolutePath) {
        URL resource = this.getClass().getClassLoader().getResource("");
        String classpath = resource.toString().substring(URLPREFIX.length());
        return absolutePath.substring(classpath.length(), absolutePath.length() - CLASSSUFFIX.length()).replaceAll("\\\\", ".");
    }

    /**
     * 通过包名获取该包名的绝对路径
     * @param packagePath
     * @return
     */
    private String getPackageAbsolutePath(String packagePath){
        if (!(packagePath != null && packagePath.trim().length() > 0)){
            return null;
        }
        packagePath = packagePath.replace(".", "/");
        URL resource = this.getClass().getClassLoader().getResource("");
        return resource.toString().replaceFirst(URLPREFIX, "") + packagePath;
    }

}
