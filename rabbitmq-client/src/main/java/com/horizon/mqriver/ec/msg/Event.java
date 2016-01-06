/**
 * 1289.com Inc.
 * Copyright (c) 2008-2010 All Rights Reserved.
 */
package com.horizon.mqriver.ec.msg;

import java.io.Serializable;

/**
 * 事件定义
 *
 * @author David.Soong
 * @version Event.java, v 0.1 2015年5月5日 下午2:26:59
 */
public final class Event implements Serializable {

	/** 系列化ID */
	private static final long	serialVersionUID	= -1069417416382607096L;
	/** 交换器 */
	private final String		exchange;
	/** 路由键 */
	private final String		routekey;
	/** 消息内容 */
	private final Object		message;
	/** 消息是否持久化 */
	private final boolean		durable;
	

	/**
	 * 构造函数
	 * 
	 * @param domainName 领域名
	 * @param eventName 事件名
	 * @param message 事件内容
	 */
	public Event(final String domainName, final String eventName, final Object message) {
		this(domainName, eventName, message, false);
	}

	/**
	 * 构造函数
	 * 
	 * @param domainName 领域名
	 * @param eventName 事件名
	 * @param message 事件内容
	 * @param durable 事件是否持久化
	 */
	public Event(final String domainName, final String eventName, final Object message, final boolean durable) {
		this.exchange = domainName;
		this.routekey = eventName;
		this.message = message;
		this.durable = durable;
	}

	public Object getMessage() {
		return message;
	}

	public String getDomainName() {
		return exchange;
	}

	public String getEventName() {
		return routekey;
	}

	public String getExchange() {
		return exchange;
	}

	public String getRoutekey() {
		return routekey;
	}

	public boolean isDurable() {
		return durable;
	}

	@Override
	public String toString() {
		return "交换器为" + exchange + ",路由键为" + routekey + ",消息内容为" + message;
	}

}
