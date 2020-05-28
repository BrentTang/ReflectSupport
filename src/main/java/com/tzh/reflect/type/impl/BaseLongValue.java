package com.tzh.reflect.type.impl;

import com.tzh.reflect.type.BaseType;
import com.tzh.reflect.type.Type;

/**
 * long的基本类型
 */
public class BaseLongValue extends BaseType {
    public BaseLongValue(long value){
        super(Type.LONG, value);
    }
}
