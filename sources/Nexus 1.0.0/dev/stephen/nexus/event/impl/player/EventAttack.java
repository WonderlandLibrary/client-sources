package dev.stephen.nexus.event.impl.player;

import dev.stephen.nexus.event.types.Event;
import lombok.Getter;
import net.minecraft.entity.Entity;

@Getter
public class EventAttack implements Event {
    Entity target;
    public EventAttack(Entity target){
        this.target = target;
    }
}
