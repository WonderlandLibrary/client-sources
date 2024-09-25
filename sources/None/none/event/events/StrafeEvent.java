package none.event.events;

import none.event.Event;

public class StrafeEvent extends Event{
	
	public float strafe, forward, friction;
	
	public void fire(float strafe, float forward, float friction) {
		this.strafe = strafe;
		this.forward = forward;
		this.friction = friction;
		super.fire();
	}

	public float getStrafe() {
		return strafe;
	}

	public void setStrafe(float strafe) {
		this.strafe = strafe;
	}

	public float getForward() {
		return forward;
	}

	public void setForward(float forward) {
		this.forward = forward;
	}

	public float getFriction() {
		return friction;
	}

	public void setFriction(float friction) {
		this.friction = friction;
	}
}
