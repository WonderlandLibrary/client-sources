package lunadevs.luna.events;

import com.darkmagician6.eventapi.events.Event;

public class EventPostMotionUpdates implements Event{

	   public float yaw, pitch;

	    public boolean ground;

	    public double y;

	    public EventPostMotionUpdates(float yaw, float pitch, boolean ground, double y) {

	        this.yaw = yaw;
	        this.pitch = pitch;
	        this.ground = ground;
	        this.y = y;
	    }

	    public float getYaw() {

	        return yaw;
	    }

	    public void setYaw(float yaw) {

	        this.yaw = yaw;
	    }

	    public float getPitch() {

	        return pitch;
	    }

	    public void setPitch(float pitch) {

	        this.pitch = pitch;
	    }

	    public boolean isGround() {

	        return ground;
	    }

	    public void setGround(boolean ground) {

	        this.ground = ground;
	    }

	    public double getY() {

	        return y;
	    }

	    public void setY(double y) {

	        this.y = y;
	    }
	}
