package io.github.liticane.clients.feature.event.impl.other;

import io.github.liticane.clients.feature.event.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


public class JumpFixEvent extends Event {
    private float yaw;

    public JumpFixEvent(float yaw) {
        this.yaw = yaw;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public float getYaw() {
        return yaw;
    }
}
