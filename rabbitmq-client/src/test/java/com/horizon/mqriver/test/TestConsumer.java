package com.horizon.mqriver.test;

import java.io.IOException;

import com.horizon.mqriver.ec.msg.rabbit.RabbitConnectManager;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class TestConsumer {

	public static void main(String[] args) throws IOException {
		Channel channel = RabbitConnectManager.getInstance().createChannel();
		channel.exchangeDeclare("exchange_fanout_test", "fanout");
		String queueName = "com_1289_sdk_test_1";
		channel.queueDeclare(queueName, true, false, false, null);
		channel.queueBind(queueName, "exchange_fanout_test", "");

		System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

		Consumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
					byte[] body) throws IOException {
				String message = new String(body, "UTF-8");
				System.out.println(" [x] Received '" + message + "'");
			}
		};

		channel.basicConsume(queueName, true, consumer);
	}

}
