/**
 * NewHeight.com Inc.
 * Copyright (c) 2008-2010 All Rights Reserved.
 */
package com.horizon.mqriver.ec.msg.rabbit;

/**
 * rabbitmq 常量类
 *
 * @author David.Soong
 * @version RabbitConstants.java, v 0.1 2015年10月8日 下午6:05:29
 */
public final class RabbitConstants {

	private RabbitConstants() {
	}

	/** 用户名 */
	public static final String	USER_NAME		= "username";
	/** 密码 */
	public static final String	PASSWORD		= "password";
	/** RabbitMQ主机 */
	public static final String	HOSTS			= "hosts";
	/** RabbitMQ交换机类型 */
	public static final String	EXCHANGE_TYPE	= "exchange_type";
	/** 节点序号 */
	public static final String	APP_ID			= "app_id";
}
