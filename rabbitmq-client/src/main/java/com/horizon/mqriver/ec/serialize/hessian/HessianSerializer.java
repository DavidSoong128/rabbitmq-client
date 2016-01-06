package com.horizon.mqriver.ec.serialize.hessian;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import com.caucho.hessian.io.HessianInput;
import com.caucho.hessian.io.HessianOutput;
import com.horizon.mqriver.ec.serialize.Serializer;

public class HessianSerializer implements Serializer {

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
	public byte[] serialize(Object object) throws IOException {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			HessianOutput ho = new HessianOutput(os);
			ho.writeObject(object);
			return os.toByteArray();
		} finally {
			os.close();
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
	public <T> T deserialize(byte[] by, Class<T> clazz) throws IOException {
		ByteArrayInputStream is = new ByteArrayInputStream(by);
		try {
			HessianInput hi = new HessianInput(is);
			return clazz.cast(hi.readObject());
		} finally {
			is.close();
		}
	}

}
