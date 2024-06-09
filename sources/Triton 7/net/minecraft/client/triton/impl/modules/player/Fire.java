// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.client.triton.impl.modules.player;

import net.minecraft.client.triton.management.event.Event;
import net.minecraft.client.triton.management.event.EventTarget;
import net.minecraft.client.triton.management.event.events.UpdateEvent;
import net.minecraft.client.triton.management.module.Module;

public class Fire extends Module
{
    @EventTarget
    private void onPostUpdate(final UpdateEvent event) {
        event.getState();
        final Event.State pre = Event.State.PRE;
    }
}
