package com.tzh.reflect.filter;

public interface ClassFilter {

    /**
     * 过滤Class，返回true则保留，反之不保留
     * @param clazz
     * @return
     */
    public boolean doFilter(Class clazz);

}
