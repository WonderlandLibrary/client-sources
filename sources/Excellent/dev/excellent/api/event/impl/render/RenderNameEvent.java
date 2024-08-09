package dev.excellent.api.event.impl.render;

import dev.excellent.api.event.CancellableEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.entity.Entity;

@Getter
@AllArgsConstructor
public final class RenderNameEvent extends CancellableEvent {
    private final Entity entity;
}