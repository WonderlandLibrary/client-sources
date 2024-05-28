package dev.vertic.event.impl.motion;

import dev.vertic.event.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public final class PreMotionEvent extends Event {
    private float yaw, pitch;
    private boolean onGround;
    private double x, y, z;
}
