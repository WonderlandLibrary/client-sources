package dev.excellent.api.event.impl.player;

import dev.excellent.api.event.Event;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.minecraft.entity.Entity;

@Getter
@RequiredArgsConstructor
public class RemoveEntityEvent extends Event {
    private final Entity entity;
}
