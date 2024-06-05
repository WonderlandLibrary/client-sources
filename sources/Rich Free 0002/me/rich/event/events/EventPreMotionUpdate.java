package me.rich.event.events;

import me.rich.event.Event;
import me.rich.helpers.combat.LPositionHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.CPacketPlayer.Rotation;

public class EventPreMotionUpdate extends Event {

    private boolean cancel;

    public float yaw, pitch;

    public double y;
    
    public boolean ground;
    
    private Rotation rotation;

	private LPositionHelper location;

	  public EventPreMotionUpdate(float yaw, float pitch, double y, LPositionHelper location) {

	        this.yaw = yaw;
	        this.pitch = pitch;
	        this.y = y;
	        this.location = location;
	    }

	    public boolean isCancel() {

	        return cancel;
	    }

	    public void setCancel(boolean cancel) {

	        this.cancel = cancel;
	    }

	    public Rotation getRotation() {
	        return this.rotation;
	    }

	    public float getYaw() {

	        return yaw;
	    }

	    public void setYaw(float yaw) {
	        Minecraft.getMinecraft().player.renderYawOffset = yaw;
	        Minecraft.getMinecraft().player.rotationYawHead = yaw;
	        this.yaw = yaw;
	    }

	    public float getPitch() {

	        return pitch;
	    }

	    public void setPitch(float pitch) {
	        Minecraft.getMinecraft().player.rotationPitchHead = pitch;
	        this.pitch = pitch;
	    }

	    public double getY() {

	        return y;
	    }

	    public void setY(double y) {

	        this.y = y;
	    }

	    public EventPlayerMotionUpdate getLocation() {

	        return null;
	    }

	    public double getLegitMotion() {
	        return 0.41999998688697815D;
	    }
	

    public boolean onGround() {
        return ground;
    }
    public void setGround(boolean ground) {
        this.ground = ground;
    }
}
