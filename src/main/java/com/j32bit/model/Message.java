package com.j32bit.model;

public class Message {
	private String from;
	private String to;
	private String content;
	private long timestamp;

	public Message() {
		super();
	}

	public Message(String from, long timestamp) {
		super();
		this.from = from;
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		return super.toString();
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
}
