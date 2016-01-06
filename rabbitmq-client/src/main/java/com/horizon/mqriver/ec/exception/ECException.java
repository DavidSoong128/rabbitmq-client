/**
 * www.1289.com Inc.
 * Copyright (c) 2012-2015 All Rights Reserved.
 */
package com.horizon.mqriver.ec.exception;

/**
 * 事件异常
 *
 * @author David.Soong
 * @version ECException.java, v 0.1 2015年5月8日 下午2:25:52
 */
public class ECException extends RuntimeException {

	/** 序列化id */
	private static final long	serialVersionUID	= 2640647134946680046L;

	public ECException(String message, Throwable cause) {
		super(message, cause);
	}

	public ECException(String message) {
		super(message);
	}

	public ECException(Throwable cause) {
		super(cause);
	}

}
