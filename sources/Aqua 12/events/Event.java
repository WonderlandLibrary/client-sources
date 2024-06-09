// 
// Decompiled by Procyon v0.5.36
// 

package events;

import net.minecraft.network.EnumPacketDirection;

public class Event<T>
{
    public boolean cancelled;
    public EventType type;
    public EventDirection direction;
    public EnumPacketDirection direction2;
    
    public boolean isCancelled() {
        return this.cancelled;
    }
    
    public void setCancelled(final boolean cancelled) {
        this.cancelled = cancelled;
    }
    
    public EventType getType() {
        return this.type;
    }
    
    public void setType(final EventType type) {
        this.type = type;
    }
    
    public EnumPacketDirection getDirection() {
        return this.direction2;
    }
    
    public void setDirection(final EventDirection direction) {
        this.direction = direction;
    }
    
    public boolean isPre() {
        return this.type != null && this.type == EventType.PRE;
    }
    
    public boolean isPost() {
        return this.type != null && this.type == EventType.POST;
    }
    
    public boolean isIncoming() {
        return this.direction != null && this.direction == EventDirection.INCOMING;
    }
    
    public boolean isOutgoing() {
        return this.direction != null && this.direction == EventDirection.OUTGOING;
    }
}
