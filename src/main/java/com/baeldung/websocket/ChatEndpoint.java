package com.baeldung.websocket;

import java.io.IOException;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import com.baeldung.model.Message;

@ServerEndpoint(value = "/chat/{username}", decoders = MessageDecoder.class, encoders = MessageEncoder.class)
public class ChatEndpoint {
	private Session session;
	private static final Set<ChatEndpoint> chatEndpoints = new CopyOnWriteArraySet<>();
	private static HashMap<String, String> users = new HashMap<>();

	@OnOpen
	public void onOpen(Session session, @PathParam("username") String username) throws IOException, EncodeException {

		this.session = session;
		chatEndpoints.add(this);
		users.put(session.getId(), username);
		System.out.println("ID:" + session.getId() + " - User:" + username + " connected.");

		if (chatEndpoints.size() > 2) {
			broadcastTimeStamp(session.getId(), 10);
		}
	}

	@OnMessage
	public void onMessage(Session session, Message message) throws IOException, EncodeException {
		message.setFrom(users.get(session.getId()));
		// System.out.println("Received from " + session.getId() + ": " + message.getContent());
		// broadcast(message);
		// broadcastTimeStamp(10);
	}

	@OnClose
	public void onClose(Session session) throws IOException, EncodeException {
		chatEndpoints.remove(this);
		Message message = new Message();
		message.setFrom(users.get(session.getId()));
		message.setContent("Disconnected!");
		// broadcast(message);
		// broadcastTimeStamp(10);
	}

	@OnError
	public void onError(Session session, Throwable throwable) {
		// Do error handling here
	}

	private static void broadcast(Message message) throws IOException, EncodeException {
		chatEndpoints.forEach(endpoint -> {
			synchronized (endpoint) {
				try {
					endpoint.session.getBasicRemote().sendObject(message);
				} catch (IOException | EncodeException e) {
					e.printStackTrace();
				}
			}
		});
	}

	private static void broadcastTimeStamp(String user, int count) throws IOException, EncodeException {
		for (int i = 0; i < count; i++) {
			chatEndpoints.forEach(endpoint -> {
				synchronized (endpoint) {
					try {
						Message message = new Message(users.get(endpoint.session.getId()), System.currentTimeMillis());
						endpoint.session.getBasicRemote().sendObject(message);
					} catch (IOException | EncodeException e) {
						e.printStackTrace();
					}
				}
			});
		}
	}

}
