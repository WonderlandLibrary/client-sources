// 
// Decompiled by Procyon v0.5.30
// 

package me.chrest.client.module.modules.render;

import me.chrest.event.EventTarget;
import me.chrest.utils.ClientUtils;
import me.chrest.event.events.UpdateEvent;
import me.chrest.client.module.Module;

@Mod(displayName = "No Bob")
public class NoBob extends Module
{
    @EventTarget
    private void onUpdate(final UpdateEvent event) {
        ClientUtils.player().distanceWalkedModified = 0.0f;
    }
}
