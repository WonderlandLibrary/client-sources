package dev.nexus.events.impl;

import dev.nexus.events.types.Event;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EventRenderThirdPerson implements Event {
    private float yaw;
    private float pitch;
    private float prevYaw;
    private float prevPitch;
    private boolean accepted;

    public EventRenderThirdPerson(float yaw, float pitch, float prevYaw, float prevPitch) {
        this.yaw = yaw;
        this.pitch = pitch;
        this.prevYaw = prevYaw;
        this.prevPitch = prevPitch;
    }

    public boolean getAccepted() {
        return accepted;
    }
}