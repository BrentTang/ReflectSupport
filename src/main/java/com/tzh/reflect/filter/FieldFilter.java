package com.tzh.reflect.filter;

import java.lang.reflect.Field;

public interface FieldFilter {

    public boolean doFilter(Field field);

}
