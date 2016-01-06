/**
 * www.1289.com Inc.
 * Copyright (c) 2012-2015 All Rights Reserved.
 */
package com.horizon.mqriver.ec.serialize;

import com.horizon.mqriver.ec.serialize.fst.FstSerializer;

/**
 * 默认序列化工具工厂
 * 
 * @author David.Soong
 * @version DefaultSerializerFactory.java, v 0.1 2015年5月12日 下午5:45:46
 */
public final class DefaultSerializerFactory {

	/**
	 * 构造函数
	 */
	private DefaultSerializerFactory() {
	}

	/**
	 * 新建默认序列化
	 *
	 * @return 序列化器
	 */
	public static Serializer newDefaultSerializer() {
		return new FstSerializer();
	}

}
