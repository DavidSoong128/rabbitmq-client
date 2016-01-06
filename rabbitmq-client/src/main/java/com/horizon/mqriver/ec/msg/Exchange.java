package com.horizon.mqriver.ec.msg;

import java.util.Map;

public interface Exchange {

	String getName();

	String getType();

	boolean isDurable();

	boolean isAutoDelete();

	Map<String, Object> getArguments();
}