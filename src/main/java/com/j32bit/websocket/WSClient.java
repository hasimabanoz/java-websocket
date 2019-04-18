package com.j32bit.websocket;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.ContainerProvider;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

import com.j32bit.util.Constants;

@ClientEndpoint
public class WSClient {

	private static Object waitLock = new Object();
	private List<String> messages = new ArrayList<>();

	@OnMessage
	public void onMessage(String message) {
		// the new USD rate arrives from the websocket server side.
		String log = message.concat(" Client timestamp: " + System.currentTimeMillis());
		messages.add(log);
		// System.out.println("Server: " + message + " Client timestamp: " + System.currentTimeMillis());
	}

	@OnError
	public void onError(Throwable t) {
		System.out.println(t.getMessage());
	}

	@OnClose
	public void onClose(Session websocket, CloseReason reason) {
		System.out.println("Closed. " + websocket.getId());
		StringBuilder sb = new StringBuilder();
		for (String message : messages) {
			sb.append(message).append("\n");
		}
		System.out.println(sb.toString());
	}

	private static void wait4TerminateSignal() {
		synchronized (waitLock) {
			try {
				waitLock.wait();
			} catch (InterruptedException e) {

			}
		}
	}

	public void startSession(String user) {
		System.out.println("Start session: " + user);
		messages.clear();
		WebSocketContainer container = null;
		Session session = null;
		try {
			// Tyrus is plugged via ServiceLoader API. See notes above
			container = ContainerProvider.getWebSocketContainer();
			// WS1 is the context-root of my web.app
			// ratesrv is the path given in the ServerEndPoint annotation on server implementation
			session = container.connectToServer(WSClient.class, URI.create(Constants.WS_ENDPOINT_BASE + user));
			wait4TerminateSignal();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				try {
					session.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void main(String[] args) {
		WebSocketContainer container = null;
		Session session = null;
		try {
			// Tyrus is plugged via ServiceLoader API. See notes above
			container = ContainerProvider.getWebSocketContainer();
			// WS1 is the context-root of my web.app
			// ratesrv is the path given in the ServerEndPoint annotation on server implementation
			session = container.connectToServer(WSClient.class, URI.create("ws://localhost:8080/java-websocket/chat/2"));
			wait4TerminateSignal();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				try {
					session.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

}
