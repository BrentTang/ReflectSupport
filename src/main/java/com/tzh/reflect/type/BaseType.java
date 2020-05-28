package com.tzh.reflect.type;

/**
 * 基本数据类型的父类
 */
public class BaseType {

    private Type type;
    private Object value;
    public BaseType(){}
    public BaseType(Type type, Object value){
        this.type = type;
        this.value = value;
    }
    protected void setType(Type type){
        this.type = type;
    }

    public Class getType(){
        return type.getType();
    }

    public Object getValue() {
        return value;
    }

    protected void setValue(Object value) {
        this.value = value;
    }
}
