/**
 * NewHeight.com Inc.
 * Copyright (c) 2008-2010 All Rights Reserved.
 */
package com.horizon.mqriver.ec.msg.rabbit;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 交换器管理器 （topic模式）
 * 
 * @author David.Soong
 * @version RabbitTopicExchangeManager.java, v 0.1 2015年10月8日 上午11:04:38
 */
public final class RabbitTopicExchangeManager extends AbstractRabbitExchangeManager {
	/** 日志 */
	private static final Logger	logger	= LoggerFactory.getLogger(RabbitTopicExchangeManager.class);

	/**
	 * 构造函数
	 */
	public RabbitTopicExchangeManager() {
	}

	/**
	 * 定义队列交换器并绑定
	 *
	 * @param exchangeName
	 *            交换器名
	 * @param routeKey
	 *            路由键
	 * @param queueName
	 *            队列名
	 */
	@Override
	public void handleExchange(String exchangeName, String routeKey, String queueName) {
		try {
			channel.exchangeDeclare(exchangeName, RabbitConfig.DIRECT_EXCHANGE_TYPE, true);
			channel.queueDeclare(queueName, true, false, false, null);
			channel.queueBind(queueName, exchangeName, routeKey);
		} catch (IOException e) {
			logger.error("定义队列交换器并绑定失败", e);
		}
	}

}
