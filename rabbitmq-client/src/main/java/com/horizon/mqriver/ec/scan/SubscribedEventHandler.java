/**
 * www.1289.com Inc.
 * Copyright (c) 2012-2015 All Rights Reserved.
 */
package com.horizon.mqriver.ec.scan;

import com.horizon.mqriver.api.ec.IEventHandler;

/**
 * 订阅事件的事件处理器
 *
 * @author David.Soong
 * @version ClassMethodAnnotation.java, v 0.1 2015年5月6日 上午9:55:57
 */
public class SubscribedEventHandler {

	private final String		exchangeName;
	private final String		routeKey;
	private final IEventHandler handler;

	public SubscribedEventHandler(String exchangeName, String routeKey, IEventHandler handler) {
		this.exchangeName = exchangeName;
		this.routeKey = routeKey;
		this.handler = handler;
	}

	public String getExchangeName() {
		return exchangeName;
	}

	public String getRouteKey() {
		return routeKey;
	}

	public IEventHandler getHandler() {
		return handler;
	}

}
