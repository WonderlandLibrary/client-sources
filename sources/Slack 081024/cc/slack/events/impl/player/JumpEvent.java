package cc.slack.events.impl.player;

import cc.slack.events.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class JumpEvent extends Event {
    private float yaw;
    private float jumpMotion;
}
