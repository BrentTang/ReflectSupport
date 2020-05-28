package com.tzh.reflect.type.impl;

import com.tzh.reflect.type.BaseType;
import com.tzh.reflect.type.Type;

/**
 * boolean的基本类型
 */
public class BaseBooleanValue extends BaseType {
    public BaseBooleanValue(boolean value){
        super(Type.BOOLEAN, value);
    }
}
