package ru.FecuritySQ.event.imp;

import net.minecraft.entity.Entity;
import ru.FecuritySQ.event.Event;

public class EventInteractEntity extends Event {

    public Entity entity;
    public EventInteractEntity(Entity entity){
        this.entity = entity;
    }

}
