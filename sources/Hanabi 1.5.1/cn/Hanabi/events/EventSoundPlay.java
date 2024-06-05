package cn.Hanabi.events;

import com.darkmagician6.eventapi.events.*;
import net.minecraft.entity.*;

public class EventSoundPlay implements Event
{
    public Entity entity;
    public String name;
    public boolean cancel;
    
    public EventSoundPlay(final Entity entity, final String name) {
        this.entity = entity;
        this.name = name;
    }
    
    public Entity getEntity() {
        return this.entity;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setCancelled(final boolean b) {
        this.cancel = b;
    }
}
