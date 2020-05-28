package com.tzh.reflect.type.impl;

import com.tzh.reflect.type.BaseType;
import com.tzh.reflect.type.Type;

/**
 * float的基本类型
 */
public class BaseFloatValue extends BaseType {
    public BaseFloatValue(float value){
        super(Type.FLOAT, value);
    }
}
