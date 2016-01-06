/**
 * www.1289.com Inc.
 * Copyright (c) 2012-2015 All Rights Reserved.
 */
package com.horizon.mqriver.ec;

import com.horizon.mqriver.api.ec.IEventListener;
import com.horizon.mqriver.ec.msg.rabbit.RabbitEventListener;

/**
 * 默认监听器工厂
 * 
 * @author David.Soong
 * @version DefaultListenerFactory.java, v 0.1 2015年6月8日 上午11:41:23
 */
public final class DefaultEventListenerFactory {

	/**
	 * 构造函数
	 */
	private DefaultEventListenerFactory() {

	}

	
	/**
	 * 新建默认序列化
	 *
	 * @return 序列化器
	 */
	public static IEventListener newDefaultEventListener() {
		return new RabbitEventListener();
	}
}
