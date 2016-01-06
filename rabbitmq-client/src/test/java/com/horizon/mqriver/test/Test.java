package com.horizon.mqriver.test;

import java.net.UnknownHostException;

import com.horizon.mqriver.api.ec.IEventListener;
import com.horizon.mqriver.ec.DefaultEventListenerFactory;

public class Test {

	public static void main(String[] args) throws UnknownHostException {
		IEventListener listener = DefaultEventListenerFactory.newDefaultEventListener();
		listener.init("com.1289.sdk");
		// InetAddress address = InetAddress.getLocalHost();
		// System.out.println(address.getHostAddress());
		// InetAddress a[] = InetAddress.getAllByName("localhost");
		// for (InetAddress ia : a) {
		// System.out.println("------" + ia.getHostAddress());
		// }
	}

}
