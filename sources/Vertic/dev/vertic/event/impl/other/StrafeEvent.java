package dev.vertic.event.impl.other;

import dev.vertic.event.Event;
import dev.vertic.util.player.MoveUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class StrafeEvent extends Event {
    private float forward, strafe, friction;
    public void setSpeed(final float speed) {
        setFriction(getForward() != 0 && getStrafe() != 0 ? speed * 0.99F : speed);
        MoveUtil.stop();
    }
}
