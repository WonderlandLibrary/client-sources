// 
// Decompiled by Procyon v0.5.30
// 

package me.chrest.event.events;

import org.w3c.dom.events.EventTarget;
import net.minecraft.entity.Entity;
import org.w3c.dom.events.Event;

public class EventPostRenderEntity implements Event
{
    private Entity ent;
    
    public EventPostRenderEntity(final Entity ent) {
        this.ent = ent;
    }
    
    public Entity getEntity() {
        return this.ent;
    }
    
    @Override
    public boolean getBubbles() {
        return false;
    }
    
    @Override
    public boolean getCancelable() {
        return false;
    }
    
    @Override
    public EventTarget getCurrentTarget() {
        return null;
    }
    
    @Override
    public short getEventPhase() {
        return 0;
    }
    
    @Override
    public EventTarget getTarget() {
        return null;
    }
    
    @Override
    public long getTimeStamp() {
        return 0L;
    }
    
    @Override
    public String getType() {
        return null;
    }
    
    @Override
    public void initEvent(final String arg0, final boolean arg1, final boolean arg2) {
    }
    
    @Override
    public void preventDefault() {
    }
    
    @Override
    public void stopPropagation() {
    }
}
