package io.github.liticane.electron.event.impl.minecraft.player;

import io.github.liticane.electron.event.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MoveUpdateEvent extends Event {
    private double x, y, z;
    private float yaw, pitch;
    private boolean onGround;
}
