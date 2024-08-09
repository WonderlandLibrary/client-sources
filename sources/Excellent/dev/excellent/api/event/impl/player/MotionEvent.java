package dev.excellent.api.event.impl.player;

import dev.excellent.api.event.CancellableEvent;
import dev.excellent.api.interfaces.game.IMinecraft;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public final class MotionEvent extends CancellableEvent implements IMinecraft {
    private double x, y, z;
    private float yaw, pitch;
    private boolean onGround;
    private boolean isSneaking;
    private boolean isSprinting;
    private Runnable postMotion;
}