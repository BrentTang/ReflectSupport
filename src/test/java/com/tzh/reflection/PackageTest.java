package com.tzh.reflection;

import com.tzh.reflect.scan.ClassRegister;
import com.tzh.reflect.scan.PackageScan;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PackageTest {

    @Test
    public void test1() throws IOException, ClassNotFoundException {
        PackageScan p = new PackageScan();
        List<Class<?>> re = p.scan("com.tzh");
        for (Class<?> aClass : re) {
            System.out.println(aClass.getName());
        }
    }

    @Test
    public void test2() throws IOException, ClassNotFoundException {
        PackageScan p = new PackageScan();
        File file = new File("E:\\apache-tomcat-7.0.42\\webapps/Test02\\WEB-INF\\classes/Star.xml");
        //String re = p.absolute2ClassPath(file.getAbsolutePath());
        //System.out.println(re);
    }

    @Test
    public void test3() throws IOException, ClassNotFoundException {
        PackageScan pc = new PackageScan();
        List<Class> l1 = new ArrayList<Class>();
        /*ClassRegister<List<Class>> classRegister1 = new ClassRegister<List<Class>>(l1) {
            @Override
            public void doFilter(Class clazz) {
                String name = clazz.getName();
                if (name.endsWith("on")) {
                    getContainer().add(clazz);
                }
            }
        };*/

        ClassRegister<List<Class>> classRegister2 = new ClassRegister<List<Class>>(new ArrayList<Class>()) {
            @Override
            public void doFilter(Class clazz) {
                String name = clazz.getName();
                if (name.endsWith("er")) {
                    getContainer().add(clazz);
                }
            }
        };
        pc.scan("com.tzh", new ClassRegister<List<Class>>(l1) {
            @Override
            public void doFilter(Class clazz) {
                String name = clazz.getName();
                if (name.endsWith("on")) {
                    getContainer().add(clazz);
                }
            }
        }, classRegister2);

        System.out.println(l1);
        System.out.println(classRegister2.getContainer());
    }

}
