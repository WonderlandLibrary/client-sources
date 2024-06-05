// 
// Decompiled by Procyon v0.6.0
// 

package wtf.eviate.protection;

import gg.childtrafficking.smokex.event.Event;
import gg.childtrafficking.smokex.event.EventDispatcher;

public final class ProtectionManager
{
    private final EventDispatcher<Event> eventDispatcher;
    
    public ProtectionManager() {
        this.eventDispatcher = new EventDispatcher<Event>();
    }
}
