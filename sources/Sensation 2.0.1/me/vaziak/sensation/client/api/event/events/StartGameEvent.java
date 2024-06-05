package me.vaziak.sensation.client.api.event.events;

/**
 * @author antja03
 */
public class StartGameEvent {

	private boolean pre;

	public StartGameEvent(boolean pre) {
		this.pre = pre;
	}
	
	public boolean isPre()	{
		return pre;
	}
}
