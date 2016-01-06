package com.horizon.mqriver.ec.serialize.kryo;

import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import com.esotericsoftware.kryo.Kryo;

/**
 *
 * @author fuzi
 * @version $Id: KyroFactory.java, v 0.1 2015年4月28日 下午2:23:49 fuzi Exp $
 */
public final class KyroFactory {

	/** kryo对象池 **/
	private final GenericObjectPool<Kryo>	kryoPool;

	/**
	 * 构造函数
	 */
	public KyroFactory() {
		kryoPool = new GenericObjectPool<Kryo>(new PooledKryoFactory());
	}

	/**
	 * 
	 * @param maxTotal
	 *            最大总共数
	 * @param minIdle
	 *            最小空闲数
	 * @param maxWaitMillis
	 *            最大等待时间
	 * @param minEvictableIdleTimeMillis
	 *            最小空闲时间
	 */
	public KyroFactory(final int maxTotal, final int minIdle, final long maxWaitMillis,
			final long minEvictableIdleTimeMillis) {
		kryoPool = new GenericObjectPool<Kryo>(new PooledKryoFactory());
		GenericObjectPoolConfig config = new GenericObjectPoolConfig();
		config.setMaxTotal(maxTotal);
		config.setMinIdle(minIdle);
		config.setMaxWaitMillis(maxWaitMillis);
		config.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
		kryoPool.setConfig(config);
	}

	/**
	 * 获取kryo实例
	 *
	 *
	 * @return kryo实例
	 */
	public Kryo getKryo() {
		try {
			return kryoPool.borrowObject();
		} catch (final Exception ex) {
			throw new RuntimeException(ex.getMessage(), ex);
		}
	}

	/**
	 * 归还kryo实例回对象池
	 *
	 * @param kryo实例
	 */
	public void returnKryo(final Kryo kryo) {
		kryoPool.returnObject(kryo);
	}
}
