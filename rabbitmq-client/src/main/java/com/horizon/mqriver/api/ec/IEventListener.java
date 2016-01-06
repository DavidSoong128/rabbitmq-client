/**
 * www.1289.com Inc.
 * Copyright (c) 2012-2015 All Rights Reserved.
 */
package com.horizon.mqriver.api.ec;

import com.horizon.mqriver.ec.serialize.Serializer;

/**
 * 事件监听器
 * 
 * @author David.Soong
 * @version IEventListener.java, v 0.1 2015年5月6日 下午2:50:19
 */
public interface IEventListener {

	/**
	 * 监听器启动 序列化默认为Java原生序列化
	 * 
	 * @param packages
	 *            需要扫描的事件订阅包路径
	 */
	public void init(String packages);

	/**
	 * 监听器启动
	 * 
	 * @param packages
	 *            需要扫描的事件订阅包路径
	 * @param serializer
	 *            序列化工具
	 */
	public void init(String packages, Serializer serializer);

}
