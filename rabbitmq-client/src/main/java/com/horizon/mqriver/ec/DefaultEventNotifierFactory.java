/**
 * www.1289.com Inc.
 * Copyright (c) 2012-2015 All Rights Reserved.
 */
package com.horizon.mqriver.ec;

import com.horizon.mqriver.api.ec.IEventNotifier;
import com.horizon.mqriver.ec.msg.rabbit.RabbitFanoutNotify;
import com.horizon.mqriver.ec.msg.rabbit.RabbitTopicNotify;
import com.horizon.mqriver.ec.msg.rabbit.RabbitConfig;

/**
 * 消息发送器接口默认实现
 * 
 * @author David.Soong
 * @version EventNotifierFactory.java, v 0.1 2015年6月8日 上午11:33:39
 */
public final class DefaultEventNotifierFactory {

	/**
	 * 构造函数
	 */
	private DefaultEventNotifierFactory() {
	}

	/**
	 * 获取默认实现的消息发送器接口
	 *
	 * @return 消息发送器接口
	 */
	public static IEventNotifier getEventNotifier() {
		if (RabbitConfig.FANOUT_EXCHANGE_TYPE.equals(RabbitConfig.EXCHANGE_TYPE)) {
			return RabbitFanoutNotify.getInstance();
		}
		return RabbitTopicNotify.getInstance();
	}

	/**
	 * 获取默认实现的消息发送器接口
	 *
	 * @return 消息发送器接口
	 */
	public static IEventNotifier getEventNotifier(String type) {
		if (RabbitConfig.FANOUT_EXCHANGE_TYPE.equals(type)) {
			return RabbitFanoutNotify.getInstance();
		}
		return RabbitTopicNotify.getInstance();
	}
}
