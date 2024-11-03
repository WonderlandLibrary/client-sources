package net.silentclient.client.event.impl;

import net.silentclient.client.event.EventCancelable;

public class EventScrollMouse extends EventCancelable {
	private int amount;
	
	public EventScrollMouse(int amount) {
		this.amount = amount;
	}

	public int getAmount() {
		return amount;
	}
}
