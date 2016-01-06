package com.horizon.mqriver.ec.serialize.kryo;

import java.util.Arrays;
import java.util.EnumMap;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;

import de.javakaffee.kryoserializers.ArraysAsListSerializer;
import de.javakaffee.kryoserializers.EnumMapSerializer;
import de.javakaffee.kryoserializers.KryoReflectionFactorySupport;
import de.javakaffee.kryoserializers.SubListSerializers;
import de.javakaffee.kryoserializers.UnmodifiableCollectionsSerializer;

/**
 * 对象池Kryo工厂
 * 
 * @author fuzi
 * @version $Id: PooledKryoFactory.java, v 0.1 2015年4月28日 下午2:23:49 fuzi Exp $
 */
final class PooledKryoFactory extends BasePooledObjectFactory<Kryo> {

	/**
	 * 创建kryo对象
	 * 
	 * @return kryo对象
	 * @throws Exception
	 *             异常
	 * @see org.apache.commons.pool2.BasePooledObjectFactory#create()
	 */
	@Override
	public Kryo create() throws Exception {
		return createKryo();
	}

	/**
	 * 
	 * @param kryo
	 *            kryo实例
	 * @return PooledObject 对象池对象
	 * @see org.apache.commons.pool2.BasePooledObjectFactory#wrap(java.lang.Object)
	 */
	@Override
	public PooledObject<Kryo> wrap(Kryo kryo) {
		return new DefaultPooledObject<Kryo>(kryo);
	}

	/**
	 * 创建kryo对象
	 *
	 * @return kryo对象
	 */
	private Kryo createKryo() {
		Kryo kryo = new KryoReflectionFactorySupport() {

			@Override
			public Serializer<?> getDefaultSerializer(@SuppressWarnings("rawtypes")
			final Class clazz) {
				if (EnumMap.class.isAssignableFrom(clazz)) {
					return new EnumMapSerializer();
				}
				if (SubListSerializers.ArrayListSubListSerializer.canSerialize(clazz)
						|| SubListSerializers.JavaUtilSubListSerializer.canSerialize(clazz)) {
					return SubListSerializers.createFor(clazz);
				}
				return super.getDefaultSerializer(clazz);
			}
		};
		kryo.register(Arrays.asList("").getClass(), new ArraysAsListSerializer());
		UnmodifiableCollectionsSerializer.registerSerializers(kryo);
		return kryo;
	}
}
