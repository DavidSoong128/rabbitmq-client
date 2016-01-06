/**
 * www.1289.com Inc.
 * Copyright (c) 2012-2015 All Rights Reserved.
 */
package com.horizon.mqriver.ec.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 订阅消息
 *
 * @author David.Soong
 * @version Comsume.java, v 0.1 2015年5月11日 下午3:52:00
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Consume {

	/**
	 * 交换器
	 * 
	 * @return 交换器名
	 */
	String exchangeName();

	/**
	 * 路由键
	 * 
	 * @return 路由键值
	 */
	String routeKey();

}
