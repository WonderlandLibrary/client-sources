package Focus.Beta.API.events.world;

import Focus.Beta.API.Event;
import net.minecraft.client.Minecraft;

public class EventPreUpdate extends Event {
	private float yaw;
	public static float pitch;
	public double y;
	private boolean ground;

	public EventPreUpdate(float yaw, float pitch, double y, boolean ground) {
		this.yaw = yaw;
		this.pitch = pitch;
		this.y = y;
		this.ground = ground;
	}

	public float getYaw() {
		return this.yaw;
	}

	public void setYaw(float yaw) {
		this.yaw = yaw;
		Minecraft.getMinecraft().thePlayer.rotationYawHead = yaw;
		Minecraft.getMinecraft().thePlayer.renderYawOffset = yaw;

	}

	public float getPitch() {
		return this.pitch;
	}

	public void setPitch(float pitch) {
		this.pitch = pitch;
		Minecraft.getMinecraft().thePlayer.rotationPitchHead = pitch;

	}

	public double getY() {
		return this.y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public boolean isOnground() {
		return this.ground;
	}

	public void setOnground(boolean ground) {
		this.ground = ground;
	}
}
