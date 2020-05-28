package com.tzh.reflect.type.impl;

import com.tzh.reflect.type.BaseType;
import com.tzh.reflect.type.Type;

/**
 * short的基本类型
 */
public class BaseShortValue extends BaseType {
    public BaseShortValue(short value){
        super(Type.SHORT, value);
    }
}
