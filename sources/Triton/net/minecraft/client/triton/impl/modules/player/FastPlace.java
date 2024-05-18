// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.client.triton.impl.modules.player;

import net.minecraft.client.triton.management.event.Event;
import net.minecraft.client.triton.management.event.EventTarget;
import net.minecraft.client.triton.management.event.events.UpdateEvent;
import net.minecraft.client.triton.management.module.Module;
import net.minecraft.client.triton.management.module.Module.Mod;
import net.minecraft.client.triton.utils.ClientUtils;

@Mod(displayName = "Fast Place")
public class FastPlace extends Module
{
    @EventTarget
    private void onUpdate(final UpdateEvent event) {
        if (event.getState() == Event.State.PRE) {
            ClientUtils.mc().rightClickDelayTimer = 0;
        }
    }
}
