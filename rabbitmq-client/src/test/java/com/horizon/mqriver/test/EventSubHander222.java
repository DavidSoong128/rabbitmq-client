/**
 * www.1289.com Inc.
 * Copyright (c) 2012-2015 All Rights Reserved.
 */
package com.horizon.mqriver.test;


import com.horizon.mqriver.api.ec.IEventHandler;

/**
 *
 * @author David.Soong
 * @version EventSubHander222.java, v 0.1 2015年5月7日 上午11:49:10
 */
public class EventSubHander222 implements IEventTestHandler {
	/**
	 * @param message
	 * @see IEventHandler#handleEvent(java.lang.Object)
	 */
	@Override
//	@Consume(exchangeName = "dddd", routeKey = "fffff")
	public void handleEvent(Object message) {
		Message m = (Message) message;
		System.out.println("EventSubHander222=====>>>" + m);
	}

	@Override
	public void test() {
	}

}
