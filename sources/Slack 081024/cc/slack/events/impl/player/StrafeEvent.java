package cc.slack.events.impl.player;

import cc.slack.events.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class StrafeEvent extends Event {
    private float strafe;
    private float forward;
    private float friction;
    private float yaw;
}