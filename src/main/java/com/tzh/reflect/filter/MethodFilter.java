package com.tzh.reflect.filter;

import java.lang.reflect.Method;

public interface MethodFilter {

    public boolean doFilter(Method method);

}
