package dev.stephen.nexus.event.impl.player;

import dev.stephen.nexus.event.types.Event;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventSilentRotation implements Event {

    private final float initYaw;
    private final float initPitch;
    private float yaw, pitch;
    private float speed;

    public EventSilentRotation(float yaw, float pitch, float speed) {
        this.initYaw = yaw;
        this.initPitch = pitch;
        this.yaw = yaw;
        this.pitch = pitch;
        this.speed = speed;
    }

    public boolean hasBeenModified() {
        return initYaw != yaw || initPitch != pitch;
    }
}