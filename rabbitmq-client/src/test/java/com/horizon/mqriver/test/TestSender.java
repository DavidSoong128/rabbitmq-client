package com.horizon.mqriver.test;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import com.horizon.mqriver.api.ec.IEventNotifier;
import com.horizon.mqriver.ec.DefaultEventNotifierFactory;
import com.horizon.mqriver.ec.exception.ECException;
import com.horizon.mqriver.ec.msg.Event;

public class TestSender {

	public static void main(String[] args) {

		final IEventNotifier notifier = DefaultEventNotifierFactory.getEventNotifier();

		new Thread(new Runnable() {

			@Override
			public void run() {
				Random r = new Random();
				for (int i = 0; i < 50; i++) {
					Message m = new Message(i, i, "eeee" + i, "eeee" + i, new Date());

					try {
						notifier.notifyEvent(new Event("dddd", "eeee", m, true));
						if (i == 200) {
							TimeUnit.SECONDS.sleep(r.nextInt(10));
						}
					} catch (ECException e) {
						e.printStackTrace();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();

	}

}
