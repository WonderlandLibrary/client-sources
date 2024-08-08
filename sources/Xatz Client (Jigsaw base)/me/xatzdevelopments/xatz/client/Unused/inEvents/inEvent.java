// 
// Decompiled by Procyon v0.5.36
// 

package me.xatzdevelopments.xatz.client.Unused.inEvents;

public class inEvent<T>
{
    public boolean cancelled;
    public inEventType type;
    public inEventDirection direction;
    
    public boolean isCancelled() {
        return this.cancelled;
    }
    
    public void setCancelled(final boolean cancelled) {
        this.cancelled = cancelled;
    }
    
    public inEventType getType() {
        return this.type;
    }
    
    public void setType(final inEventType type) {
        this.type = type;
    }
    
    public inEventDirection getDirection() {
        return this.direction;
    }
    
    public void setDirection(final inEventDirection direction) {
        this.direction = direction;
    }
    
    public boolean isPre() {
        return this.type != null && this.type == inEventType.PRE;
    }
    
    public boolean isPost() {
        return this.type != null && this.type == inEventType.POST;
    }
    
    public boolean isIncoming() {
        return this.direction != null && this.direction == inEventDirection.INCOMING;
    }
    
    public boolean isOutgoing() {
        return this.direction != null && this.direction == inEventDirection.OUTGOING;
    }
}
