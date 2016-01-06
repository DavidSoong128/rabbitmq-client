/**
 * 1289.com Inc.
 * Copyright (c) 2008-2010 All Rights Reserved.
 */
package com.horizon.mqriver.api.ec;

import com.horizon.mqriver.ec.exception.ECException;
import com.horizon.mqriver.ec.msg.Event;

/**
 * 消息发送器接口
 * 
 * @author David.Soong
 * @version IEventMsgService.java, v 0.1 2015年4月28日 下午2:23:49
 */
public interface IEventNotifier {

	public void notifyEvent(Event event) throws ECException;

}
