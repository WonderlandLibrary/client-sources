package de.tired.event.events;

import de.tired.event.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class RotationEvent extends Event {
    float yaw, pitch;

    public void setRotations(float[] rotations) {
        this.yaw = rotations[0];
        this.pitch = rotations[1];
    }

}