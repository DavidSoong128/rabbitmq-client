/**
 * 1289.com Inc.
 * Copyright (c) 2008-2010 All Rights Reserved.
 */
package com.horizon.mqriver.ec.serialize.fst;

import java.io.IOException;

import com.horizon.mqriver.ec.serialize.Serializer;

/**
 * Fst序列化工具
 *
 * @author fueze
 * @version FstSerializer.java, v 0.1 2015年5月22日 下午1:07:49 fueze
 */
public class FstSerializer implements Serializer {


	/**
	 * 序列化
	 *
	 * @param object
	 *            被序列化对象
	 * @return 字节数组
	 * @throws IOException
	 *             IO异常
	 */
	@Override
	public byte[] serialize(Object object) throws IOException {
		FstObjectOutput fstObjectOutput = new FstObjectOutput();
		byte[] result = null;
		try {
			fstObjectOutput.writeObject(object);
			result = fstObjectOutput.getBytes();
		} finally {
			fstObjectOutput.clear();
		}
		return result;
	}

	/**
	 * @param bytes
	 *            字节数组
	 * @param clazz
	 *            反序列化对象类型
	 * @return 反序列化对象
	 * @throws IOException
	 *             IO异常
	 */
	@Override
	public <T> T deserialize(byte[] bytes, Class<T> clazz) throws IOException {

		FstObjectInput fstObjectInput = new FstObjectInput(bytes);
		try {
			return fstObjectInput.readObject(clazz);
		} finally {
			fstObjectInput.clear();
		}
	}

}
