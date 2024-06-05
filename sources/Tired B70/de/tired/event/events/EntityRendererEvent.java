package de.tired.event.events;

import de.tired.event.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.entity.Entity;

@AllArgsConstructor  @Getter
@Setter
public class EntityRendererEvent extends Event {
    Entity entity;
}