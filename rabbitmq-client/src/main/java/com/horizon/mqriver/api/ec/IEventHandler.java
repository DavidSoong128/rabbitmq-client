package com.horizon.mqriver.api.ec;

public interface IEventHandler {
	
	/*
	 * 处理消息
	 */
	public void handleEvent(Object message);

}
