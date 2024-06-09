// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient.eventbus.bus;

public interface Bus<Event>
{
    void subscribe(final Object p0);
    
    void unsubscribe(final Object p0);
    
    void post(final Event p0);
}
