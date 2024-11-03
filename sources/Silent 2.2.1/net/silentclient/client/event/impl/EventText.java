package net.silentclient.client.event.impl;

import net.silentclient.client.event.Event;

public class EventText extends Event {
	private String text;
	private String outputText;
	
	public EventText(String text) {
		this.text = text;
		this.outputText = text;
	}
	
	public String getText() {
		return this.text;
	}
	
	public String getOutputText() {
		return this.outputText;
	}
	
	public String replace(String src, String target) {
		this.outputText = text.replace(src, target);
		return this.outputText;
	}
	
	public void setOutputText(String outputText) {
		this.outputText = outputText;
	}
}
