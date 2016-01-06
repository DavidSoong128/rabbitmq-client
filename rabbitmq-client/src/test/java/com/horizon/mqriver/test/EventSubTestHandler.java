/**
 * www.1289.com Inc.
 * Copyright (c) 2012-2015 All Rights Reserved.
 */
package com.horizon.mqriver.test;

/**
 * 
 * @author David.Soong
 * @version EventSubTestHandler.java, v 0.1 2015年5月7日 下午3:41:36
 */
public class EventSubTestHandler extends EventSubHander222 {

	@Override
//	@Consume(exchangeName = "dddd", routeKey = "fffff")
	public void handleEvent(Object message) {
		Message m = (Message) message;
		System.out.println("EventSubTestHandler=====>>>" + m);
	}
	
}
