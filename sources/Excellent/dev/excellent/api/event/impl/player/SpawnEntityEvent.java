package dev.excellent.api.event.impl.player;

import dev.excellent.api.event.Event;
import lombok.AllArgsConstructor;
import lombok.Data;
import net.minecraft.entity.Entity;

@Data
@AllArgsConstructor
public class SpawnEntityEvent extends Event {
    private Entity entity;
}