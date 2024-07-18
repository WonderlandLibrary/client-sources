package net.shoreline.client.impl.event.entity.player;

import net.minecraft.util.math.Vec3d;
import net.shoreline.client.api.event.Cancelable;
import net.shoreline.client.api.event.StageEvent;

@Cancelable
public class TravelEvent extends StageEvent {
    private final Vec3d movementInput;

    public TravelEvent(Vec3d movementInput) {
        this.movementInput = movementInput;
    }

    public Vec3d getMovementInput() {
        return movementInput;
    }
}
