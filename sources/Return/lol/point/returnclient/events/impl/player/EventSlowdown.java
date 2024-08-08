package lol.point.returnclient.events.impl.player;

import lol.point.returnclient.events.Event;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class EventSlowdown extends Event {
    public float strafe, forward;
}
