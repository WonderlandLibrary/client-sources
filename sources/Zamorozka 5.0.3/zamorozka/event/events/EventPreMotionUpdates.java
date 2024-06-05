package zamorozka.event.events;

import net.minecraft.network.play.client.CPacketPlayer.Rotation;
import zamorozka.event.Event;
import zamorozka.ui.Location2;

/**
 * Created by Hexeption on 07/01/2017.
 */
public class EventPreMotionUpdates extends Event {

    private boolean cancel;

    public float yaw, pitch;

    public double y;
    
    private Rotation rotation;

	private Location2 location;

    public EventPreMotionUpdates(float yaw, float pitch, double y, Location2 location) {

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

        this.yaw = yaw;
    }

    public float getPitch() {

        return pitch;
    }

    public void setPitch(float pitch) {

        this.pitch = pitch;
    }

    public double getY() {

        return y;
    }

    public void setY(double y) {

        this.y = y;
    }

	public EventPlayerMotionUpdate getLocation() {
		// TODO Auto-generated method stub
		return null;
	}

    public double getLegitMotion() {
        return 0.41999998688697815D;
    }
}
