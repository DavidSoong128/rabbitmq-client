/**
 * www.1289.com Inc.
 * Copyright (c) 2012-2015 All Rights Reserved.
 */
package com.horizon.mqriver.ec.utils;

/**
 * 字符串工具
 * 
 * @author David.Soong
 * @version StringUtils.java, v 0.1 2015年6月24日 下午4:28:28
 */
public final class StringUtils {

	/**
	 * 构造函数
	 */
	private StringUtils() {

	}

	/**
	 * 判断字符串是否为空
	 * 
	 * @param str
	 *            字符串
	 * @return 是否为空
	 */
	public static boolean isEmpty(String str) {
		if (str == null) {
			return true;
		}
		if (str.trim().length() == 0) {
			return true;
		}
		return false;
	}

	/**
	 * 判断字符串是否为空
	 * 
	 * @param str
	 *            字符串
	 * @return 是否为空
	 */
	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}
}
