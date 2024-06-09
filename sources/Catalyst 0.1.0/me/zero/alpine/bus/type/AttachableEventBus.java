// 
// Decompiled by Procyon v0.5.36
// 

package me.zero.alpine.bus.type;

import me.zero.alpine.bus.EventBus;

public interface AttachableEventBus extends EventBus
{
    void attach(final EventBus p0);
    
    void detach(final EventBus p0);
}
