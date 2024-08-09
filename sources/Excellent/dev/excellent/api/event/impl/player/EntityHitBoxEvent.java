package dev.excellent.api.event.impl.player;

import dev.excellent.api.event.Event;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.minecraft.entity.Entity;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class EntityHitBoxEvent extends Event {
    private Entity entity;
    private float size;
}
