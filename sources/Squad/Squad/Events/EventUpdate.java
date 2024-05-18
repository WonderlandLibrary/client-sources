package Squad.Events;

import com.darkmagician6.eventapi.events.Event;
import com.darkmagician6.eventapi.events.callables.EventCancellable;

/**
 * Created by Nikolas on 16.02.2017.
 */

public class EventUpdate extends EventCancellable {
    public Event.State state;
    public float yaw;
    public float pitch;
    public double y;
    public boolean ground;

    public EventUpdate() {
        this.state = Event.State.POST;
    }

    public EventUpdate(double y, float yaw, float pitch, boolean ground) {
        this.state = Event.State.PRE;
        this.yaw = yaw;
        this.pitch = pitch;
        this.y = y;
        this.ground = ground;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public Event.State getState() {
        return this.state;
    }
}


