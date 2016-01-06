package com.horizon.mqriver.ec.msg;

import java.util.Map;

public class Queue {
	private final String					name;
	private volatile boolean				durable;
	private volatile boolean				exclusive;
	private volatile boolean				autoDelete;
	private volatile Map<String, Object>	arguments;

	public Queue(String name) {
		this.name = name;
	}

	public boolean isDurable() {
		return durable;
	}

	public void setDurable(boolean durable) {
		this.durable = durable;
	}

	public boolean isExclusive() {
		return exclusive;
	}

	public void setExclusive(boolean exclusive) {
		this.exclusive = exclusive;
	}

	public boolean isAutoDelete() {
		return autoDelete;
	}

	public void setAutoDelete(boolean autoDelete) {
		this.autoDelete = autoDelete;
	}

	public Map<String, Object> getArguments() {
		return arguments;
	}

	public void setArguments(Map<String, Object> arguments) {
		this.arguments = arguments;
	}

	public String getName() {
		return name;
	}

}