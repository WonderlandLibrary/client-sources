package lol.point.returnclient.events.impl.player;

import lol.point.returnclient.events.Event;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class EventMotion extends Event {
    public double x, y, z;
    public boolean onGround;
    public boolean Sprinting;
    public float yaw, pitch;
}
