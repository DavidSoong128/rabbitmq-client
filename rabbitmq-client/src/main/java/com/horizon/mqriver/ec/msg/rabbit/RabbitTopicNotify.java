package com.horizon.mqriver.ec.msg.rabbit;

import java.io.IOException;

import com.horizon.mqriver.api.ec.IEventNotifier;
import com.horizon.mqriver.ec.exception.ECException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.horizon.mqriver.ec.msg.Event;
import com.rabbitmq.client.MessageProperties;
import com.rabbitmq.client.ShutdownSignalException;

/**
 * RabbitMQ生产者（Topic）
 *
 * @author David.Soong
 * @version RabbitTopicNotify.java, v 0.1 2015年10月8日 上午10:22:43
 */
public final class RabbitTopicNotify extends AbstractRabbitNotify {

	/** 日志 */
	private static final Logger	logger	= LoggerFactory.getLogger(RabbitTopicNotify.class);

	/**
	 * 构造函数
	 */
	private RabbitTopicNotify() {
		super();
	}

	/**
	 * 单例
	 *
	 * @author David.Soong
	 * @version RabbitNotify.java, v 0.1 2015年5月26日 下午3:32:39
	 */
	private static class RabbitTopicNotifyHolder {
		private static IEventNotifier notifier	= new RabbitTopicNotify();
	}

	/**
	 * 单例
	 *
	 * @return IEventNotifier实例
	 */
	public static IEventNotifier getInstance() {
		return RabbitTopicNotifyHolder.notifier;
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
	public void notifyEvent(Event event) throws ECException {
		initChannel();
		try {
			byte[] bytes = serializer.serialize(event);
			channel.basicPublish(event.getExchange(), event.getRoutekey(),
					event.isDurable() ? MessageProperties.PERSISTENT_TEXT_PLAIN : null, bytes);
		} catch (ShutdownSignalException | IOException e) {
			logger.error("发送事件失败," + event, e);
			throw new ECException("发送事件失败," + event, e);
		}
	}
}
