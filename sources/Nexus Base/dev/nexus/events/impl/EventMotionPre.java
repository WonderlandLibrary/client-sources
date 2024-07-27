package dev.nexus.events.impl;

import dev.nexus.events.types.CancellableEvent;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventMotionPre extends CancellableEvent {
    private double x, y, z;
    private float yaw, pitch;
    private boolean onGround;

    public EventMotionPre(double x, double y, double z, float yaw, float pitch, boolean onGround) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
        this.onGround = onGround;
    }
}
