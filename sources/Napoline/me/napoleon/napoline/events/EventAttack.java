package me.napoleon.napoline.events;

import me.napoleon.napoline.manager.event.Event;
import net.minecraft.entity.Entity;

public class EventAttack extends Event {
    public Entity target;
    public EventAttack(Entity t){
        target = t;
    }
    public Entity getTarget(){
        return this.target;
    }
}
