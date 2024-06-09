// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.client.triton.impl.modules.misc;

import net.minecraft.client.triton.management.event.Event;
import net.minecraft.client.triton.management.event.EventTarget;
import net.minecraft.client.triton.management.event.events.UpdateEvent;
import net.minecraft.client.triton.management.module.Module;
import net.minecraft.client.triton.management.module.Module.Mod;
import net.minecraft.client.triton.utils.ClientUtils;

@Mod(displayName = "FastRespawn")
public class Respawn extends Module
{
    @EventTarget
    private void onUpdate(final UpdateEvent event) {
        if (event.getState() == Event.State.POST && !ClientUtils.player().isEntityAlive()) {
            ClientUtils.player().respawnPlayer();
        }
    }
}
