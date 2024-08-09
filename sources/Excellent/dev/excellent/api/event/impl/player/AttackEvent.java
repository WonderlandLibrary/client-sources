package dev.excellent.api.event.impl.player;

import dev.excellent.api.event.CancellableEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.entity.Entity;

@Getter
@AllArgsConstructor
public final class AttackEvent extends CancellableEvent {
    private Entity target;
}