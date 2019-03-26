package com.baeldung.websocket;

public class WSMulti {

	public static void main(String[] args) {
		// WSClient r1 = new WSClient();
		// r1.startSession("usr1");
		WSRunner R1 = new WSRunner("Thread-1");
		R1.start();
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		WSRunner R2 = new WSRunner("Thread-2");
		R2.start();
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		WSRunner R3 = new WSRunner("Thread-3");
		R3.start();
	}

}
