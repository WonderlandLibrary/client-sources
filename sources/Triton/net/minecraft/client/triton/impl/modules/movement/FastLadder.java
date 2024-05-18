// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.client.triton.impl.modules.movement;

import net.minecraft.client.triton.management.event.EventTarget;
import net.minecraft.client.triton.management.event.events.MoveEvent;
import net.minecraft.client.triton.management.module.Module;
import net.minecraft.client.triton.management.module.Module.Mod;
import net.minecraft.client.triton.management.option.Option.Op;
import net.minecraft.client.triton.utils.ClientUtils;

@Mod(displayName = "Fast Ladder")
public class FastLadder extends Module
{
    private static final double MAX_LADDER_SPEED = 0.287299999999994;
    
    @EventTarget
    private void onMove(final MoveEvent event) {
        if (event.getY() > 0.0 && ClientUtils.player().isOnLadder()) {
            event.setY(0.287299999999994);
        }
    }
}
