package com.tzh.reflect.type.impl;

import com.tzh.reflect.type.BaseType;
import com.tzh.reflect.type.Type;

/**
 * char的基本类型
 */
public class BaseCharValue extends BaseType {
    public BaseCharValue(char value){
        super(Type.CHAR, value);
    }
}
