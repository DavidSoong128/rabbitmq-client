/**
 * www.1289.com Inc.
 * Copyright (c) 2012-2015 All Rights Reserved.
 */
package com.horizon.mqriver.ec.msg.rabbit;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author David.Soong
 * @version RabbitExecutor.java, v 0.1 2015年7月8日 上午10:04:20
 */
final class RabbitExecutor {

	/** 线程池 */
	private ExecutorService	executor;

	private static class RabbitExecutorsHolder {
		private static RabbitExecutor	rabbitExecutor	= new RabbitExecutor();
	}

	/**
	 * 构造函数
	 */
	private RabbitExecutor() {
	}

	/**
	 * 
	 * 单例
	 *
	 * @return 获取单例
	 */
	public static RabbitExecutor getInstance() {
		return RabbitExecutorsHolder.rabbitExecutor;
	}

	/**
	 * 执行线程
	 * 
	 * @param command
	 *            命令
	 */

	public void execute(Runnable command) {
		executor.execute(command);
	}

	/**
	 * 初始化线程池大小
	 *
	 * @param poolSize
	 *            线程池大小
	 */
	public void init(int poolSize) {
		executor = Executors.newFixedThreadPool(poolSize);
	}
}
