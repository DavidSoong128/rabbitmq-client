package com.horizon.mqriver.ec.msg;

import com.rabbitmq.client.MessageProperties;

public class Message {

	private final MessageProperties	messageProperties;

	private final byte[]			body;

	public Message(byte[] body, MessageProperties messageProperties) {
		this.body = body;
		this.messageProperties = messageProperties;
	}

	public byte[] getBody() {
		return this.body;
	}

	public MessageProperties getMessageProperties() {
		return this.messageProperties;
	}

}