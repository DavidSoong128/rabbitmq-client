package com.horizon.mqriver.ec.serialize.java;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.horizon.mqriver.ec.serialize.Serializer;

/**
 * java原生序列化
 *
 * @author David.Soong
 * @version JavaSerializer.java, v 0.1 2015年5月8日 上午9:56:55
 */
public class JavaSerializer implements Serializer {

	/** 日志 */
	private static final Logger	logger	= LoggerFactory.getLogger(JavaSerializer.class);

	/**
	 * 序列化
	 * 
	 * @param object
	 *            被序列化对象
	 * @return 字节数组
	 * @throws IOException
	 *             IO异常
	 * @see Serializer#serialize(java.lang.Object)
	 */
	@Override
	public byte[] serialize(Object object) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(baos);
			oos.writeObject(object);
			return baos.toByteArray();
		} finally {
			baos.close();
			if (oos != null) {
				oos.close();
			}
		}
	}

	/**
	 * 
	 * @param bytes
	 *            字节数组
	 * @param clazz
	 *            反序列化对象类型
	 * @return 反序列化对象
	 * @throws IOException
	 *             IO异常
	 * @see Serializer#deserialize(byte[],
	 *      java.lang.Class)
	 */
	@Override
	public <T> T deserialize(byte[] bytes, Class<T> clazz) throws IOException {
		ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(bais);
			return clazz.cast(ois.readObject());
		} catch (ClassNotFoundException e) {
			logger.error("Java原生反序列化失败", e);
			throw new IOException("Java原生反序列化失败", e);
		} finally {
			bais.close();
			if (ois != null) {
				ois.close();
			}
		}
	}

}
