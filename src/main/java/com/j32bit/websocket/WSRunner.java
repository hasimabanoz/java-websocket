package com.j32bit.websocket;

public class WSRunner implements Runnable {
	private Thread t;
	private String threadName;

	WSRunner(String name) {
		threadName = name;
		System.out.println("Creating " + threadName);
	}

	@Override
	public void run() {
		System.out.println("Running " + threadName);
		WSClient r1 = new WSClient();
		r1.startSession(threadName);
		System.out.println("Thread " + threadName + " exiting.");
	}

	public void start() {
		System.out.println("Starting " + threadName);
		if (t == null) {
			t = new Thread(this, threadName);
			t.start();
		}
	}
}
