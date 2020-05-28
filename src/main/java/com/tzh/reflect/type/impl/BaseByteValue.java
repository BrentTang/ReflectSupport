package com.tzh.reflect.type.impl;

import com.tzh.reflect.type.BaseType;
import com.tzh.reflect.type.Type;

/**
 * byte的基本类型
 */
public class BaseByteValue extends BaseType {
    public BaseByteValue(byte value){
        super(Type.BYTE, value);
    }
}
