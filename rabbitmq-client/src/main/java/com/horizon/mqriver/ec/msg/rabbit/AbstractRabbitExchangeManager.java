/**
 * www.1289.com Inc.
 * Copyright (c) 2012-2015 All Rights Reserved.
 */
package com.horizon.mqriver.ec.msg.rabbit;

import java.io.IOException;

import com.horizon.mqriver.ec.exception.ECException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitmq.client.Channel;

/**
 * 交换器管理器
 * 
 * @author David.Soong
 * @version RabbitExchangeManager.java, v 0.1 2015年5月6日 下午2:31:54
 */
abstract class AbstractRabbitExchangeManager {

	/** 日志 */
	private static final Logger	logger	= LoggerFactory.getLogger(AbstractRabbitExchangeManager.class);

	protected final Channel		channel;

	/**
	 * 构造函数
	 */
	public AbstractRabbitExchangeManager() {
		this.channel = RabbitConnectManager.getInstance().createChannel();
		if (this.channel == null) {
			throw new ECException("无法链接到RabbitMQ服务，请检查RabbitMQ配置或检查RabbitMQ服务是否启动或者网络情况!");
		}
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
	public abstract void handleExchange(String exchangeName, String routeKey, String queueName);

	// {
	// try {
	// channel.exchangeDeclare(exchangeName, RabbitConfig.DIRECT_EXCHANGE_TYPE,
	// true);
	// channel.queueDeclare(queueName, true, false, false, null);
	// channel.queueBind(queueName, exchangeName, routeKey);
	// } catch (IOException e) {
	// logger.error("定义队列交换器并绑定失败", e);
	// }
	// }

	protected void closeChannel() {
		try {
			this.channel.close();
		} catch (IOException e) {
			logger.error("关闭RabbitMQ管道", e);
		}
	}
}
