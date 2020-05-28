package com.tzh.reflect.type;

public class BaseTypeConvertor {

    /**
     * 判断clazz是否为基本类型
     * @param clazz
     * @return
     */
    public static boolean isBaseType(Class clazz){
        return //clazz.equals(Integer.class) ||
                clazz.equals(int.class) ||
                //clazz.equals(Byte.class) ||
                clazz.equals(byte.class) ||
                //clazz.equals(Long.class) ||
                clazz.equals(long.class) ||
                //clazz.equals(Double.class) ||
                clazz.equals(double.class) ||
                //clazz.equals(Float.class) ||
                clazz.equals(float.class) ||
                //clazz.equals(Character.class) ||
                clazz.equals(char.class) ||
                //clazz.equals(Short.class) ||
                clazz.equals(short.class) ||
                //clazz.equals(Boolean.class) ||
                clazz.equals(boolean.class);
    }

    /**
     * 判断clazz是否为基本类型或包装类型
     * @param clazz
     * @return
     */
    public static boolean isBaseOrPackType(Class clazz){
        return  clazz.equals(Integer.class) ||
                clazz.equals(int.class) ||
                clazz.equals(Byte.class) ||
                clazz.equals(byte.class) ||
                clazz.equals(Long.class) ||
                clazz.equals(long.class) ||
                clazz.equals(Double.class) ||
                clazz.equals(double.class) ||
                clazz.equals(Float.class) ||
                clazz.equals(float.class) ||
                clazz.equals(Character.class) ||
                clazz.equals(char.class) ||
                clazz.equals(Short.class) ||
                clazz.equals(short.class) ||
                clazz.equals(Boolean.class) ||
                clazz.equals(boolean.class);
    }

    /**
     * 获取BaseType对象中保存的Class
     *      如果参数对象不是BaseType则返回该参数的Class
     * @param baseType
     * @return
     */
    public static Class convertorBaseType(Object baseType){

        if (!(baseType instanceof BaseType)){
            return baseType.getClass();
        }

        BaseType type = (BaseType) baseType;
        return type.getType();
    }

    /**
     * 获取BaseType中保存的value
     *      如果参数对象不是BaseType则返回null
     * @param baseType
     * @return
     */
    public static Object getBaseTypeValue(Object baseType){

        if (!(baseType instanceof BaseType)){
            return null;
        }

        BaseType type = (BaseType) baseType;
        return type.getValue();
    }

}
