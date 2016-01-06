package com.horizon.mqriver.test;

import com.horizon.mqriver.api.ec.IEventHandler;

public class TestSubHandler111 implements IEventHandler {

	@Override
//	@Consume(exchangeName = "dddd", routeKey = "TestSubHandler111")
	public void handleEvent(Object message) {
		System.out.println("--------------" + message);
//		try {
//			TimeUnit.SECONDS.sleep(10);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
	}

}
