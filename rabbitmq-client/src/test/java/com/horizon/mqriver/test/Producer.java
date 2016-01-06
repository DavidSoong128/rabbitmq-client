/**
 * NewHeight.com Inc.
 * Copyright (c) 2008-2010 All Rights Reserved.
 */
package com.horizon.mqriver.test;

import java.io.IOException;

import com.horizon.mqriver.ec.msg.rabbit.RabbitConnectManager;
import com.rabbitmq.client.Channel;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author David.Soong
 * @version Producer.java, v 0.1 2015年9月30日 下午3:30:08
 */
public class Producer {

	/**
	 * <pre>
	 * 
	 * </pre>
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		// ConnectionFactory factory = new ConnectionFactory();
		Channel channel = RabbitConnectManager.getInstance().createChannel();
		try {
			channel.exchangeDeclare("exchange_fanout_test", "fanout");
			channel.basicPublish("exchange_fanout_test", "", null, "fanout_2222".getBytes());
			channel.close();
		} catch (IOException e) {
			// logger.error("", e);
		}
	}

}
