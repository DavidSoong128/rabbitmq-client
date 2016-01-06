package com.horizon.mqriver.ec.serialize;

import java.io.IOException;

/**
 * 序列化接口
 * 
 * @author fuzi
 * @version $Id: Serializer.java, v 0.1 2015年4月28日 下午2:23:49 fuzi Exp $
 */
public interface Serializer {

	public byte[] serialize(Object object) throws IOException;

	public <T> T deserialize(byte[] bytes, Class<T> clazz) throws IOException;
}
