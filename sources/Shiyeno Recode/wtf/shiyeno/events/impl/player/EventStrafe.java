package wtf.shiyeno.events.impl.player;

import wtf.shiyeno.events.Event;

public class EventStrafe extends Event {

    public float yaw;

    public EventStrafe(float yaw) {
        this.yaw = yaw;
    }

}
