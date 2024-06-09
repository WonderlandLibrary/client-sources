package me.valk.event.events.entity;

import me.valk.event.Event;
import me.valk.event.EventType;
import net.minecraft.entity.Entity;

public class EventEntityStep extends Event {

    private float stepHeight;
    private Entity entity;
    private EventType type;

    public EventEntityStep(Entity entity, float stepHeight, EventType type){
        this.entity = entity;
        this.stepHeight = stepHeight;
        this.type = type;
    }

    public float getStepHeight() {
        return stepHeight;
    }

    public void setStepHeight(float stepHeight) {
        this.stepHeight = stepHeight;
    }

    public Entity getEntity(){
        return entity;
    }

    public EventType getType(){
        return type;
    }

}
