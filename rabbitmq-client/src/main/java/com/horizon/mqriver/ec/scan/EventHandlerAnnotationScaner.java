/**
 * www.1289.com Inc.
 * Copyright (c) 2012-2015 All Rights Reserved.
 */
package com.horizon.mqriver.ec.scan;

import java.util.ArrayList;
import java.util.List;

import com.horizon.mqriver.ec.annotation.Consume;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.horizon.mqriver.api.ec.IEventHandler;

/**
 * 事件处理器注解扫描器
 * 
 * @author David.Soong
 * @version EventHandlerAnnotationScaner.java, v 0.1 2015年5月6日 上午10:20:48
 */
public final class EventHandlerAnnotationScaner {

	/** 日志 */
	private static final Logger	logger	= LoggerFactory.getLogger(EventHandlerAnnotationScaner.class);

	private EventHandlerAnnotationScaner() {
	}

	/**
	 * 扫描全部的事件处理器
	 *
	 * @param packages
	 *            扫描的包路径
	 * @return 事件处理器方法注解列表
	 */
	public static List<SubscribedEventHandler> scanEventHandler(String packages) {
		List<SubscribedEventHandler> annotations = new ArrayList<SubscribedEventHandler>();
		// 扫描packages包下的全部class并过滤掉com.1289.sdk.ec包的
		List<String> clazzNames = filterPackages(PackageUtil.getClassName(packages, true), "com.1289.sdk.ec");
		String eventHandlerClzName = IEventHandler.class.getName();
		for (String name : clazzNames) {
			annotations.addAll(findAnnotations(name, eventHandlerClzName));
		}
		return annotations;
	}

	/**
	 * 
	 * 过滤指定包的类
	 *
	 * @param clazzNames
	 *            类列表
	 * @param filterPck
	 *            需要过滤的包
	 * @return 过滤后的类列表
	 */
	private static List<String> filterPackages(List<String> clazzNames, String filterPck) {
		List<String> packages = new ArrayList<String>();
		for (String pack : clazzNames) {
			if (!pack.startsWith(filterPck)) {
				packages.add(pack);
			}
		}
		return packages;
	}

	/**
	 * 查找类中全部Comsume注解
	 *
	 * @param className
	 *            类名
	 * @param matchesInterfaceName
	 *            匹配接口名
	 * @return 类全部的Comsume注解
	 */
	private static List<SubscribedEventHandler> findAnnotations(String className, String matchesInterfaceName) {
		try {
			Class<?> clazz = Class.forName(className);
			if (!clazz.isInterface() && matchesInterface(clazz, matchesInterfaceName)) {
				IEventHandler handler = (IEventHandler) clazz.newInstance();
				return getEventHandlerAnnotations(handler);
			}
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			logger.error("查找类中全部Comsume注解异常", e);
		}
		return new ArrayList<SubscribedEventHandler>();
	}

	/**
	 * 判断类是否实现特定接口
	 *
	 * @param clazz
	 *            类
	 * @param interfaceName
	 *            匹配接口全名
	 * @return 类是否实现了interfaceName
	 */
	private static boolean matchesInterface(Class<?> clazz, String interfaceName) {
		Class<?>[] clazzes = clazz.getInterfaces();
		for (Class<?> clz : clazzes) {
			if (interfaceName.equals(clz.getName())) {
				return true;
			}
			return matchesInterface(clz, interfaceName);
		}

		Class<?> superClass = clazz.getSuperclass();
		if (superClass != null) {
			return matchesInterface(superClass, interfaceName);
		}
		return false;
	}

	/**
	 * 获取事件处理器全部Comsume注解
	 *
	 * @param handler
	 *            事件处理器
	 * @return 事件处理器方法注解列表
	 */
	private static List<SubscribedEventHandler> getEventHandlerAnnotations(IEventHandler handler) {
		List<Consume> subscribes = ClassAnnotationScan.methodAnnotation(handler.getClass().getMethods(), Consume.class);
		List<SubscribedEventHandler> annotations = new ArrayList<SubscribedEventHandler>();
		for (Consume sub : subscribes) {
			annotations.add(new SubscribedEventHandler(sub.exchangeName(), sub.routeKey(), handler));
		}
		return annotations;
	}
}
