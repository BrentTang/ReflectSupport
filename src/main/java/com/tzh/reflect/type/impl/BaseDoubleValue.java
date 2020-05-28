package com.tzh.reflect.type.impl;

import com.tzh.reflect.type.BaseType;
import com.tzh.reflect.type.Type;

/**
 * double的基本类型
 */
public class BaseDoubleValue extends BaseType {
    public BaseDoubleValue(double value){
        super(Type.DOUBLE, value);
    }
}
