package com.horizon.mqriver.ec.msg.rabbit;

import java.io.IOException;

import com.horizon.mqriver.ec.serialize.Serializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.horizon.mqriver.api.ec.IEventHandler;
import com.horizon.mqriver.ec.exception.ECException;
import com.horizon.mqriver.ec.msg.Event;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.ShutdownListener;
import com.rabbitmq.client.ShutdownSignalException;

/**
 * 消息队列处理线程
 *
 * @author David.Soong
 * @version RabbitEventHandler.java, v 0.1 2015年5月7日 上午10:13:10
 */
final class RabbitEventHandler implements Runnable {

	/** 日志 */
	private static final Logger			logger	= LoggerFactory.getLogger(RabbitEventHandler.class);
	/** 序列化工具 */
	private final Serializer serializer;
	/** 消费者 */
	private volatile QueueingConsumer	consumer;
	/** 事件处理 */
	private final IEventHandler			handler;
	/** 管道 */
	private volatile Channel			channel;
	/** 队列名 */
	private final String				queueName;

	private volatile boolean			isClose	= false;

	/**
	 * 构造函数
	 * 
	 * @param seralizer
	 *            序列化工具
	 * @param handler
	 *            消息处理器
	 */
	public RabbitEventHandler(Serializer seralizer, IEventHandler handler) {
		this.serializer = seralizer;
		this.handler = handler;
		// 初始化管道链接
		initChannel();
		queueName = QueueManager.createQueueName(handler);
		bindQueue(queueName);
		logger.info("监听消息队列，队列名称为" + queueName);
	}

	/**
	 * 初始化管道链接
	 */
	private void initChannel() {
		this.channel = RabbitConnectManager.getInstance().createChannel();
		if (this.channel == null) {
			throw new ECException("无法链接到RabbitMQ服务，请检查RabbitMQ配置或检查RabbitMQ服务是否启动或者网络情况!");
		}
	}

	/**
	 * 消费者绑定队列
	 *
	 * @param queueName
	 *            队列名
	 */
	private void bindQueue(final String queueName) {
		// BlockingQueue<QueueingConsumer.Delivery> blockingQueue = new
		// LinkedBlockingQueue<QueueingConsumer.Delivery>();
		// 连接关闭处理
		channel.addShutdownListener(new ShutdownListener() {
			@Override
			public void shutdownCompleted(ShutdownSignalException cause) {
				isClose = true;
				// 重启新新线程处理
				RabbitExecutor.getInstance().execute(new RabbitEventHandler(serializer, handler));
				try {
					channel.close();
				} catch (IOException e) {
					logger.error("管道关闭异常", e);
				}
			}
		});

		try {
			consumer = new QueueingConsumer(channel);
			channel.basicConsume(queueName, false, consumer);
		} catch (IOException e) {
			logger.error("消费者绑定队列失败", e);
		}
	}

	/**
	 * 消费处理消息
	 */
	private void consumeEvent() {
		try {
			QueueingConsumer.Delivery delivery = consumer.nextDelivery();
			this.handleEvent(delivery);
		} catch (ShutdownSignalException | ConsumerCancelledException | InterruptedException e) {
			logger.error("消费处理消息失败", e);
		} catch (Exception e) {
			logger.error("消费处理消息失败", e);
		}
	}

	/**
	 * 处理消息
	 * 
	 * @param delivery
	 *            消息传递
	 */
	private void handleEvent(QueueingConsumer.Delivery delivery) {
		byte[] bytes = delivery.getBody();
		try {
			Event message = serializer.deserialize(bytes, Event.class);
			// 消息处理业务
			handler.handleEvent(message.getMessage());
			// 确认消息已经收到
			this.basicAck(delivery);
		} catch (IOException ex) {
			logger.error(this.getStringFromBytes(bytes));
			logger.error(this.queueName + "队列上的消息反序列化失败", ex);
			// 反序列化失败回复ACK
			this.basicAck(delivery);
		} catch (Exception ex) {
			logger.error("事件处理失败", ex);
			this.basicNackMessage(delivery);
		}
	}

	private String getStringFromBytes(byte[] bytes) {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		if (bytes != null) {
			for (int i = 0; i < bytes.length; i++) {
				sb.append(bytes[i]).append(",");
			}
		}
		sb.append("]");
		return sb.toString();
	}

	/**
	 * 回复ACK
	 *
	 * @param delivery
	 *            消费者delivery
	 */
	private void basicAck(QueueingConsumer.Delivery delivery) {
		try {
			// 确认消息已经收到
			channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
		} catch (IOException ex) {
			logger.error("回复ack失败,delivery tag是" + delivery.getEnvelope().getDeliveryTag(), ex);
		}
	}

	/**
	 * 回复非ACK
	 *
	 * @param delivery
	 *            消费者delivery
	 */
	private void basicNackMessage(QueueingConsumer.Delivery delivery) {
		try {
			channel.basicNack(delivery.getEnvelope().getDeliveryTag(), false, true);
		} catch (IOException ex) {
			logger.error("消息通知nack失败", ex);
		}
	}

	/**
	 * 线程执行
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		while (!isClose) {
			consumeEvent();
		}
	}

}
