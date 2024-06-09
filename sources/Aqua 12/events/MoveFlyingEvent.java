// 
// Decompiled by Procyon v0.5.36
// 

package events;

public class MoveFlyingEvent extends Event<MoveFlyingEvent>
{
    public float yaw;
    
    public MoveFlyingEvent(final float yaw) {
        this.yaw = yaw;
    }
}
