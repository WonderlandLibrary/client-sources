package net.silentclient.client.event.impl;

import net.silentclient.client.event.Event;

public class EventDebugFps extends Event {
	private int fps;
	
	public EventDebugFps(int fps) {
		this.fps = fps;
	}
	
	public int getFps() {
		return fps;
	}
	
	public void setFps(int fps) {
		this.fps = fps;
	}
}
