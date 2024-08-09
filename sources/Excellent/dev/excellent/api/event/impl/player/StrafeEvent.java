package dev.excellent.api.event.impl.player;

import dev.excellent.api.event.CancellableEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.minecraft.util.math.vector.Vector3d;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public final class StrafeEvent extends CancellableEvent {

    private float friction;
    private Vector3d relative;
    private float yaw;
}