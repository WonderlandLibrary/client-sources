package me.valk.event.events.entity;

import me.valk.event.Event;
import net.minecraft.entity.Entity;

/**
 * Created by Zeb on 5/7/2016.
 */
public class EventEntityInteract extends Event{

    private Entity entity;

    public EventEntityInteract(Entity entity){
        this.entity = entity;
    }

    public Entity getEntity(){
        return entity;
    }
}
