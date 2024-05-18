// 
// Decompiled by Procyon v0.5.30
// 

package me.chrest.client.module.modules.player;

import me.chrest.event.EventTarget;
import me.chrest.event.Event;
import me.chrest.event.events.UpdateEvent;
import me.chrest.client.module.Module;

public class Fire extends Module
{
    @EventTarget
    private void onPostUpdate(final UpdateEvent event) {
        event.getState();
        final Event.State pre = Event.State.PRE;
    }
}
