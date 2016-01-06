package com.horizon.mqriver.ec.msg.rabbit;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.horizon.mqriver.ec.utils.StringUtils;
import com.rabbitmq.client.Address;

/**
 * RabbitMQ配置读取
 *
 * @author David.Soong
 * @version RabbitConfig.java, v 0.1 2015年5月5日 上午11:01:23
 */
public final class RabbitConfig {
	/** 日志 */
	private static final Logger	logger					= LoggerFactory.getLogger(RabbitConfig.class);

	public static final String	DIRECT_EXCHANGE_TYPE	= "direct";
	public static final String	FANOUT_EXCHANGE_TYPE	= "fanout";
	public static String		EXCHANGE_TYPE			= "direct";
	public static String		QUEUE_PREFIX			= "";
	public static String		USERNAME				= "1289";
	public static String		PASSWORD				= "123456";
	public static String		APP_ID					= "";
	public static String		VHOST_PATH				= "/";
	// public static int CONNECTIONS = 2;
	public static Address[]		address;
	private static final int	DEFAULT_PORT			= 5672;

	private static final String	MQ_PROPERTIES			= "ec_mq.properties";

	/**
	 * 构造函数
	 */
	private RabbitConfig() {

	}

	static {
		load();
	}

	/**
	 * 加载ec_mq.properties文件
	 *
	 */
	private static void load() {
		Properties properties = new Properties();
		InputStream in = null;
		try {
			in = Thread.currentThread().getContextClassLoader().getResourceAsStream(MQ_PROPERTIES);
			properties.load(in);
			USERNAME = properties.getProperty(RabbitConstants.USER_NAME);
			PASSWORD = properties.getProperty(RabbitConstants.PASSWORD);
			String hosts = properties.getProperty(RabbitConstants.HOSTS);
			String[] hostsArray = hosts.split("\\,");
			int nodeCount = hostsArray.length;
			address = new Address[nodeCount];
			int port = DEFAULT_PORT;
			for (int i = 0; i < nodeCount; i++) {
				String[] hostlist = hostsArray[i].split(":");
				if (hostlist.length >= 2) {
					port = StringUtils.isNotEmpty(hostlist[1]) ? Integer.valueOf(hostlist[1]) : DEFAULT_PORT;
					address[i] = new Address(hostlist[0], port);
				} else {
					address[i] = new Address(hostsArray[i]);
				}
			}
			EXCHANGE_TYPE = StringUtils.isNotEmpty(properties.getProperty(RabbitConstants.EXCHANGE_TYPE)) ? properties
					.getProperty(RabbitConstants.EXCHANGE_TYPE) : DIRECT_EXCHANGE_TYPE;
			APP_ID = properties.getProperty(RabbitConstants.APP_ID);
			VHOST_PATH = StringUtils.isNotEmpty(APP_ID) ? (VHOST_PATH + APP_ID) : VHOST_PATH;
			QUEUE_PREFIX = StringUtils.isNotEmpty(APP_ID) ? APP_ID + "_" : "";

		} catch (IOException e) {
			logger.error("加载ec_mq.properties文件失败", e);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					logger.error("关闭输入流失败", e);
				}
			}
		}
	}
}
