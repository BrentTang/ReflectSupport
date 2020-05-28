package com.tzh.reflect.type;

/**
 * 保存了所有基本数据类型的Class
 */
public enum Type {

    INT(int.class), LONG(long.class), SHORT(short.class), BYTE(byte.class),
    FLOAT(float.class), DOUBLE(double.class),
    CHAR(char.class), BOOLEAN(boolean.class);

    private Class type;

    private Type(Class type){
        this.type = type;
    }

    public Class getType(){
        return type;
    }
}
