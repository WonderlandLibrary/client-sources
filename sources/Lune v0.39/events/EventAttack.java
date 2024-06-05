package me.superskidder.lune.events;

import me.superskidder.lune.manager.event.Event;
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
