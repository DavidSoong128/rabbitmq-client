/**
 * www.1289.com Inc.
 * Copyright (c) 2012-2015 All Rights Reserved.
 */
package com.horizon.mqriver.ec.msg.rabbit;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import com.horizon.mqriver.api.ec.IEventNotifier;
import com.horizon.mqriver.ec.exception.ECException;
import com.horizon.mqriver.ec.serialize.DefaultSerializerFactory;
import com.horizon.mqriver.ec.serialize.Serializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.horizon.mqriver.ec.msg.Event;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ShutdownListener;
import com.rabbitmq.client.ShutdownSignalException;

/**
 * RabbitMQ生产者（抽象类）
 *
 * @author David.Soong
 * @version AbstractRabbitNotify.java, v 0.1 2015年5月5日 上午11:25:43
 */
public abstract class AbstractRabbitNotify implements IEventNotifier {

	/** 日志 */
	private static final Logger	logger	= LoggerFactory.getLogger(AbstractRabbitNotify.class);
	/** 序列化工具 */
	protected final Serializer serializer;
	/** 管道 */
	protected volatile Channel	channel;

	/**
	 * 构造函数
	 */
	public AbstractRabbitNotify() {
		this(DefaultSerializerFactory.newDefaultSerializer());
	}

	/**
	 * 构造函数
	 * 
	 * @param serializer
	 *            序列化工具
	 */
	private AbstractRabbitNotify(Serializer serializer) {
		this.serializer = serializer;
		this.channel = RabbitConnectManager.getInstance().createChannel();
		handleShutdown();
	}

	/**
	 * 初始化管道 避免在初始化时直接失败
	 *
	 */
	protected void initChannel() {
		if (channel != null) {
			return;
		}
		synInitChannel();
	}

	/**
	 * 加锁初始化管道
	 *
	 */
	private synchronized void synInitChannel() {
		if (channel != null) {
			return;
		}
		channel = RabbitConnectManager.getInstance().createChannel();
		if (channel == null) {
			throw new ECException("发送事件失败,无法链接到RabbitMQ服务，请检查RabbitMQ配置或检查RabbitMQ服务是否启动或者网络情况");
		}
		handleShutdown();
	}

	/**
	 * 监听管道关闭处理
	 *
	 */
	private void handleShutdown() {
		if (channel == null) {
			return;
		}
		// 连接关闭处理
		channel.addShutdownListener(new ShutdownListener() {
			@Override
			public void shutdownCompleted(ShutdownSignalException cause) {
				// 连接断开 重置管道
				logger.error("管道已经关闭", cause);
				Channel chnl = RabbitConnectManager.getInstance().reconnectAndNewChnl();
				Random random = new Random();
				while (chnl == null) {
					try {
						// 随机睡眠时间 0-2秒
						TimeUnit.SECONDS.sleep(random.nextInt(2));
					} catch (InterruptedException e) {
						logger.error("连接关闭处理中出现中断异常", e);
					}
					chnl = RabbitConnectManager.getInstance().reconnectAndNewChnl();
				}
				try {
					channel.close();
				} catch (IOException e) {
					logger.error("管道关闭异常", e);
				}
				// 连接断开 重置管道
				channel = chnl;
			}
		});
	}

	/**
	 * 发送事件
	 * 
	 * @param event
	 *            事件
	 * @throws ECException
	 *             事件异常
	 */
	@Override
	public abstract void notifyEvent(Event event) throws ECException;

}
