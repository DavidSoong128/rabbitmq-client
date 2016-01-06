package com.horizon.mqriver.ec.scan;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public final class ClassAnnotationScan {

	/**
	 * 私有构造函数 不允许初始化
	 */
	private ClassAnnotationScan() {
	}

	/**
	 * 
	 * @param clazz
	 *            类
	 * @param clazz2
	 *            注解
	 * @return 返回类中的注解 无则返回null
	 */
	public static <A extends Annotation> A classAnnotation(Class<?> clazz, Class<A> clazz2) {
		A a = clazz.getAnnotation(clazz2);
		return a;
	}

	/**
	 * 
	 * @param methods
	 *            方法数组
	 * @param clazz
	 *            注解
	 * @return 返回方法中的注解列表
	 */
	public static <A extends Annotation> List<A> methodAnnotation(Method[] methods, Class<A> clazz) {
		List<A> list = new ArrayList<A>();
		for (Method method : methods) {
			A a = method.getAnnotation(clazz);
			if (a != null) {
				list.add(a);
			}
		}
		return list;
	}

	/**
	 * 
	 * @param params
	 *            方法数组
	 * @param clazz
	 *            注解
	 * @return 返回参数中的注解列表
	 */
	public static <A extends Annotation> List<A> parameterAnnotation(A[][] params, Class<A> clazz) {
		List<A> list = new ArrayList<A>();
		for (A[] as : params) {
			for (A a : as) {
				if (a != null) {
					list.add(a);
				}
			}
		}
		return list;
	}

	/**
	 * 
	 * @param fields
	 *            成员变量数组
	 * @param clazz
	 *            注解
	 * @return 返回成员变量中的注解列表
	 */
	public static <A extends Annotation> List<A> fieldAnnotation(Field[] fields, Class<A> clazz) {
		List<A> list = new ArrayList<A>();
		for (Field field : fields) {
			A a = field.getAnnotation(clazz);
			if (a != null) {
				list.add(a);
			}
		}
		return list;
	}

	/**
	 * 
	 * @param consturs
	 *            成员变量数组
	 * @param clazz
	 *            注解
	 * @return 返回构造函数中的注解列表
	 */
	public static <A extends Annotation> List<A> consturAnnotation(Constructor<?>[] consturs, Class<A> clazz) {
		List<A> list = new ArrayList<A>();
		for (Constructor<?> constur : consturs) {
			A a = constur.getAnnotation(clazz);
			if (a != null) {
				list.add(a);
			}
		}
		return list;
	}

}
