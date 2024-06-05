// 
// Decompiled by Procyon v0.6.0
// 

package gg.childtrafficking.smokex.event;

public final class EventListenerStorage<T>
{
    private final Object owner;
    private final EventListener<T> callback;
    
    public EventListenerStorage(final Object owner, final EventListener<T> callback) {
        this.owner = owner;
        this.callback = callback;
    }
    
    public Object owner() {
        return this.owner;
    }
    
    public EventListener<T> callback() {
        return this.callback;
    }
}
