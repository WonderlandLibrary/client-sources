package cn.Hanabi.events;

import com.darkmagician6.eventapi.events.*;
import net.minecraft.entity.*;

public class EventAttack implements Event
{
    public Entity entity;
    
    public EventAttack(final Entity entity) {
        this.entity = entity;
    }
    
    public Entity getEntity() {
        return this.entity;
    }
}
