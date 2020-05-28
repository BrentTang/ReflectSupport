package com.tzh.reflect;

import com.tzh.reflect.filter.AnnotationFilter;
import com.tzh.reflect.filter.CommonFilter;
import com.tzh.reflect.filter.FieldFilter;
import com.tzh.reflect.filter.MethodFilter;
import com.tzh.reflect.type.BaseTypeConvertor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TZHInvoke {

    private Object obj;

    public TZHInvoke(Object obj){
        this.obj = obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    /**
     * 检查obj是否包含anno指定注解
     * @param anno
     * @return
     */
    public boolean containAnnotation(Class anno){
        Annotation annotation = obj.getClass().getAnnotation(anno);
        return annotation != null;
    }

    /**
     * 设置指定字段值
     * @param field
     * @param val
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    public void setField(String field, Object val) throws NoSuchFieldException, IllegalAccessException {

        Field f = obj.getClass().getDeclaredField(field);
        if (f == null)
            throw new IllegalArgumentException(field + "字段不存在!");
        Class<?> type = f.getType();
        f.setAccessible(true);
        if (BaseTypeConvertor.isBaseOrPackType(type)){
            setBaseTypeField(f, type, val);
        } else {
            f.set(obj, val);
        }
    }

    /**
     * 为给定的field设置值
     * @param field
     * @param val
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    public void setField(Field field, Object val) throws NoSuchFieldException, IllegalAccessException {

        if (field == null)
            throw new IllegalArgumentException(field + "字段不存在!");

        Class<?> type = field.getType();
        field.setAccessible(true);
        if (BaseTypeConvertor.isBaseOrPackType(type)){
            setBaseTypeField(field, type, val);
        } else {
            field.set(obj, val);
        }
    }

    /**
     * 设置基本类型值
     * @param type
     * @param val
     */
    public void setBaseTypeField(Field field, Class type, Object val) throws IllegalAccessException {
        String v = val.toString();
        if (type.equals(int.class)) {
            field.setInt(obj, Integer.parseInt(v));
        } else if (type.equals(Integer.class)) {
            field.set(obj, Integer.valueOf(v));
        } else if (type.equals(short.class)) {
            field.setShort(obj, Short.parseShort(v));
        } else if (type.equals(Short.class)) {
            field.set(obj, Short.valueOf(v));
        } else if (type.equals(long.class)) {
            field.setLong(obj, Long.parseLong(v));
        } else if (type.equals(Long.class)) {
            field.set(obj, Long.valueOf(v));
        } else if (type.equals(byte.class)) {
            field.setByte(obj, Byte.parseByte(v));
        } else if (type.equals(Byte.class)) {
            field.set(obj, Byte.valueOf(v));
        } else if (type.equals(float.class)) {
            field.setFloat(obj, Float.parseFloat(v));
        } else if (type.equals(Float.class)) {
            field.set(obj, Float.valueOf(v));
        } else if (type.equals(double.class)) {
            field.setDouble(obj, Double.parseDouble(v));
        } else if (type.equals(Double.class)) {
            field.set(obj, Double.valueOf(v));
        } else if (type.equals(char.class)) {
            field.setChar(obj, v.charAt(0));
        } else if (type.equals(Character.class)) {
            field.set(obj, v.charAt(0));
        } else if (type.equals(boolean.class)) {
            field.setBoolean(obj, Boolean.parseBoolean(v));
        } else if (type.equals(Boolean.class)) {
            field.set(obj, Boolean.valueOf(v));
        }
    }

    /**
     * 获取当前对象
     * @return
     */
    public Object getInstance(){
        return obj;
    }

    /**
     * 获取指定字段
     * @param fieldName
     * @return
     * @throws NoSuchFieldException
     */
    public Field getField(String fieldName) throws NoSuchFieldException {
        Field field = obj.getClass().getDeclaredField(fieldName);
        if (field == null) {
            field = obj.getClass().getField(fieldName);
        }
        return field;
    }

    /**
     * 获取指定字段的类型
     * @param fieldName
     * @return
     * @throws NoSuchFieldException
     */
    public Class getFieldType(String fieldName) throws NoSuchFieldException {
        Field field = getField(fieldName);
        if (field == null) {
            throw new IllegalArgumentException("未发现" + fieldName + "字段");
        }
        return field.getType();
    }

    /**
     * 指定字段是否包含指定注解
     * @param fieldName
     * @param anno
     * @return
     */
    public boolean fieldContainAnno(String fieldName, Class anno) throws NoSuchFieldException {
        Field field = getField(fieldName);
        Annotation annotation = field.getAnnotation(anno);
        return annotation != null;
    }

    /**
     * 通过字段名获取该字段的指定注解的key-value
     * @param fieldName
     * @param anno
     * @return
     * @throws NoSuchFieldException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public Map<String, Object> getFieldAnnoValue(String fieldName, final Class anno) throws NoSuchFieldException, InvocationTargetException, IllegalAccessException {

        if (anno == null) return null;

        Field field = getField(fieldName);

        if (field == null) {
            throw new IllegalArgumentException("没有" + fieldName + "字段");
        }

        Annotation[] annotations = field.getAnnotations();
        Annotation[] result = filterAnnotation(annotations, anno, new AnnotationFilter() {
            public boolean doFilter(Annotation annotation) {
                return anno.isInstance(annotation);
            }
        });
        //System.out.println("**************" + result.length);
        if (!(result != null && result.length > 0)) {
            return null;
        }

        return getAnnoValue(result[0]);
    }

    /**
     * 通过注解的方法名过滤得到注解key-value
     * @param annotation
     * @param commonFilter
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public Map<String, Object> filterAnnoValue(Annotation annotation, CommonFilter commonFilter) throws InvocationTargetException, IllegalAccessException {

        Method[] methods = annotation.getClass().getDeclaredMethods();
        if (!(methods != null && methods.length > 0))
            return null;
        Map<String, Object> result = new HashMap<String, Object>();
        for (Method m : methods) {
            if (m.getParameterTypes().length > 0)
                continue;
            else if (commonFilter == null) {
                result.put(m.getName(), m.invoke(annotation));
            } else if (commonFilter.doFilter(m.getName())) {
                result.put(m.getName(), m.invoke(annotation));
            }
        }
        return result;
    }

    /**
     * 获取指定注解的key-value
     * @param annotation
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public Map<String, Object> getAnnoValue(Annotation annotation) throws InvocationTargetException, IllegalAccessException {
        return filterAnnoValue(annotation, null);
    }

    /**
     * 获取符合过滤器的注解
     * @param annotations
     * @param anno
     * @param annotationFilter
     * @return
     */
    public Annotation[] filterAnnotation(Annotation[] annotations, Class anno, AnnotationFilter annotationFilter) {
        if (!(annotations != null && annotations.length > 0)) return null;

        List<Annotation> result = new ArrayList<Annotation>();
        for (Annotation a : annotations) {
            if (annotationFilter == null) {
                result.add(a);
            } else if (annotationFilter.doFilter(a)) {
                result.add(a);
            }
        }
        return result.toArray(new Annotation[result.size()]);
    }

    /**
     * 获取所有当前类中声明的字段
     * @return
     */
    public Field[] getDeclaredFields(){
        return getDeclaredFields(null);
    }

    /**
     * 获取符合FieldFilter并且当前类中声明的字段
     * @param fieldFilter
     * @return
     */
    public Field[] getDeclaredFields(FieldFilter fieldFilter) {
        Field[] fields = obj.getClass().getDeclaredFields();
        return filterField(fields, fieldFilter);
    }

    /**
     * 获取所有public修饰的字段（包含父类）
     * @return
     */
    public Field[] getFields(){
        return getFields(null);
    }

    /**
     * 获得指定ieldFilterF的所有public字段
     * @param fieldFilter
     * @return
     */
    public Field[] getFields(FieldFilter fieldFilter){
        Field[] fields = obj.getClass().getFields();
        return filterField(fields, fieldFilter);
    }

    /**
     * 通过指定的FieldFilter，获得符合该过滤器的Fiaeld
     * @param fields
     * @param fieldFilter
     * @return
     */
    public Field[] filterField(Field[] fields, FieldFilter fieldFilter){
        List<Field> result = new ArrayList<Field>();
        for (Field f : fields){
            if (fieldFilter == null) {
                result.add(f);
            } else if (fieldFilter.doFilter(f)){
                result.add(f);
            }
        }
        return result.toArray(new Field[result.size()]);
    }

    /**
     * 获取当前类所有声明的方法（不包含父类方法）
     * @return
     */
    public Method[] getDeclaredMethods(){
        return getDeclaredMethods(null);
    }

    /**
     * 获得符合过滤器的Method
     * @param methodFilter
     * @return
     */
    public Method[] getDeclaredMethods(MethodFilter methodFilter){
        Method[] methods = obj.getClass().getDeclaredMethods();
        return filterMethod(methods, methodFilter);
    }

    /**
     * 过滤掉不符合过滤器的Method
     * @param methods
     * @param methodFilter
     * @return
     */
    public Method[] filterMethod(Method[] methods, MethodFilter methodFilter){
        List<Method> result = new ArrayList<Method>();
        for (Method m : methods){
            if (methodFilter == null){
                result.add(m);
            } else if (methodFilter.doFilter(m)){
                result.add(m);
            }
        }
        return result.toArray(new Method[result.size()]);
    }

    /**
     * 获取所有public的方法
     * @return
     */
    public Method[] getMethods(){
        return getMethods(null);
    }

    /**
     * 获得符合过滤器的Method
     * @param methodFilter
     * @return
     */
    public Method[] getMethods(MethodFilter methodFilter){
        Method[] methods = obj.getClass().getMethods();
        return filterMethod(methods, methodFilter);
    }

    /**
     * 通过methodName以及args调用符合methodName的method方法
     *      如果参数是基本类型需要使用Base*Value类
     * @param methodName
     * @param args
     * @return
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public Object invoke(String methodName, Object... args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = null;
        if (args == null)
            method = obj.getClass().getDeclaredMethod(methodName);
        else{
            method = getMethodWithParamter(methodName, args);
        }
        return method.invoke(obj, args);
    }

    /**
     * 获取带有参数的Method
     * @param methodName
     * @param args
     * @return
     * @throws NoSuchMethodException
     */
    private Method getMethodWithParamter(String methodName, Object... args) throws NoSuchMethodException {
        List<Class> classType = new ArrayList<Class>();
        int count = 0;
        for (Object arg : args){
            if (arg == null){
                throw new IllegalArgumentException("参数中不能包含null");
            }
            Class type = BaseTypeConvertor.convertorBaseType(arg);
            classType.add(type);

            Object val = BaseTypeConvertor.getBaseTypeValue(arg);
            if (val != null){
                args[count] = val;
            }
            count++;
        }
        return obj.getClass().getDeclaredMethod(methodName, classType.toArray(new Class[classType.size()]));
    }
}
