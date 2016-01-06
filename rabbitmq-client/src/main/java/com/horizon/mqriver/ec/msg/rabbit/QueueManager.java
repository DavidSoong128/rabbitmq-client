/**
 * NewHeight.com Inc.
 * Copyright (c) 2008-2010 All Rights Reserved.
 */
package com.horizon.mqriver.ec.msg.rabbit;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

import com.horizon.mqriver.api.ec.IEventHandler;
import com.horizon.mqriver.ec.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 队列管理器
 *
 * @author David.Soong
 * @version QueueManager.java, v 0.1 2015年10月8日 下午12:02:52
 */
public final class QueueManager {

	/** 日志 */
	private static final Logger	logger	= LoggerFactory.getLogger(QueueManager.class);

	/**
	 * 构造函数
	 */
	private QueueManager() {

	}

	/**
	 * 创建队列名
	 *
	 * @param handler
	 *            消息处理器
	 * @return 队列名
	 */
	public static String createQueueName(IEventHandler handler) {
		String clazzName = handler.getClass().getName().replace(".", "_");
		if (RabbitConfig.FANOUT_EXCHANGE_TYPE.equals(RabbitConfig.EXCHANGE_TYPE)) {
			String address = getIPAddress();
			String queueSuffix = StringUtils.isEmpty(address) ? "" : "_" + address.replace(".", "_");
			return RabbitConfig.QUEUE_PREFIX + clazzName + queueSuffix;
		}
		return clazzName;
	}

	/**
	 * 
	 * 获取本地IP地址
	 *
	 * @return 本地IP地址
	 */
	private static String getIPAddress() {
		String ipAddr = null;
		try {
			Enumeration<NetworkInterface> netInterfaces = NetworkInterface.getNetworkInterfaces();
			while (netInterfaces.hasMoreElements()) {
				NetworkInterface ni = netInterfaces.nextElement();
				Enumeration<InetAddress> ips = ni.getInetAddresses();
				while (ips.hasMoreElements()) {
					InetAddress ip = ips.nextElement();
					if (ip.isSiteLocalAddress() && !ip.isLoopbackAddress() && ip.getHostAddress().indexOf(":") == -1) {
						ipAddr = ip.getHostAddress();
						break;
					}
				}
			}
		} catch (Exception e) {
			logger.error("获取IP地址异常", e);
		}
		return ipAddr;
	}

}
