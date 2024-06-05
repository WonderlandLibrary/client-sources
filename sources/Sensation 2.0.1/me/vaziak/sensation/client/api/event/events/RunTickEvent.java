package me.vaziak.sensation.client.api.event.events;

/**
 * @author antja03
 */
public class RunTickEvent{

	private boolean pre;
	
	public RunTickEvent(boolean pre) {
		this.pre = pre;
	}
	
	public boolean isPre() {
		return pre;
	}
}
