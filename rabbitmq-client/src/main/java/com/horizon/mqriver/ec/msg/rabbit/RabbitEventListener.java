/**
 * www.1289.com Inc.
 * Copyright (c) 2012-2015 All Rights Reserved.
 */
package com.horizon.mqriver.ec.msg.rabbit;

import java.util.List;

import com.horizon.mqriver.api.ec.IEventListener;
import com.horizon.mqriver.ec.scan.EventHandlerAnnotationScaner;
import com.horizon.mqriver.ec.scan.SubscribedEventHandler;
import com.horizon.mqriver.ec.serialize.DefaultSerializerFactory;
import com.horizon.mqriver.ec.serialize.Serializer;

/**
 * RabbitMQ事件监听器
 * 
 * @author David.Soong
 * @version EventListener.java, v 0.1 2015年5月6日 上午9:24:41
 */
public final class RabbitEventListener implements IEventListener {

	/**
	 * 监听器启动
	 * 
	 * @param packages
	 *            需要扫描的事件订阅包路径
	 */
	@Override
	public void init(String packages) {
		init(packages, DefaultSerializerFactory.newDefaultSerializer());
	}

	/**
	 * 监听器启动
	 * 
	 * @param packages
	 *            需要扫描的事件订阅包路径
	 * @param serializer
	 *            序列化工具
	 */
	@Override
	public void init(String packages, Serializer serializer) {
		List<SubscribedEventHandler> annotations = EventHandlerAnnotationScaner.scanEventHandler(packages);
		if (annotations.isEmpty()) {
			return;
		}
		// 创建并绑定消息订阅关系
		AbstractRabbitExchangeManager manager = null;
		if (RabbitConfig.FANOUT_EXCHANGE_TYPE.equals(RabbitConfig.EXCHANGE_TYPE)) {
			manager = new RabbitFanoutExchangeManager();
			for (SubscribedEventHandler annotation : annotations) {
				String queueName = QueueManager.createQueueName(annotation.getHandler());
				manager.handleExchange(annotation.getExchangeName(), annotation.getRouteKey(), queueName);
			}
		} else {
			manager = new RabbitTopicExchangeManager();
			for (SubscribedEventHandler annotation : annotations) {
				String queueName = QueueManager.createQueueName(annotation.getHandler());
				manager.handleExchange(annotation.getExchangeName(), annotation.getRouteKey(), queueName);
			}
		}
		// 关闭管道 避免资源浪费
		manager.closeChannel();
		// 启动消息消费线程处理
		RabbitExecutor.getInstance().init(annotations.size());
		for (SubscribedEventHandler annotation : annotations) {
			RabbitExecutor.getInstance().execute(new RabbitEventHandler(serializer, annotation.getHandler()));
		}
	}

}
