package com.horizon.mqriver.ec.serialize.kryo;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.horizon.mqriver.ec.serialize.Serializer;

/**
 * Kryo 序列化
 * 
 * @author fuzi
 * @version KryoSerialization.java, v 0.1 2015年4月28日 下午2:23:49
 */
public final class KryoSerializer implements Serializer {

	/** kyro工厂 */
	private final KyroFactory	kyroFactory;

	/**
	 * 构造函数
	 */
	public KryoSerializer() {
		this(new KyroFactory());
	}

	/**
	 * 构造函数
	 * 
	 * @param kyroFactory
	 *            kyro工厂
	 */
	public KryoSerializer(final KyroFactory kyroFactory) {
		this.kyroFactory = kyroFactory;
	}

	/**
	 * 
	 * @param object
	 *            序列化对象
	 * @return 字节数组
	 * @throws IOException
	 *             IO异常
	 * @see Serializer#serialize(java.lang.Object)
	 */
	@Override
	public byte[] serialize(Object object) throws IOException {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		serialize(stream, object);
		return stream.toByteArray();
	}

	/**
	 * 
	 * @param bytes
	 *            字节数组
	 * @param clazz
	 *            类型
	 * @return 反序列化出来的对象
	 * @throws IOException
	 *             IO异常
	 * @see Serializer#deserialize(byte[],
	 *      java.lang.Class)
	 */
	@Override
	public <T> T deserialize(byte[] bytes, Class<T> clazz) throws IOException {
		ByteArrayInputStream stream = new ByteArrayInputStream(bytes);
		T result = deserialize(stream, clazz);
		return result;
	}

	private void serialize(final OutputStream out, final Object message) throws IOException {
		Kryo kryo = kyroFactory.getKryo();
		Output output = new Output(out);
		kryo.writeObject(output, message);
		output.close();
		kyroFactory.returnKryo(kryo);
	}

	private <T> T deserialize(final InputStream in, Class<T> clazz) throws IOException {
		Kryo kryo = kyroFactory.getKryo();
		Input input = new Input(in);
		T result = kryo.readObject(input, clazz);
		input.close();
		kyroFactory.returnKryo(kryo);
		return result;
	}
}
