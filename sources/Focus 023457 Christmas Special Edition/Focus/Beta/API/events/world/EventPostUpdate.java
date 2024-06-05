
package Focus.Beta.API.events.world;

import Focus.Beta.API.Event;

public class EventPostUpdate extends Event {
	private float yaw;
	public static float pitch;
	public static boolean rotatingPitch;

	public EventPostUpdate(float yaw, float pitch) {
		this.yaw = yaw;
		this.pitch = pitch;
	}

	public float getYaw() {
		return this.yaw;
	}

	public void setYaw(float yaw) {
		this.yaw = yaw;
	}

	public float getPitch() {
		return this.pitch;
	}

	public void setPitch(float pitch) {
		this.pitch = pitch;
		rotatingPitch = true;
	}
}
