package club.bluezenith.events.impl;

import club.bluezenith.events.EventType;
import club.bluezenith.events.MultiTypeEvent;

public class UpdatePlayerEvent extends MultiTypeEvent {
    public float yaw, pitch;

    public double x, y, z;

    public boolean onGround;

    public UpdatePlayerEvent(float yaw, float pitch, double x, double y, double z, boolean onGround, EventType type) {
        this.yaw = yaw;
        this.pitch = pitch;
        this.x = x;
        this.y = y;
        this.z = z;
        this.onGround = onGround;
        this.type = type;
    }

    public void setType(EventType t) {
        this.type = t;
    }
}
