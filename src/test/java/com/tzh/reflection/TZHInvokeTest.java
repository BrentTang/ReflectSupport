package com.tzh.reflection;

import com.tzh.reflect.TZHClassLoader;
import com.tzh.reflect.TZHInvoke;
import com.tzh.reflection.entity.User;
import org.junit.Test;

import java.io.IOException;

public class TZHInvokeTest {

    @Test
    public void test1() throws ClassNotFoundException, InstantiationException, IllegalAccessException, IOException, NoSuchFieldException {
        TZHClassLoader loader = new TZHClassLoader();
        TZHInvoke invoke = loader.getBaseInvoke("com.tzh.reflection.entity.User");
        invoke.setField("name", "tang");
        invoke.setField("age", 10);
        invoke.setField("money", "200.0");
        invoke.setField("honest", true);
        invoke.setField("rank", '良');
        invoke.setField("gender", '男');
        User user = (User) invoke.getInstance();
        System.out.println(user);
    }

    @Test
    public void test2() throws ClassNotFoundException, InstantiationException, IllegalAccessException, IOException, NoSuchFieldException {
        TZHClassLoader loader = new TZHClassLoader();
        TZHInvoke invoke = loader.getBaseInvoke("com.tzh.reflection.entity.User");
        invoke.setField("id", "tzh001");
        User user = (User) invoke.getInstance();
        System.out.println(user);

        System.out.println(User.getId());
    }
}
