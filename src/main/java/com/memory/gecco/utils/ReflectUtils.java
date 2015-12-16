package com.memory.gecco.utils;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.List;
import java.util.Set;

import org.reflections.ReflectionUtils;

import com.memory.gecco.spider.SpiderBean;

/**
 * 泛型，Type的相关知识
 * 
 * http://developer.51cto.com/art/201103/250028.htm
 * http://lsy.iteye.com/blog/220264
 * 
 * @author huchengyi
 *
 */
public class ReflectUtils {
	
	/**
	 * 获得类的所有基类和接口
	 * 
	 * @param clazz
	 * @return
	 */
	public static Set<Class<?>> getAllSuperType(Class clazz) {
		return ReflectionUtils.getAllSuperTypes(clazz);
	}
	
	/**
	 * 是否继承某个基类
	 * 
	 * @param childClazz
	 * @param superClazz
	 * @return
	 */
	public static boolean haveSuperType(Class childClazz, Class superClazz) {
		for(Class<?> clazz : getAllSuperType(childClazz)) {
			if(clazz.equals(superClazz)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 是否继承某个基类
	 * 
	 * @param bean
	 * @param superClazz
	 * @return
	 */
	public static boolean haveSuperType(Object bean, Class superClazz) {
		return haveSuperType(bean.getClass(), superClazz);
	}
	
	public static Class getGenericClass(Type type, int i) {
		if (type instanceof ParameterizedType) { // 处理泛型类型
			return getGenericClass((ParameterizedType) type, i);
		} else if (type instanceof TypeVariable) { // 处理泛型擦拭对象
			return (Class) getGenericClass(((TypeVariable) type).getBounds()[0], 0);
		} else {// class本身也是type，强制转型
			return (Class) type;
		}
	}

	private static Class getGenericClass(ParameterizedType parameterizedType, int i) {
		Object genericClass = parameterizedType.getActualTypeArguments()[i];
		if (genericClass instanceof ParameterizedType) { // 处理多级泛型
			return (Class) ((ParameterizedType) genericClass).getRawType();
		} else if (genericClass instanceof GenericArrayType) { // 处理数组泛型
			return (Class) ((GenericArrayType) genericClass).getGenericComponentType();
		} else if (genericClass instanceof TypeVariable) { // 处理泛型擦拭对象
			return (Class) getGenericClass(((TypeVariable) genericClass).getBounds()[0], 0);
		} else {
			return (Class) genericClass;
		}
	}
	
}