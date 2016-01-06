/**
 * www.1289.com Inc.
 * Copyright (c) 2012-2015 All Rights Reserved.
 */
package com.horizon.mqriver.test;

import com.horizon.mqriver.api.ec.IEventHandler;

/**
 *
 * @author David.Soong
 * @version TestEventHandler.java, v 0.1 2015年5月6日 下午3:02:51
 */
public class TestEventHandler implements IEventHandler {

	/**
	 * @param message
	 * @see IEventHandler#handleEvent(java.lang.Object)
	 */
	@Override
//	@Consume(exchangeName = "dddd", routeKey = "eeee")
	public void handleEvent(Object message) {
		Message m = (Message) message;
		System.out.println("TestEventHandler=====>>>" + m);
	}

}
