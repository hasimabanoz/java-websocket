package com.j32bit.websocket;

import com.j32bit.util.Constants;
import com.j32bit.util.PropertiesUtils;

public class WSMulti {

	public static void main(String[] args) {
		PropertiesUtils.loadProperties("ws.properties", Constants.props);

		Constants.MAX_THREAD = Integer.parseInt(Constants.props.getProperty("max.thread"));
		Constants.THREAD_PREFIX = Constants.props.getProperty("thread.prefix");
		Constants.WS_ENDPOINT_BASE = Constants.props.getProperty("web.socket.endpoint.base");

		System.out.println(Constants.props.getProperty("app.name") + " initalized.\n");

		for (int i = 0; i < Constants.MAX_THREAD; i++) {
			WSRunner R1 = new WSRunner(Constants.THREAD_PREFIX + (i + 1));
			R1.start();
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
