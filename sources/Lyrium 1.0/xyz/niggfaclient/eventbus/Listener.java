// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient.eventbus;

@FunctionalInterface
public interface Listener<Event>
{
    void call(final Event p0);
}
