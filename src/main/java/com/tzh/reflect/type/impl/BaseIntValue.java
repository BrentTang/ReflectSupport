package com.tzh.reflect.type.impl;

import com.tzh.reflect.type.BaseType;
import com.tzh.reflect.type.Type;

/**
 * int的基本类型
 */
public class BaseIntValue extends BaseType {
    public BaseIntValue(int value){
        super(Type.INT, value);
    }
}
