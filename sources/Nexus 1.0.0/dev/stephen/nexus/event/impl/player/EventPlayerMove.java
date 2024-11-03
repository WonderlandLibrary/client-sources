package dev.stephen.nexus.event.impl.player;

import dev.stephen.nexus.event.types.CancellableEvent;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.entity.MovementType;
import net.minecraft.util.math.Vec3d;

@Getter
@Setter
public class EventPlayerMove extends CancellableEvent {
    private MovementType type;
    private Vec3d movement;

    public EventPlayerMove(MovementType type, Vec3d movement) {
        this.type = type;
        this.movement = movement;
    }
}
