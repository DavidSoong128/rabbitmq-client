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
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * RabbitMQ链接管理器
 *
 * @author David.Soong
 * @version RabbitEventConnection.java, v 0.1 2015年5月6日 上午11:42:39
 */
public final class RabbitConnectManager {

	/** 日志 */
	private static final Logger	logger	= LoggerFactory.getLogger(RabbitConnectManager.class);

	private ConnectionFactory	factory;
	private Connection			connection;

	/**
	 * 单例处理
	 * 
	 * @author David.Soong
	 * @version RabbitConnectManager.java, v 0.1 2015年5月12日 下午2:43:54
	 */
	private static class RabbitConnectManagerHolder {
		private static RabbitConnectManager	manager	= new RabbitConnectManager();
	}

	/**
	 *
	 * @return RabbitConnectManager 链接管理器
	 */
	public static RabbitConnectManager getInstance() {
		return RabbitConnectManagerHolder.manager;
	}

	/**
	 * 构造函数
	 */
	private RabbitConnectManager() {
		init();
	}

	/**
	 * 初始化RabbitMQ工厂
	 *
	 */
	private void init() {
		factory = new ConnectionFactory();
		factory.setAutomaticRecoveryEnabled(true);
		factory.setUsername(RabbitConfig.USERNAME);
		factory.setPassword(RabbitConfig.PASSWORD);
		factory.setVirtualHost(RabbitConfig.VHOST_PATH);
		// 心跳设置
		factory.setRequestedHeartbeat(3);
		try {
			connection = createConnection();
		} catch (IOException e) {
			logger.error("RabbitMQ链接管理器启动失败", e);
			throw new ECException("无法链接到RabbitMQ服务，请检查RabbitMQ配置或检查RabbitMQ服务是否启动或者网络情况!", e);
		}
	}

	/**
	 * 创建RabbitMQ连接
	 *
	 * @return RabbitMQ连接
	 */
	private Connection createConnection() throws IOException {
		return factory.newConnection(RabbitConfig.address);
	}

	/**
	 * 重连并创建新管道
	 *
	 * @return RabbitMQ管道
	 */
	public synchronized Channel reconnectAndNewChnl() {
		while (!connection.isOpen()) {
			try {
				closeConn();
				connection = createConnection();
			} catch (IOException e) {
				logger.error("重连RabbitMQ失败", e);
			}
		}
		return createChannel();
	}

	/**
	 * 创建RabbitMQ管道
	 *
	 * @return RabbitMQ管道
	 */
	public Channel createChannel() {
		try {
			return connection.createChannel();
		} catch (IOException e) {
			logger.error("创建RabbitMQ管道失败", e);
		}
		return null;
	}

	/**
	 * 关闭连接
	 *
	 */
	private void closeConn() {
		if (connection == null) {
			return;
		}
		try {
			connection.close();
		} catch (IOException ex) {
			logger.error("关闭链接异常", ex);
		}
	}
}
