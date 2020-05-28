package com.tzh.reflect.scan;

/**
 * @author 豪
 * @title: ClassRegister
 * @projectName Java
 * @description: TODO
 * @date 2019/5/2719:45
 */
public abstract class ClassRegister<T> {

    private T container;

    public ClassRegister(T container) {
        this.container = container;
    }

    public abstract void doFilter(Class clazz);

    public T getContainer() {
        return container;
    }

}
