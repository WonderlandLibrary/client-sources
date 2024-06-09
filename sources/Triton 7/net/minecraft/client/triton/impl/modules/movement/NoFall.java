// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.client.triton.impl.modules.movement;

import net.minecraft.client.triton.management.event.EventTarget;
import net.minecraft.client.triton.management.event.events.UpdateEvent;
import net.minecraft.client.triton.management.module.Module;
import net.minecraft.client.triton.management.module.Module.Mod;

@Mod(displayName = "No Fall")
public class NoFall extends Module
{
    @EventTarget
    private void onUpdate(final UpdateEvent event) {
        if (!event.shouldAlwaysSend()) {
            event.setGround(true);
        }
    }
}
