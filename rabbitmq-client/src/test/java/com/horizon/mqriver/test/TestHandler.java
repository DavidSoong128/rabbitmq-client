package com.horizon.mqriver.test;

import com.horizon.mqriver.api.ec.IEventHandler;
import com.horizon.mqriver.ec.annotation.Consume;

public class TestHandler implements IEventHandler {

	@Consume(exchangeName = "dddd", routeKey = "eeee")
	public void handleEvent(Object message) {
		Message m = (Message) message;
		System.out.println("TestHandler=====>>>" + m);
	}

}
