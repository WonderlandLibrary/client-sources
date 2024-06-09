package me.swezedcode.client.utils.events;

import com.darkmagician6.eventapi.events.Event;
import com.darkmagician6.eventapi.events.callables.EventCancellable;
import com.darkmagician6.eventapi.types.EventType;

public class EventPreMotionUpdates extends EventCancellable {

	public EventType state;
	public float yaw, pitch;
	public double y;
	public boolean ground;

	public EventPreMotionUpdates() {
		this.state = state.POST;
	}

	public EventPreMotionUpdates(double y, float yaw, float pitch, boolean ground) {
		this.state = state.PRE;
		this.yaw = yaw;
		this.pitch = pitch;
		this.y = y;
		this.ground = ground;
	}
	
	public EventType getState() {
		return state;
	}
	
}