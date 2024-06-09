// 
// Decompiled by Procyon v0.5.36
// 

package events.listeners;

import events.Event;

public class EventPlayerMove extends Event
{
    private boolean canceled;
    public double x;
    public double y;
    public double z;
    public static EventPlayerMove INSTANCE;
    
    public EventPlayerMove(final double x, final double y, final double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        EventPlayerMove.INSTANCE = this;
    }
    
    public double getX() {
        return this.x;
    }
    
    public double getY() {
        return this.y;
    }
    
    public double getZ() {
        return this.z;
    }
    
    public void setX(final double x) {
        this.x = x;
    }
    
    public void setY(final double y) {
        this.y = y;
    }
    
    public void setZ(final double z) {
        this.z = z;
    }
}
