package lunadevs.luna.events;

import org.apache.commons.io.DirectoryWalker.CancelException;

import com.darkmagician6.eventapi.events.Event;
import com.darkmagician6.eventapi.events.callables.EventCancellable;

public class PreMotionUpdatesEvent extends EventCancellable implements Event{
	
    private boolean cancel;

    public float yaw, pitch;

    public double y;

    public PreMotionUpdatesEvent(float yaw, float pitch, double y) {

        this.yaw = yaw;
        this.pitch = pitch;
        this.y = y;
    }

    public boolean isCancel() {

        return cancel;
    }

    public void setCancel(boolean cancel) {

        this.cancel = cancel;
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
  
}
